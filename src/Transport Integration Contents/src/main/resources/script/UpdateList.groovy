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

    jsonData.d.results.each{ result->

        if(result.Version != 'Active'){
            def jsonDate = result.ModifiedAt
            def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            def date = new Date(Long.parseLong(jsonDate))
            def formattedDate = dateFormat.format(date)

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
//    println integrationFlowAll
//    println JsonOutput.toJson(integrationFlowAll)
//    println JsonOutput.prettyPrint(JsonOutput.toJson(integrationFlowAll))

    return message
}

def Message valueMappingAll(Message message) {

    def body = message.getBody(java.io.Reader)
    def jsonData = new JsonSlurper().parse(body)

    def prop = message.getProperties()
    List valueMappingAll = prop.get("valueMappingAll")

    jsonData.d.results.each{ result->

        if(result.Version != 'Draft'){
            valueMappingAll.push([
                    "packageId": result.PackageId,
                    "valueMappingId":result.Id,
                    "valueMappingName": result.Name,
                    "valueMappingVersion": result.Version,
                    "valueMappingDescription": result.Description,
                    "valueMappingUri": result.__metadata.media_src
            ])
        }

    }

//    println JsonOutput.toJson(valueMappingAll)
//    println JsonOutput.prettyPrint(JsonOutput.toJson(valueMappingAll))

    return message
}



