import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.text.SimpleDateFormat
import java.util.TimeZone

def Message integrationFlowAll(Message message) {

    def body = message.getBody(java.io.Reader)
    def jsonData = new JsonSlurper().parse(body)

    def prop = message.getProperties()
    List integrationFlowAll = prop.get("integrationFlowAll")
    def lastSuccessfulDateTime = prop.get("LAST_SUCCESSFUL_RUN_DATETIME")
    def lastRun = Date.parse("yyyy-MM-dd'T'HH:mm:ss",lastSuccessfulDateTime)

    jsonData.d.results.each{ result->

        if(result.Version != 'Active'){
            def jsonDate = result.ModifiedAt
            def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            def date = new Date(Long.parseLong(jsonDate))
            def formattedDate = dateFormat.format(date)
            def modifiedDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",formattedDate)

            if(modifiedDate > lastRun){
                integrationFlowAll.push([
                        "packageId": result.PackageId,
                        "integrationFlowId":result.Id,
                        "integrationFlowName": result.Name,
                        "integrationFlowVersion": result.Version,
                        "integrationFlowModifiedDate": formattedDate,
                        "integrationFlowUri": result.__metadata.media_src
                ])
            }

        }

    }
//    println integrationFlowAll
//    println JsonOutput.toJson(integrationFlowAll)
//    println JsonOutput.prettyPrint(JsonOutput.toJson(integrationFlowAll))

    return message
}





