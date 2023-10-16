import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def prop = message.getProperties()
    def countPackageAll = prop.get("countPackageAll")
    def countIntegrationFlowAll = prop.get("countIntegrationFlowAll")
    def countValueMappingAll = prop.get("countValueMappingAll")
    def interfaceExecutionTimeStamp = prop.get("INTERFACE_EXECUTION_TIMESTAMP")

    StringBuilder csvFile = new StringBuilder()
    csvFile << "Total Packages Extracted,"<< countPackageAll << "\n"
    csvFile << "Total iFlows Extracted,"<< countIntegrationFlowAll << "\n"
    csvFile << "Total ValueMappings Extracted,"<< countValueMappingAll << "\n"
    csvFile << "Interface Execution TimeStamp,"<< interfaceExecutionTimeStamp << "\n"
    csvFile << "\n\n\n"

    def fieldOrder = ["ArtifactType", "Id", "Version", "Task", "TransportStatus", "ResponseCode", "ResponseStatus", "ResponseBody", "DeployStatus", "DeployResponse"]
    csvFile <<"ArtifactType,Id,Version,Task,TransportStatus,ResponseCode,ResponseStatus,ResponseBody,DeployStatus,DeployResponse\n"

    List transportStatusHistory = prop.get("transportStatusHistory")
    transportStatusHistory.each { item ->
        def csvLine = fieldOrder.collect { field -> item[field] }.join(",")
        csvFile << csvLine << "\n"
    }

    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog != null)
        messageLog.addAttachmentAsString('SUMMARY REPORT', csvFile.toString(), 'text/plain')

    return message
}

