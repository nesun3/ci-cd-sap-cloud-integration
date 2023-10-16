import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*

def Message processData(Message message) {

    def messageLog = messageLogFactory.getMessageLog(message)
    def body = message.getBody(String)
    def prop = message.getProperties()
    def currentDateTime = prop.get("InterfaceExecutionTimeStamp").toString()
    def header = message.getHeaders()
    def msgId = header.SAP_MessageProcessingLogID

    List transportStatusHistory = prop.get("transportStatusHistory")
    def count = 0

    def sw = new StringWriter()
    def builder = new StreamingJsonBuilder(sw)

    builder {
        success('true')
        id(msgId)
        records(transportStatusHistory) { item ->
            count++
            ArtifactType(item.ArtifactType.toString())
            Id(item.Id.toString())
            Version(item.Version.toString())
            Task(item.Task.toString())
            TransportStatus(item.TransportStatus.toString())
            ResponseCode(item.ResponseCode.toString())
            ResponseStatus(item.ResponseStatus.toString())
            ResponseBody(item.ResponseBody.toString())
            DeployStatus(item.DeployStatus.toString())
            DeployResponse(item.DeployResponse.toString())
        }
        additionalInfo {
            InterfaceExecutionTimeStamp(currentDateTime)
            TotalRecords(count)
        }
    }

    message.setBody(JsonOutput.prettyPrint(sw.toString()))
    messageLog.addAttachmentAsString('API Response', JsonOutput.prettyPrint(sw.toString()), 'text/plain')

    return message
}