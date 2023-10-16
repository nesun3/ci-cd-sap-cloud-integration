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
    List syncStatusHistory = prop.get("syncStatusHistory")

    def errorMsg = ""

    StringBuilder httpLogs = prop.get("httpLogs")
    //HTTP_Receiver,HTTP_Method,HTTP_Request_Name,HTTP_Request_Uri,HTTP_Response_Status_Code,HTTP_Response_Status_Text,HTTP_Response_Body

    if(httpResponseCode == 200 || httpResponseCode == 201)
        httpLogs << "$httpReceiver,$httpMethod,$requestName,\"$httpUri\",$httpResponseCode,$httpResponseText,,\n"
    else{
        if(httpReceiver =="SAP CI"){
            def exception = prop.get("CamelExceptionCaught")
            if (exception!=null){
                //HTTP Error
                if (exception.getClass().getCanonicalName().equals("org.apache.camel.component.ahc.AhcOperationFailedException")) {
                    def xmlData = new XmlSlurper().parseText(exception.getResponseBody())
                    errorMsg = xmlData.message.text()
                    httpLogs << "$httpReceiver,$httpMethod,$requestName,\"$httpUri\",$httpResponseCode,$httpResponseText,\"$errorMsg\"\n"
                }
            }
        }
        else{ // httpReceiver == "GitHub"
            def exception = prop.get("CamelExceptionCaught")
            if (exception!=null){
                //HTTP Error
                if (exception.getClass().getCanonicalName().equals("org.apache.camel.component.ahc.AhcOperationFailedException")) {
                    def jsonData = new JsonSlurper().parseText(exception.getResponseBody())
                    errorMsg = jsonData.message.toString().replaceAll("\n", "").replaceAll("\"", "'")
                    httpLogs << "$httpReceiver,$httpMethod,$requestName,\"$httpUri\",$httpResponseCode,$httpResponseText,\"$errorMsg\"\n"
                }
            }
        }
    }

    // Prepare Summary Report
    if (httpMethod.toString().toUpperCase() == "PUT" || httpMethod.toString().toUpperCase() == "POST") {

        def id = prop.get("Id")
        def version = prop.get("Version")
        def artifactType = prop.get("GITHUB_FOLDER")
        def status = (httpResponseCode == 200 || httpResponseCode == 201 || httpResponseCode == 202) ? "Success" : "Failed"
        def rspBody = (httpResponseCode == 200 || httpResponseCode == 201 || httpResponseCode == 202) ? "" : errorMsg
        def runMode = prop.get("RUN_MODE")
        def method = (runMode.toString().toUpperCase() == "CREATE") ? "CREATE" : "UPDATE"


        def uploadFileType = prop.get("UPLOAD_FILE_TYPE")
        if(uploadFileType.trim().toUpperCase() =="UNZIP"){
            def path = headers.get("CamelFileNameOnly")
            syncStatusHistory.push([
                    "ArtifactType"  : artifactType,
                    "Id"            : id,
                    "Version"       : version,
                    "Task"          : method,
                    "GitHubPath"    : path,
                    "UploadStatus"  : status,
                    "ResponseCode"  : httpResponseCode,
                    "ResponseStatus": httpResponseText,
                    "ResponseBody"  : rspBody
            ])
        }
        else{
            syncStatusHistory.push([
                    "ArtifactType"  : artifactType,
                    "Id"            : id,
                    "Version"       : version,
                    "Task"          : method,
                    "UploadStatus"  : status,
                    "ResponseCode"  : httpResponseCode,
                    "ResponseStatus": httpResponseText,
                    "ResponseBody"  : rspBody
            ])
        }

    }


    return message
}

