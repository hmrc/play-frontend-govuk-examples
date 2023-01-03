/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.govukfrontend.examples

import java.io.{PrintWriter, File => JFile}
import java.nio.file.{Files, Path, Paths}

import scala.reflect.io.File
import scala.reflect.io.Directory
import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

object FileSystem extends Retryable {

  sealed trait TruePath {
    val path: Path
    def ensure(implicit ec: ExecutionContext): Future[Unit]
    def exists(): Boolean
    def getParent: Option[TrueDir] = Option(this.path.toFile.getParentFile).map(f => TrueDir(f.toPath))
    def toPathAncestry: PathAncestry
  }
  case class TrueFile(path: Path) extends TruePath {
    def ensure(implicit ec: ExecutionContext): Future[Unit] = Future {
      val cond = s"[$path] exists and is writable within 15 attempts"
      retryUntil(exists() && Files.isWritable(path), Some(cond))(Files.createFile(path))
    }

    def exists(): Boolean = File(s"$path").exists && Files.exists(path)

    def read(implicit ec: ExecutionContext): Future[String] = Future {
      val source = Source.fromFile(new JFile(path.toString), "UTF-8")
      try source.getLines().mkString("\n")
      finally source.close()
    }

    def toPathAncestry: PathAncestry = PathAncestry(this, getParent.map(_.toPathAncestry))

    def write(content: String)(implicit ec: ExecutionContext): Future[Unit] = Future {
      val pw = new PrintWriter(path.toFile)
      try pw.write(content)
      finally pw.close()
    }

  }
  case class TrueDir(path: Path) extends TruePath {

    val recursiveContents: Iterable[TrueFile] =
      new Directory(path.toFile).deepFiles.map(f => TrueFile(Paths.get(f.path))).toIterable

    def del(): Unit = {
      val cond = s"[$path] directory and children have been deleted within 15 attempts"
      retryUntil(!exists(), Some(cond))(Directory(s"$path").deleteRecursively())
    }

    def ensure(implicit ec: ExecutionContext): Future[Unit] = Future {
      val cond = s"[$path] directory and parents exist and are readable within 15 attempts"
      retryUntil(exists() && Files.isReadable(path), Some(cond))(Files.createDirectories(path))
    }

    def exists(): Boolean = Directory(s"$path").exists && Files.exists(path)

    def toPathAncestry: PathAncestry = PathAncestry(this, getParent.map(_.toPathAncestry))
  }

  case class AncestorCounter(path: PathAncestry, ancestorCount: Int)

  case class PathAncestry(path: TruePath, parent: Option[PathAncestry]) {
    val treeMembers: Iterable[PathAncestry] = {
      def acc(path: PathAncestry): Iterable[PathAncestry] = {
        val ancestors: Iterable[PathAncestry] = path.parent match {
          case None    => Nil
          case Some(a) => acc(a)
        }
        Iterable(path) ++ ancestors
      }

      acc(this)
    }

    def toAncestorCounter: AncestorCounter = AncestorCounter(this, treeMembers.toList.length - 1)
  }

  private def ensure(paths: Iterable[TruePath])(implicit ec: ExecutionContext): Future[Unit] = {
    val ensures: Iterable[Future[Unit]]   = for (path <- paths) yield path.ensure
    val sequenced: Future[Iterable[Unit]] = Future.sequence(ensures)

    sequenced.map(seq => for (_ <- seq) yield ())
  }

  def prepareDirStructure(files: Iterable[TrueFile])(implicit ec: ExecutionContext): Future[Unit] = Future {
    val ancestries: Iterable[PathAncestry] = files.map(_.toPathAncestry)
    val members: Iterable[AncestorCounter] = for {
      ancestry <- ancestries
      member   <- ancestry.treeMembers
    } yield member.toAncestorCounter

    val uniques: Set[AncestorCounter] = members.toSet

    val hierarchyGrouped: Seq[(Int, Set[AncestorCounter])] = uniques.groupBy(_.ancestorCount).toSeq.sortBy(_._1)
    val hierarchyGroups: Seq[Iterable[TruePath]]           = for {
      (_, ancestorCounters) <- hierarchyGrouped
      sameTierPaths          = ancestorCounters.map(_.path.path)
    } yield sameTierPaths

    Future.traverse(hierarchyGroups)(gp => ensure(gp))
  }

}
