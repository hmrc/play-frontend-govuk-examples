# play-frontend-govuk-examples

This repo is a dependency for the Google Chrome extension [play-frontend-govuk-extension](https://github.com/hmrc/play-frontend-govuk-extension).

The extension provides extra widgets for [design-system.service.gov.uk/components](https://design-system.service.gov.uk/components/).
Currently, this webpage acts as a manual for using GovUK components with HTML and Nunjucks examples.

The extension pulls in the Scala Twirl examples provided by this repo (translated from the Nunjucks examples).
This allows it to add an extra tab to each set of examples for using the Scala Twirl implemented by [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk)

## Prerequisites
First start up the `template-service-spike` NodeJS service according to the ReadMe in the relevant GitHub repo.

## Testing
You can run the unit tests and ensure they pass before committing new changes by the below command:
```
sbt test
```

## Interface for Chrome extension
The [play-frontend-govuk-extension](https://github.com/hmrc/play-frontend-govuk-extension). repo traverses the directory structure of this repo published at [raw.githubusercontent.com](https://raw.githubusercontent.com).

To do this, it uses two URLs:
1) Examples root URL - the URL corresponding to the root directory for all Scala Twirl component example directories
2) Manifest URL - the URL for a manifest JSON file providing the URLs relative to the Examples root URL for each Scala Twirl component example.

Note that if you move the root directory where Scala Twirl examples can be found or the manifest file, you will need to update [play-frontend-govuk-extension](https://github.com/hmrc/play-frontend-govuk-extension).
Such changes will be breaking changes for the extension, and users will need to re-download the latest version of the extension source code.

## Committing changes
If you add or remove any Scala Twirl examples, you will need to regenerate the manifest JSON file.
You can do this by running the below:
```
sbt clean generateExamplesManifestTask
```

You will find changes are made to `manifest.json` - you should Git add this and commit it to master when you are ready to deploy changes.

### Generating Example Templates (Future Work)
_Note: Currently there are examples only for the following components:_

* [Back link](https://design-system.service.gov.uk/components/back-link/) 
* [Button](https://design-system.service.gov.uk/components/button/)
* [Details](https://design-system.service.gov.uk/components/details/)
* [Error message](https://design-system.service.gov.uk/components/error-message/)
* [Error summary](https://design-system.service.gov.uk/components/error-summary/)
* [Fieldset](https://design-system.service.gov.uk/components/fieldset/)
* [Footer](https://design-system.service.gov.uk/components/footer/)
* [Header](https://design-system.service.gov.uk/components/header/)
* [Panel](https://design-system.service.gov.uk/components/panel/)
* [Radios](https://design-system.service.gov.uk/components/radios/)
* [Summary list](https://design-system.service.gov.uk/components/summary-list/)
* [Textarea](https://design-system.service.gov.uk/components/textarea/)
* [Text input](https://design-system.service.gov.uk/components/text-input/)

The examples are unit tested against the expected output `HTML` accompanying each example in the [GOV.UK Design System](https://design-system.service.gov.uk/components/)
to ensure the `Twirl` examples produce the same markup as the `Nunjucks` ones.
Once the examples are created, an `sbt` task is manually run to generate a [manifest.json](src/test/resources/manifest.json)
file that is consumed by the [Chrome extension]((https://github.com/hmrc/play-frontend-govuk-extension)) to display the examples
in the [GOV.UK Design System](https://design-system.service.gov.uk/components/).

We plan to automate the example generation to achieve the following goals:
1. Generate the examples from the `Nunjucks` ones
2. Run the tests
3. Generate the `manifest.json` for the `Chrome` extension

![example generation](docs/images/example-generation.svg)

TODO

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
