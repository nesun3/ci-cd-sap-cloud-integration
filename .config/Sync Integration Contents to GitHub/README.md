# Configuration steps on SAP Cloud Integration

| Parameter Name        | Descriptions                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | Example                                                          | Required |
|-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------|----------|
| Event                 | Select either Create or Update file contents in GitHub.  <br>**Allowed Values:** create,update [can accept values in any case (upper, lower, or mixed)] <br>**Note:** Update should be used only when artifacts content already exist in GitHub Repository.                                                                                                                                                                                                                     | `CREATE`                                                         | YES      |
| GithHubAccessToken    | Set the name of the credential created in Security Material to store Github Personal Access Token.                                                                                                                                                                                                                                                                                                                                                                              | `GITHUB_ACCESS_TOKEN`                                            | YES      |
| GithubCommitMsg       | Commit message <br>**Note:** Short Summary (55 chars or less)                                                                                                                                                                                                                                                                                                                                                                                                                   | `Commit from SAP CI Package` <br> `Fixes #123, #124`             | YES      |
| GitHubOwnerName       | The account owner of Github Repository.                                                                                                                                                                                                                                                                                                                                                                                                                                         | `nesun3`                                                         | YES      |
| GitHubRepositoryName  | The name of the Github Repository without the .git extension.                                                                                                                                                                                                                                                                                                                                                                                                                   | `sap-cloud-integration-artifacts` <br>`integration-content-repo` | YES      |
| HostAddress           | Hostname of Process Integration Runtime Service  – API Plan. You find this value in BTP Cockpit of your Cloud Integration subscription.                                                                                                                                                                                                                                                                                                                                         | `https://<Host>.hana.ondemand.com`                               | YES      |
| IntegrationFlowId     | Integration flow IDs to be included in the extract (comma-separated values). This will filter only those specific Integration flow IDs. <br>**Note:** You must add only Id's and not name of the artifacts. <br>**[Required]** You must add Integration Package Id's of the corresponding Integration Flow Id's  in the parmeter name 'IntegrationPackageId'                                                                                                                    | `Test_02,Test_03,Test_04`                                        |          |
| IntegrationPackageId  | Integration Package IDs to be included in the extract (comma-separated values). This will filter only those specific Integration Package IDs.<br> **Note:** You must add only Id's and not name of the artifacts.                                                                                                                                                                                                                                                               | `TestPackage01,TestPackage04`                                    |          |
| OAuthCredential       | Set the name of the Credential created in Security Material for SAP CI ODATA V2 API authentication.                                                                                                                                                                                                                                                                                                                                                                             | `CI_ODATA_API`                                                   | YES      |
| SelectArtifactType    | Select Integration Content Type to Sync to GitHub. <br>**Allowed values:** all,pkg,ifl,vmp [can accept values in any case (upper, lower, or mixed)] <br>**Note:** <br>*ALL*- To select all artifacts type (*packages, iflows, value mappings*) to be synced.<br> *PKG* - To select only artifacts type (*packages*) to be synced.<br> *IFL* - To select only artifacts type (*iflows*) to be synced. <br>*VMP* - To select only artifacts type (*value mappings*) to be synced. | `ALL`<br>`IFL`<br>`PKG,IFL,VMP`                                  | YES      |
| SendTeamsNotification | Do you want to send Notification to your Microsoft Teams channel?  <br>**Allowed Values:** yes or no  [can accept values in any case (upper, lower, or mixed)]                                                                                                                                                                                                                                                                                                                  | `YES`                                                            |          |
| Skip                  | Skip the first n Packages from Sync (Leave Blank or give an Integer Value) <br>**Note:** Do not add any value in the parameter named IntegrationPackageId, IntegrationFlowId, ValueMappingId's if Skip or Top has values.                                                                                                                                                                                                                                                       | `0`                                                              |          |
| Top                   | Sync only the first n Packages (Leave Blank or give an Integer Value) <br>**Note:** Do not add any value in the parameter named IntegrationPackageId, IntegrationFlowId, ValueMappingId's if Skip or Top has values.                                                                                                                                                                                                                                                            | `3`                                                              |          |
| UploadFileType        | Upload to Github as Zip or Unzip File. <br>**Allowed Values:** zip,unzip [can accept values in any case (upper, lower, or mixed)].                                                                                                                                                                                                                                                                                                                                              | `UNZIP`                                                          | YES      |
| ValueMappingId's      | Value Mapping IDs to be included in the extract (comma-separated values). This will filter only those specific Value Mapping IDs. <br>**Note:** You must add only Id's and not name of the artifacts. <br>**[Required]** You must add Integration Package Id's of the corresponding Value Mapping Id's  in the parameter name 'IntegrationPackageId'.                                                                                                                           | `Pay_Frequency`<br>`Name_Suffix`                                 |          |
| WebhookAddress        | Incoming Webhook Address of your Microsoft Teams channel. <br>**Note:** *Required* only if parameter SendTeamsNotification is set as yes.                                                                                                                                                                                                                                                                                                                                       |                                                                  |          |