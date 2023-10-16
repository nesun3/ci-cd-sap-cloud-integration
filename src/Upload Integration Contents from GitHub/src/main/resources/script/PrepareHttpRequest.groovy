import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput

def Message postPayloadIntegrationFlow(Message message) {

    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def prop = message.getProperties()
    def name = prop.get("INTEGRATION_FLOW_NAME")
    def id = prop.get("INTEGRATION_FLOW_ID")
    def packageId = prop.get("INTEGRATION_PACKAGE_ID")

    def jsonOutput = JsonOutput.toJson([
             Name: name,
             Id: id,
             PackageId: packageId,
             ArtifactContent: base64Content
    ])

    println JsonOutput.prettyPrint(jsonOutput)
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    message.setHeader("Content-Type", "application/json")
    return message
}

def Message putPayloadIntegrationFlow(Message message) {

    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def prop = message.getProperties()
    def name = prop.get("INTEGRATION_FLOW_NAME")

    def jsonOutput = JsonOutput.toJson([
            Name: name,
            ArtifactContent: base64Content
    ])

    println JsonOutput.prettyPrint(jsonOutput)
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    message.setHeader("Content-Type", "application/json")
    return message
}

def Message postPayloadResources(Message message) {

    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def prop = message.getProperties()
    def name = prop.get("ResourceName")
    def resourceType = prop.get("ResourceType")

    def jsonOutput = JsonOutput.toJson([
            Name: name,
            ResourceType: resourceType,
            ResourceContent: base64Content
    ])

    println JsonOutput.prettyPrint(jsonOutput)
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    message.setHeader("Content-Type", "application/json")
    return message
}

def Message putPayloadResources(Message message) {

    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def jsonOutput = JsonOutput.toJson([
            ResourceContent: base64Content
    ])

    println JsonOutput.prettyPrint(jsonOutput)
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    message.setHeader("Content-Type", "application/json")
    return message
}