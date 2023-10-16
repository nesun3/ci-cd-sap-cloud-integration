import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def body = message.getBody(String)
    def map = message.getProperties()
    def ex = map.get("CamelExceptionCaught")
    if (ex!=null){
        // an http adapter throws an instance of org.apache.camel.component.ahc.AhcOperationFailedException
        if (ex.getClass().getCanonicalName().equals("org.apache.camel.component.ahc.AhcOperationFailedException"))
            message.setProperty("FailedException","HTTP")
        else
            message.setProperty("FailedException","java.lang.Exception")
    }
    return message
}
