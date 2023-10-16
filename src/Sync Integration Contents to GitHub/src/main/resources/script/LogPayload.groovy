import com.sap.gateway.ip.core.customdev.util.Message

def Message httpLogs(Message message) {

    def prop = message.getProperties()
    StringBuilder httpLogs = prop.get("httpLogs")
    StringBuilder logs = new StringBuilder()
    logs <<"HTTP_Receiver,HTTP_Method,HTTP_Request_Name,HTTP_Request_Uri,HTTP_Response_Status_Code,HTTP_Response_Status_Text,HTTP_Response_Body\n"
    logs <<httpLogs

    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog != null)
        messageLog.addAttachmentAsString('HTTP LOGS', logs.toString(), 'text/csv')

    return message
}
