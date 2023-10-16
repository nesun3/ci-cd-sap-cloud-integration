<p align="center">
<img src="https://github.com/nesun3/ci-cd-sap-cloud-integration/blob/main/docs/images/cicd.png" alt="Logo" width="200" height="200"/>
</p>

<h1 align="center">Simplifying CI/CD for SAP Cloud Integration Projects</h1>

<p align="center">
Simplify SAP Cloud Integration CI/CD with a native approach—no external tools or Git required.
</p>

<div align="center">
  
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![GitHub](https://img.shields.io/badge/sap_integration-Custom-blue)
</div>

## About this project
CI/CD is a hot topic in the world of software development. It stands for Continuous Integration and Continuous Deployment (or Continuous Delivery), and it plays a pivotal role in modern software development processes. CI/CD is a set of practices, tools, and principles aimed at automating and streamlining the software development lifecycle.

Most modern developer tools, such as VS Code, even SAP BAS, etc. now come equipped with native Git integration. However, SAP Cloud Integration differs significantly from other development tools, and as a result, implementing a CI/CD solution for it presents unique challenges.

Getting started with this process can be relatively straightforward, but it does necessitate a certain level of familiarity with Git, pipelines, and some knowledge on any CI/CD tools such as Jenkins, GitHub Actions, Bitbucket, Azure Pipelines or GitLab, among others. Moreover, you’ll need to install additional tools or programs on your local computer to kickstart the CI/CD process. However, it’s important to note that while constructing a CI/CD pipeline using CI/CD tools can be relatively straightforward, resolving issues or errors in the pipeline and maintaining it demands additional skills, time, and effort. SAP customers may need to train their workforce to effectively use CI/CD tools.

As everybody is not familiar and comfortable with CI/CD tools, this project can be used as an alternative to implement the Continuous Integration (CI) & Continuous Delivery/Deployment (CD) capabilities for SAP Cloud Integration without using any external CI/CD tools. The goal of this project was to leverage the inherent capabilities of SAP Cloud Integration, alongside the Power of Groovy and chaining of APIs, to create a solution that aligns more closely with the platform’s core development style.

Designed with ease-of-use in mind, making it simple and straightforward to begin without the need for any additional external tools or specialized knowledge. It is designed entirely around the core building blocks and artifacts of SAP Cloud Integration, without even relying on native Git, a prerequisite for most CI/CD and DevOps tools.

A write-up about this project can be found here:

![General architecture](https://github.com/nesun3/ci-cd-sap-cloud-integration/blob/main/docs/images/General%20architecture.png)
## Requirements
You will need a SAP Cloud Integration tenant to consume, test and enhance this integrationartifacts.

## Download and Installation
This repository contains
1. [Integration Package](https://github.com/nesun3/ci-cd-sap-cloud-integration/tree/main/build) - The zip file of the integration package that will help you to create your own Continuous Integration (CI) & Continuous Delivery (CD) pipelines for your SAP Cloud Integration Projects. This package is built in such a way, so that you only need to replace a few configuration parameters that are specific to your environment and organization/project and leave the rest as it is.
> Further, you can use this package as a starting template to create your own CI/CD pipelines tailored to your organization/project needs and customize further.
2. [Source Code](https://github.com/nesun3/ci-cd-sap-cloud-integration/tree/main/src) - The unzip or raw file contents of each integration flows.
3. [Test Integration Packages](https://github.com/nesun3/ci-cd-sap-cloud-integration/tree/main/test#test-packages) - You can download the test packages from the repository, which you can readily use for testing. You can explore various combinations within each of the test packages and integration flows and try out the overall solution in your trial or non-production tenant. While you can also use your own test packages and integration flows, these are provided for quick reference to get you started.

## Documentation
To learn how to use the integration artifacts, please refer to the [user documentation](https://github.com/nesun3/ci-cd-sap-cloud-integration/blob/main/docs/README.md).

## Features
  - Sync all or specific integration contents from the SAP Cloud Integration tenant to GitHub Repository to store and see the full history of changes.
  - Check the SAP Cloud Integration tenant for a new version of your integration contents (like packages and iflows) and if a new version exists, sync the changed integration contents to GitHub Repository and keep track of the latest changes.
  - Transport/Migrate all or specific integration contents from the Source SAP Cloud Integration tenant to the Target SAP Cloud Integration tenant without using any external tool.
  - Download the latest version of an integration flow artifact or resources of an integration flow artifact from your GitHub Repository and either update or create the artifacts on the SAP Cloud Integration tenant.

## Support, Feedback, Contributing

This project is open to feature requests/suggestions, bug reports, etc. via [GitHub issues](https://github.com/nesun3/ci-cd-sap-cloud-integration/issues) and [pull requests](https://github.com/nesun3/ci-cd-sap-cloud-integration/pulls). Contribution and feedback are encouraged and always welcome.

See [Contribution guidelines for this project](https://github.com/nesun3/ci-cd-sap-cloud-integration/blob/main/.github/CONTRIBUTING.md) if you want to take part in this project. As I am a beginner myself, beginners are welcome.

## License
See the [LICENSE](LICENSE) file for details

## 🌱 Support the Project

*If you've found my work helpful in your project, please consider buying me a coffee to show your support!*

<a href="https://www.buymeacoffee.com/nesun3" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>


<hr>
<p align="center">
Developed with ❤️ in India 🇮🇳 
</p>

