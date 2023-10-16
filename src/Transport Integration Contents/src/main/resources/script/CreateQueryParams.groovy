import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def prop = message.getProperties()
    def top = prop.get("TOP")
    def skip = prop.get("SKIP")

    def queryParams = new StringBuilder()
    queryParams <<"\$"<<"format=json"

    if(top== null || top.trim().length() == 0 || top.trim() == '""')
        queryParams <<""
    else{
        def topValue = top.toInteger()
        queryParams <<"&"<<"\$"<<"top=$topValue"
    }

    if(skip== null || skip.trim().length() == 0 || skip.trim() == '""')
        queryParams <<""
    else{
        def skipValue = skip.toInteger()
        queryParams <<"&"<<"\$"<<"skip=$skipValue"
    }


    message.setProperty('queryParams', queryParams.toString())
    return message
}

