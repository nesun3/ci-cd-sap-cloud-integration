import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.text.SimpleDateFormat
import java.util.TimeZone

def Message processData(Message message) {

    def body = message.getBody(java.io.Reader)
    def jsonData = new JsonSlurper().parse(body)

    def prop = message.getProperties()
    List packageAll = prop.get("packageAll")
    List integrationFlowUriAll = prop.get("integrationFlowUriAll")
    List valueMappingUriAll = prop.get("valueMappingUriAll")

    jsonData.d.results.each{ result->

        if( result.Vendor!="SAP" && result.PartnerContent!=true && result.Name!="Continuous Integration and Delivery for SAP Cloud Integration"){

            def jsonDate = result.ModifiedDate
            def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            def date = new Date(Long.parseLong(jsonDate))
            def formattedDate = dateFormat.format(date)

            packageAll.push(["packageId": result.Id,"packageName": result.Name,"packageVersion": result.Version, "packageModifiedDate": formattedDate, "packageUri": result.__metadata.media_src])
            integrationFlowUriAll.push(["packageId": result.Id,"integrationFlowUri": result.IntegrationDesigntimeArtifacts.__deferred.uri])
            valueMappingUriAll.push(["packageId": result.Id,"valueMappingUri": result.ValueMappingDesigntimeArtifacts.__deferred.uri])
        }

    }
//    println packageAll
//    println JsonOutput.toJson(packageAll)
//    println JsonOutput.prettyPrint(JsonOutput.toJson(packageAll))

    def integrationPackageId = prop.get("INTEGRATION_PACKAGE_ID")
    if(integrationPackageId && integrationPackageId.toString().trim().length() != 0){
        def inputList = integrationPackageId.split(',')

        def filteredPackages = []
        filteredPackages = packageAll.findAll { integrationPackage -> inputList.contains(integrationPackage.packageId) }
        message.setProperty('packageAll', filteredPackages)

        def filteredIntegrationFlowUri = []
        filteredIntegrationFlowUri = integrationFlowUriAll.findAll { integrationFlow -> inputList.contains(integrationFlow.packageId) }
        message.setProperty('integrationFlowUriAll',filteredIntegrationFlowUri)

        def filteredValueMappingUri = []
        filteredValueMappingUri = valueMappingUriAll.findAll { valueMap -> inputList.contains(valueMap.packageId) }
        message.setProperty('valueMappingUriAll',filteredValueMappingUri)
    }

    return message
}
