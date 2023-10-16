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
        throw new Exception("\'Error: Name of the Source Credential created in Security Material for SAP CI ODATA V2 API authentication is missing.\'")

    def githubOwner = prop.get("GITHUB_OWNER")
    if(githubOwner == null || githubOwner.trim().length() == 0 || githubOwner.trim() == '""')
        throw new Exception("\'Error: The account owner of Github Repository is missing\'")

    def githubRepoName = prop.get("GITHUB_REPO_NAME")
    if(githubRepoName == null || githubRepoName.trim().length() == 0 || githubRepoName.trim() == '""')
        throw new Exception("\'Error: Name of the Github Repository without the .git extension is missing\'")

    def githubAccessTokenAlias = prop.get("GITHUB_ACCESS_TOKEN")
    if(githubAccessTokenAlias == null || githubAccessTokenAlias.trim().length() == 0 || githubAccessTokenAlias.trim() == '""')
        throw new Exception("\'Error: Name of the credential created in Security Material to store Github Personal Access Token is missing\'")

    def githubAccessToken = getGithubToken(githubAccessTokenAlias)
    message.setProperty("GITHUB_ACCESS_TOKEN", githubAccessToken)

    def integrationContentType  = prop.get("INTEGRATION_CONTENT_TYPE")
    if(integrationContentType == null || integrationContentType.trim().length() == 0 || integrationContentType.trim() == '""')
        throw new Exception("\'Error: Select Artifact Type from GitHub Repo to be uploaded to the tenant is missing. Allowed values: iflow or resources.\'")
    if(integrationContentType && integrationContentType.toString().trim().length() != 0){
        def validIntContentType = ["IFLOW", "RESOURCES"]
        if (!validIntContentType.contains(integrationContentType.trim().toUpperCase()))
            throw new Exception("\'Error: Select Artifact Type from GitHub Repo to be uploaded to the tenant is not valid. Allowed values: iflow or resources.\'")
    }

    def integrationPackageId = prop.get("INTEGRATION_PACKAGE_ID")
    if(integrationPackageId == null || integrationPackageId.trim().length() == 0 || integrationPackageId.trim() == '""')
        throw new Exception("\'Error: Integration Package Id of the Integration flow to be uploaded from GitHub Repo to the SAP Cloud Integration tenant is missing\'")

    def integrationFlowId = prop.get("INTEGRATION_FLOW_ID")
    if(integrationFlowId == null || integrationFlowId.trim().length() == 0 || integrationFlowId.trim() == '""')
        throw new Exception("\'Error: Integration flow Id to be uploaded from GitHub Repo to the SAP Cloud Integration tenant is missing\'")

    def integrationFlowName = prop.get("INTEGRATION_FLOW_NAME")
    if(integrationFlowName == null || integrationFlowName.trim().length() == 0 || integrationFlowName.trim() == '""')
        throw new Exception("\'Error: Integration flow Name to be uploaded from GitHub Repo to the SAP Cloud Integration tenant is missing\'")

    
    def githubPath = prop.get("GITHUB_PATH")
    if(githubPath == null || githubPath.trim().length() == 0 || githubPath.trim() == '""')
        throw new Exception("\'Error: GitHub Path to the location of the file - relative to root of the repository is missing. Note: If SelectArtifactType = 'resources' only, then you can add multiple paths (comma-separated values)\'")

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


