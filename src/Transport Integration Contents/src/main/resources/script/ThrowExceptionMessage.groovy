import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def prop = message.getProperties()

    def sourceHost = prop.get("SOURCE_HOST")
    if(sourceHost == null || sourceHost.trim().length() == 0 || sourceHost.trim() == '""')
        throw new Exception("\'Error: Source Host URL of Process Integration Runtime Service | API Plan is missing.\'")

    def targetHost = prop.get("TARGET_HOST")
    if(targetHost == null || targetHost.trim().length() == 0 || targetHost.trim() == '""')
        throw new Exception("\'Error: Target Host URL of Process Integration Runtime Service | API Plan is missing.\'")

    def srcOAuthCredentialAlias = prop.get("OAUTH_CREDENTIAL_SOURCE")
    if(srcOAuthCredentialAlias == null || srcOAuthCredentialAlias.trim().length() == 0 || srcOAuthCredentialAlias.trim() == '""')
        throw new Exception("\'Error: Name of the Source Credential created in Security Material for SAP CI ODATA V2 API authentication is missing.\'")

    def tgtOAuthCredentialAlias = prop.get("OAUTH_CREDENTIAL_TARGET")
    if(tgtOAuthCredentialAlias == null || tgtOAuthCredentialAlias.trim().length() == 0 || tgtOAuthCredentialAlias.trim() == '""')
        throw new Exception("\'Error: Name of the Target Credential created in Security Material for SAP CI ODATA V2 API authentication is missing.\'")

    def sourceSysName = prop.get("SOURCE_SYSTEM_NAME")
    if(sourceSysName == null || sourceSysName.trim().length() == 0 || sourceSysName.trim() == '""')
        throw new Exception("\'Error: Name of the Source Sub-Account is missing.\'")

    def targetSysName = prop.get("TARGET_SYSTEM_NAME")
    if(targetSysName == null || targetSysName.trim().length() == 0 || targetSysName.trim() == '""')
        throw new Exception("\'Error: Name of the Target Sub-Account is missing.\'")


    def integrationContentType  = prop.get("INTEGRATION_CONTENT_TYPE")
    if(integrationContentType == null || integrationContentType.trim().length() == 0 || integrationContentType.trim() == '""')
        throw new Exception("\'Error: Select Artifact Type to be transported is missing. Allowed values: pkg or ifl or vmp.\'")
    if(integrationContentType && integrationContentType.toString().trim().length() != 0){
        def validIntContentType = ["PKG", "IFL", "VMP"]
        if (!validIntContentType.contains(integrationContentType.trim().toUpperCase()))
            throw new Exception("\'Error: Select Artifact Type to be transported is not valid. Allowed values: pkg or ifl or vmp.\'")
    }

    def integrationFlowId  = prop.get("INTEGRATION_FLOW_ID")
    if(integrationFlowId && integrationFlowId.toString().trim().length() != 0){
        def integrationPackageId = prop.get("INTEGRATION_PACKAGE_ID")
        if(integrationPackageId == null || integrationPackageId.trim().length() == 0 || integrationPackageId.trim() == '""')
            throw new Exception("\'Error: Integration Package Id's of the corresponding Integration Flow Id's  is missing\'")
        if (integrationContentType != 'IFL') {
            throw new Exception("\'Error: If Integration Flow Id's exists, then set the value for Select Integration Content Type to be transported to ifl.\'")
        }
    }

    def valueMappingId  = prop.get("VALUE_MAPPING_ID")
    if(valueMappingId && valueMappingId.toString().trim().length() != 0){
        def integrationPackageId = prop.get("INTEGRATION_PACKAGE_ID")
        if(integrationPackageId == null || integrationPackageId.trim().length() == 0 || integrationPackageId.trim() == '""')
            throw new Exception("\'Error: Integration Package Id's of the corresponding Value Mapping Id's  is missing\'")
        if (integrationContentType != 'VMP') {
            throw new Exception("\'Error: If Value Mapping Id's exists, then set the value for Select Integration Content Type to be transported to vmp.\'")
        }
    }

    def integrationPackageId = prop.get("INTEGRATION_PACKAGE_ID")
    if(integrationPackageId && integrationPackageId.trim().length() != 0){
        def top = prop.get("TOP")
        if(top && top.trim().length() != 0)
            throw new Exception("\'Error: If Integration Package Id's exits then TOP should set as blank\'")
        def skip = prop.get("SKIP")
        if(skip && skip.trim().length() != 0)
            throw new Exception("\'Error: If Integration Package Id's exits then SKIP should set as blank\'")
    }

    def deploy = prop.get("DEPLOY")
    if(deploy == null || deploy.trim().length() == 0 || deploy.trim() == '""')
        throw new Exception("\'Error: Value for Deploy after importing to the Target Subaccount is missing. Allowed values: yes or no.\'")
    if(deploy && deploy.toString().trim().length() != 0){
        def validValues = ["YES", "NO"]
        if (!validValues.contains(deploy.trim().toUpperCase()))
            throw new Exception("\'Error: Value for Deploy after importing to the Target Subaccount is not valid. Allowed values: yes or no.\'")
    }
    if(integrationContentType.trim().toUpperCase() == "PKG" && deploy.trim().toUpperCase() == "YES")
       throw new Exception("\'Error: If Select Artifact Type is \'PKG\', then the value for \'DeployAfterImport\' should be set as no.\'")

    def teamsNotification = prop.get("MS_TEAMS_NOTIFICATION")
    if(teamsNotification == null || teamsNotification.trim().length() == 0 || teamsNotification.trim() == '""')
        throw new Exception("\'Error: Value for Send Notification to your Microsoft Teams channel is missing. Allowed Value: yes or no.\'")
    if(teamsNotification && teamsNotification.toString().trim().length() != 0){
        def validValues = ["YES", "NO"]
        if (!validValues.contains(deploy.trim().toUpperCase()))
            throw new Exception("\'Error: Value for Send Notification to your Microsoft Teams channel is not valid. Allowed values: yes or no.\'")
    }
    if(teamsNotification.trim().toUpperCase() == "YES"){
        def webhookAddress = prop.get("WEBHOOK_ADDRESS")
        if(webhookAddress == null || webhookAddress.trim().length() == 0 || webhookAddress.trim() == '""')
        throw new Exception("\'Error: Value for Incoming Webhook Address of your Microsoft Teams channel is missing.\'")
    }

    return message
}


