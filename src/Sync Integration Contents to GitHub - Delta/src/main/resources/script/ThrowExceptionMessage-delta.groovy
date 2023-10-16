import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.SecureStoreService

def Message processData(Message message) {

    def prop = message.getProperties()

    def host = prop.get("HOST")
    if(host == null || host.trim().length() == 0 || host.trim() == '""')
        throw new Exception("\'Error: Host URL of Process Integration Runtime Service | API Plan is missing.\'")

    def oAuthCredentialAlias = prop.get("OAUTH_CREDENTIAL")
    if(oAuthCredentialAlias == null || oAuthCredentialAlias.trim().length() == 0 || oAuthCredentialAlias.trim() == '""')
        throw new Exception("\'Error: Name of the credential created in Security Material for SAP CI ODATA V2 API authentication is missing.\'")

    def githubOwner = prop.get("GITHUB_OWNER")
    if(githubOwner == null || githubOwner.trim().length() == 0 || githubOwner.trim() == '""')
        throw new Exception("\'Error: The account owner of Github Repository is missing.\'")

    def githubRepoName = prop.get("GITHUB_REPO_NAME")
    if(githubRepoName == null || githubRepoName.trim().length() == 0 || githubRepoName.trim() == '""')
        throw new Exception("\'Error: Name of the Github Repository without the .git extension is missing.\'")

    def githubAccessTokenAlias = prop.get("GITHUB_ACCESS_TOKEN")
    if(githubAccessTokenAlias == null || githubAccessTokenAlias.trim().length() == 0 || githubAccessTokenAlias.trim() == '""')
        throw new Exception("\'Error: Name of the credential created in Security Material to store Github Personal Access Token is missing.\'")

    def githubAccessToken = getGithubToken(githubAccessTokenAlias)
    message.setProperty("GITHUB_ACCESS_TOKEN", githubAccessToken)

    def githubCommitMsg = prop.get("GITHUB_COMMIT_MESSAGE")
    if(githubCommitMsg == null || githubCommitMsg.trim().length() == 0 || githubCommitMsg.trim() == '""')
        throw new Exception("\'Error: Github Commit message is missing.\'")

    def uploadFileType = prop.get("UPLOAD_FILE_TYPE")
    if(uploadFileType == null || uploadFileType.trim().length() == 0 || uploadFileType.trim() == '""')
        throw new Exception("\'Error: Value for Upload to GitHub as a \'ZIP\' or an \'UNZIP\' File is missing. Allowed values: zip,unzip.\'")
    if(uploadFileType && uploadFileType.toString().trim().length() != 0){
        def validValues = ["ZIP", "UNZIP"]
        if (!validValues.contains(uploadFileType.trim().toUpperCase()))
            throw new Exception("\'Error: Value for Upload to GitHub as a \'ZIP\' or an \'UNZIP\' File is not valid. Allowed values: zip,unzip.\'")
        else if(uploadFileType.trim().toUpperCase() =="ZIP"){
            message.setProperty("ZIP","YES")
            message.setProperty("UNZIP","NO")
        }
        else if(uploadFileType.trim().toUpperCase() =="UNZIP"){
            message.setProperty("ZIP","NO")
            message.setProperty("UNZIP","YES")
        }
    }

    def updateRunDateTimeFlag  = prop.get("UPDATE_RUN_DATETIME_FLAG")
    if(updateRunDateTimeFlag  && updateRunDateTimeFlag.toString().trim().length() != 0){
        def validValues = ["YES","NO"]
        if(!validValues.contains(updateRunDateTimeFlag.trim().toUpperCase()))
            throw new Exception("\'Error: Value for Manual Run DateTime Flag is not valid. Allowed values: yes,no.\'")
        if(updateRunDateTimeFlag.trim().toUpperCase() == "YES"){
            def updateRunDateTime = prop.get("UPDATE_RUN_DATETIME")
            if(updateRunDateTime == null || updateRunDateTime.trim().length() == 0 || updateRunDateTime.trim() == '""')
                throw new Exception("\'Error: Manual Run DateTime is missing, if Manual Run DateTime Flag is set as Yes.\'")
            if(updateRunDateTime.toString().trim().length() != 20)
                throw new Exception("\'Error: Manual Run DateTime should be in yyyy-MM-ddThh:mm:ssZ format only.\'")
        }
    }

    def integrationContentType  = prop.get("INTEGRATION_CONTENT_TYPE")
    if(integrationContentType == null || integrationContentType.trim().length() == 0 || integrationContentType.trim() == '""')
        throw new Exception("\'Error: Integration Content Type to Sync to GitHub is missing. Allowed values: all,pkg,ifl.\'")
    if(integrationContentType && integrationContentType.toString().trim().length() != 0){
        def validIntContentType = ["PKG", "IFL", "ALL"]
        def inputValues = integrationContentType.split(',')
        inputValues.each { value ->
            def trimmedValue = value.trim().toUpperCase()
            if (!validIntContentType.contains(trimmedValue))
                throw new Exception("\'Error: Value for Select Artifact Type to Sync to GitHub is not valid. Allowed values: all,pkg,ifl.\'")
        }
    }

    def integrationFlowId  = prop.get("INTEGRATION_FLOW_ID")
    if(integrationFlowId && integrationFlowId.toString().trim().length() != 0){
        def integrationPackageId = prop.get("INTEGRATION_PACKAGE_ID")
        if(integrationPackageId == null || integrationPackageId.trim().length() == 0 || integrationPackageId.trim() == '""')
            throw new Exception("\'Error: Integration Package Id's of the corresponding Integration Flow Id's  is missing.\'")
        def inputValues = integrationContentType.split(',').collect { it.trim().toUpperCase() }
        if (!inputValues.contains('ALL') && !inputValues.contains('IFL')) {
            throw new Exception("\'Error: If Integration Flow Id's exists, then Value for Select Artifact Type to Sync to GitHub must either contain all or ifl.\'")
        }
    }

    def integrationPackageId = prop.get("INTEGRATION_PACKAGE_ID")
    if(integrationPackageId && integrationPackageId.trim().length() != 0){
        def top = prop.get("TOP")
        if(top && top.trim().length() != 0)
            throw new Exception("\'Error: If Integration Package Id's exits then TOP should set as blank.\'")
        def skip = prop.get("SKIP")
        if(skip && skip.trim().length() != 0)
            throw new Exception("\'Error: If Integration Package Id's exits then SKIP should set as blank.\'")
    }

    def lastSuccessfulDateTime = prop.get("LAST_SUCCESSFUL_RUN_DATETIME")
    if(!lastSuccessfulDateTime)
        message.setProperty("LASTMODIFIEDDATE","EMPTY")


    def teamsNotification = prop.get("MS_TEAMS_NOTIFICATION")
    if(teamsNotification == null || teamsNotification.trim().length() == 0 || teamsNotification.trim() == '""')
        throw new Exception("\'Error: Value for Send Notification to your Microsoft Teams channel is missing. Allowed Value: yes or no.\'")
    if(teamsNotification && teamsNotification.toString().trim().length() != 0){
        def validValues = ["YES", "NO"]
        if (!validValues.contains(teamsNotification.trim().toUpperCase()))
            throw new Exception("\'Error: Value for Send Notification to your Microsoft Teams channel is not valid. Allowed values: yes or no.\'")
    }
    if(teamsNotification.trim().toUpperCase() == "YES"){
        def webhookAddress = prop.get("WEBHOOK_ADDRESS")
        if(webhookAddress == null || webhookAddress.trim().length() == 0 || webhookAddress.trim() == '""')
            throw new Exception("\'Error: Value for Incoming Webhook Address of your Microsoft Teams channel is missing.\'")
    }

    return message
}

String getGithubToken(String keyAlias)
{
    def secureStorageService =  ITApiFactory.getService(SecureStoreService.class, null)
    try
    {
        def secureParameter = secureStorageService.getUserCredential(keyAlias)
        return secureParameter.getPassword().toString()
    }
    catch(Exception e){
        throw new Exception("\'Error: Secure Parameter for Github Personal Access Token is not available\'")
    }
}

