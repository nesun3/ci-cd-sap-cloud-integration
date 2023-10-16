import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def prop = message.getProperties()
    def artifactType = prop.get("INTEGRATION_CONTENT_TYPE")

    def messageLog = messageLogFactory.getMessageLog(message)
    StringBuilder csvFile = new StringBuilder()

    if(artifactType.toString().toUpperCase() == "IFLOW"){

        def fieldOrder = ["ArtifactType", "Id", "Task", "UploadStatus", "ResponseCode", "ResponseStatus", "ResponseBody"]
        csvFile <<"ArtifactType,Id,Task,UploadStatus,ResponseCode,ResponseStatus,ResponseBody\n"

        List uploadStatusHistory = prop.get("uploadStatusHistory")
        uploadStatusHistory.each { item ->
            def csvLine = fieldOrder.collect { field -> item[field] }.join(",")
            csvFile << csvLine << "\n"
        }

        if (messageLog != null)
            messageLog.addAttachmentAsString('SUMMARY REPORT', csvFile.toString(), 'text/plain')
    }
    else{

        def fieldOrder = ["ArtifactType", "Id", "ResourceName", "ResourceType", "Task", "UploadStatus", "ResponseCode", "ResponseStatus", "ResponseBody"]
        csvFile <<"ArtifactType,Id,ResourceName,ResourceType,Task,UploadStatus,ResponseCode,ResponseStatus,ResponseBody\n"

        List uploadStatusHistory = prop.get("uploadStatusHistory")
        uploadStatusHistory.each { item ->
            def csvLine = fieldOrder.collect { field -> item[field] }.join(",")
            csvFile << csvLine << "\n"
        }

        if (messageLog != null)
            messageLog.addAttachmentAsString('SUMMARY REPORT', csvFile.toString(), 'text/plain')
    }

    return message
}

