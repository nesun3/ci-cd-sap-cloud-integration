import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def prop = message.getProperties()
    def countPackageAll = prop.get("countPackageAll")
    def countIntegrationFlowAll = prop.get("countIntegrationFlowAll")
    def lastSuccessfulRunDateTime = prop.get("LAST_SUCCESSFUL_RUN_DATETIME")
    def interfaceExecutionTimeStamp = prop.get("INTERFACE_EXECUTION_TIMESTAMP")

    StringBuilder csvFile = new StringBuilder()
    csvFile << "Total Packages Extracted,"<< countPackageAll << "\n"
    csvFile << "Total iFlows Extracted,"<< countIntegrationFlowAll << "\n"
    csvFile << "Last Successful Run DateTime,"<< lastSuccessfulRunDateTime << "\n"
    csvFile << "Interface Execution TimeStamp,"<< interfaceExecutionTimeStamp << "\n"
    csvFile << "\n\n\n"

    def uploadFileType = prop.get("UPLOAD_FILE_TYPE")
    if(uploadFileType.trim().toUpperCase() =="UNZIP"){

        def fieldOrder = ["ArtifactType", "Id", "Version", "Task", "GitHubPath", "UploadStatus", "ResponseCode", "ResponseStatus", "ResponseBody"]
        csvFile <<"ArtifactType,Id,Version,Task,GitHubPath,UploadStatus,ResponseCode,ResponseStatus,ResponseBody\n"

        List syncStatusHistory = prop.get("syncStatusHistory")
        syncStatusHistory.each { item ->
            def csvLine = fieldOrder.collect { field -> item[field] }.join(",")
            csvFile << csvLine << "\n"
        }
    }
    else{

        def fieldOrder = ["ArtifactType", "Id", "Version", "Task", "UploadStatus", "ResponseCode", "ResponseStatus", "ResponseBody"]
        csvFile <<"ArtifactType,Id,Version,Task,UploadStatus,ResponseCode,ResponseStatus,ResponseBody\n"

        List syncStatusHistory = prop.get("syncStatusHistory")
        syncStatusHistory.each { item ->
            def csvLine = fieldOrder.collect { field -> item[field] }.join(",")
            csvFile << csvLine << "\n"
        }
    }

    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog != null)
        messageLog.addAttachmentAsString('SUMMARY REPORT', csvFile.toString(), 'text/plain')

    return message
}

