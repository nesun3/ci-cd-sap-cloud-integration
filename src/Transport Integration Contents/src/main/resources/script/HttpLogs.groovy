import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {

    def body = message.getBody(String)

    def headers = message.getHeaders()
    def httpResponseCode = headers.get("CamelHttpResponseCode")
    def httpResponseText = headers.get("CamelHttpResponseText")

    def prop = message.getProperties()
    def httpReceiver = prop.get("httpReceiver")
    def requestName = prop.get("requestName")
    def httpMethod = prop.get("httpMethod")
    def httpUri = prop.get("httpUri")
    List transportStatusHistory = prop.get("transportStatusHistory")

    def errorMsg =""

    // Write HTTP Logs
    StringBuilder httpLogs = prop.get("httpLogs")
    //HTTP_Receiver,HTTP_Method,HTTP_Request_Name,HTTP_Request_Uri,HTTP_Response_Status_Code,HTTP_Response_Status_Text,HTTP_Response_Body

    if (httpResponseCode == 200 || httpResponseCode == 201 || httpResponseCode == 202)
        httpLogs << "$httpReceiver,$httpMethod,$requestName,\"$httpUri\",$httpResponseCode,$httpResponseText,,\n"
    else {
        def exception = prop.get("CamelExceptionCaught")
        if (exception != null) {
            //HTTP Error
            if (exception.getClass().getCanonicalName().equals("org.apache.camel.component.ahc.AhcOperationFailedException")) {
                def xmlData = new XmlSlurper().parseText(exception.getResponseBody())
                errorMsg = xmlData.message.text()
                httpLogs << "$httpReceiver,$httpMethod,$requestName,\"$httpUri\",$httpResponseCode,$httpResponseText,\"$errorMsg\"\n"
            }
        }

    }

    // Prepare Summary Report
    if(httpMethod.toString().toUpperCase() == "PUT" || httpMethod.toString().toUpperCase() == "POST"){

        def id = prop.get("Id")
        def version = prop.get("Version")
        def artifactType = prop.get("INTEGRATION_CONTENT_TYPE")
        def isDeployed = prop.get("isDeployed")

        if(isDeployed == "false"){

            def status = (httpResponseCode == 200 || httpResponseCode == 201 || httpResponseCode == 202)?"Success":"Failed"
            def method
            if(artifactType.toString().toUpperCase() == "VMP"){
                def valueMapExist = prop.get("valueMapExist")
                method = valueMapExist =="false"?"Import":"Update"
            }
            else
                method = (httpMethod.toString().toUpperCase() == "POST")?"Import":"Update"
            def type =  (artifactType.toString().toUpperCase() == "PKG")?"Integration Package":(artifactType.toString().toUpperCase() == "IFL")?"Integration Flow":"Value Mapping"
            def rspBody = (httpResponseCode == 200 || httpResponseCode == 201 || httpResponseCode == 202)?"":errorMsg

            transportStatusHistory.push([
                    "ArtifactType": type,
                    "Id": id,
                    "Version": version,
                    "Task": method,
                    "TransportStatus": status,
                    "ResponseCode": httpResponseCode,
                    "ResponseStatus": httpResponseText,
                    "ResponseBody": rspBody,
                    "DeployStatus":"",
                    "DeployResponse":""
            ])
        }
        else{
//            Update the corresponding "DeployStatus" & "DeployResponse" field in the List 'transportStatusHistory'
            def searchItem = transportStatusHistory.find { it.Id == id && it.Version == version }
            if (searchItem) {
                println "Item with Id = $id found:"
//                println searchItem
                searchItem.DeployStatus = httpResponseCode == 202?"Deployment Started":"Failed"
                searchItem.DeployResponse = httpResponseText
            } else
                println "Item with Id = $id not found."
        }
    }

    return message
}

