import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    StringBuilder builder = new StringBuilder()
    def headers = message.getHeaders()
    def messageLog = messageLogFactory.getMessageLog(message)

    headers.each {
        key, value -> builder << "${key}=${value}\n"
    }
    messageLog.addAttachmentAsString("Incoming HTTP Headers", builder.toString(), "text/plain")

    message.setProperty("SOURCE_HOST", headers.get("SOURCE_HOST")?:"")
    message.setProperty("TARGET_HOST", headers.get("TARGET_HOST")?:"")
    message.setProperty("OAUTH_CREDENTIAL_SOURCE", headers.get("OAUTH_CREDENTIAL_SOURCE")?:"")
    message.setProperty("OAUTH_CREDENTIAL_TARGET", headers.get("OAUTH_CREDENTIAL_TARGET")?:"")
    message.setProperty("SOURCE_SYSTEM_NAME", headers.get("SOURCE_SYSTEM_NAME")?:"")
    message.setProperty("TARGET_SYSTEM_NAME", headers.get("TARGET_SYSTEM_NAME")?:"")
    message.setProperty("DEPLOY", headers.get("DEPLOY")?:"")
    message.setProperty("INTEGRATION_CONTENT_TYPE", headers.get("INTEGRATION_CONTENT_TYPE")?:"")
    message.setProperty("INTEGRATION_PACKAGE_ID", headers.get("SOURCE_HOST")?:"")
    message.setProperty("INTEGRATION_FLOW_ID", headers.get("SOURCE_HOST")?:"")
    message.setProperty("VALUE_MAPPING_ID", headers.get("VALUE_MAPPING_ID")?:"")
    message.setProperty("TOP", headers.get("TOP")?:"")
    message.setProperty("SKIP", headers.get("SKIP")?:"")
    
    return message
}
