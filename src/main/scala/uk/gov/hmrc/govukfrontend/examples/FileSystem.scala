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
    def ensure(): Unit
    def exists(): Boolean
    def getParent: Option[TrueDir] = Option(this.path.toFile.getParentFile).map(f => TrueDir(f.toPath))
    def toFileAncestry: FileAncestry
  }
  case class TrueFile(path: Path) extends TruePath {
    def ensure(): Unit = {
      val cond = s"[$path] exists and is writable within 15 attempts"
      retryUntil(exists() && Files.isWritable(path), Some(cond))(Files.createFile(path))
    }

    def exists(): Boolean = File(s"$path").exists && Files.exists(path)

    def read(): String = {
      val source = Source.fromFile(new JFile(path.toString), "UTF-8")
      try {
        source.getLines().mkString("\n")
      } finally {
        source.close()
      }
    }

    def toFileAncestry: FileAncestry = FileAncestry(this, getParent.map(_.toFileAncestry))

    def write(twirlExample: String)(implicit ec: ExecutionContext): Future[Unit] = Future {
        val pw = new PrintWriter(path.toFile)
        try {
          pw.write(twirlExample)
        } finally {
          pw.close()
        }
    }

  }
  case class TrueDir(path: Path) extends TruePath {

    val recursiveContents: Iterable[TrueFile] = new Directory(path.toFile).deepFiles.map(f => TrueFile(Paths.get(f.path))).toIterable

    def del(): Unit = {
      val cond = s"[$path] directory and children have been deleted within 15 attempts"
      retryUntil(!exists(), Some(cond))(Directory(s"$path").deleteRecursively())
    }

    def ensure(): Unit = {
      val cond = s"[$path] directory and parents exist and are readable within 15 attempts"
      retryUntil(exists() && Files.isReadable(path), Some(cond))(Files.createDirectories(path))
    }

    def exists(): Boolean = Directory(s"$path").exists && Files.exists(path)

    def toFileAncestry: FileAncestry = FileAncestry(this, getParent.map(_.toFileAncestry))
  }

  case class AncestorCounter(file: FileAncestry, ancestorCount: Int)

  case class FileAncestry(path: TruePath, parent: Option[FileAncestry]) {
    val treeMembers: Iterable[FileAncestry] = {
      def acc(file: FileAncestry): Iterable[FileAncestry] = {
        val ancestors: Iterable[FileAncestry] = file.parent match {
          case None => Nil
          case Some(a) => acc(a)
        }
        Iterable(file) ++ ancestors
      }

      acc(this)
    }

    def toAncestorCounter: AncestorCounter = AncestorCounter(this, treeMembers.toList.length - 1)
  }

  def prepareDirStructure(files: Iterable[TrueFile])(implicit ec: ExecutionContext): Future[Unit] = Future {
    val ancestries: Iterable[FileAncestry] = files.map(_.toFileAncestry)
    val members: Iterable[AncestorCounter] = for {
      ancestry <- ancestries
      member <- ancestry.treeMembers
    } yield member.toAncestorCounter

    val uniques: Set[AncestorCounter] = members.toSet
    val hierarchy: Seq[TruePath] = uniques.toSeq.sortBy(_.ancestorCount).map(_.file.path)

    hierarchy.foreach(_.ensure())
  }

}
