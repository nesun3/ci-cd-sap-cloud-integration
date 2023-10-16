import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

	def body = message.getBody()
	def map = message.getProperties()
	
	def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog != null) 
        messageLog.addAttachmentAsString('Exception Log', body, 'text/plain')
    
	return message
}