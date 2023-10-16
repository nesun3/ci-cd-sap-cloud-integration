import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput

def Message uploadPayloadIntegrationPackages(Message message) {

    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def jsonOutput = JsonOutput.toJson([
            PackageContent: base64Content
    ])

//    println JsonOutput.prettyPrint(jsonOutput)
    message.setHeader("Content-Type", "application/json")
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    return message
}

def Message postPayloadIntegrationFlows(Message message) {

    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def prop = message.getProperties()
    def name = prop.get("Name")
    def id = prop.get("Id")
    def packageId = prop.get("PackageId")

    def jsonOutput = JsonOutput.toJson([
             Name: name,
             Id: id,
             PackageId: packageId,
             ArtifactContent: base64Content
    ])

//    println JsonOutput.prettyPrint(jsonOutput)
    message.setHeader("Content-Type", "application/json")
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    return message
}

def Message putPayloadIntegrationFlows(Message message) {

    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def prop = message.getProperties()
    def name = prop.get("Name")

    def jsonOutput = JsonOutput.toJson([
            Name: name,
            ArtifactContent: base64Content
    ])

//    println JsonOutput.prettyPrint(jsonOutput)
    message.setHeader("Content-Type", "application/json")
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    return message
}

def Message uploadPayloadValueMapping(Message message) {

    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def prop = message.getProperties()
    def name = prop.get("Name")
    def id = prop.get("Id")
    def packageId = prop.get("PackageId")
    def description = prop.get("Description")

    def jsonOutput = JsonOutput.toJson([
            Name: name,
            Id: id,
            PackageId: packageId,
            Description: description,
            ArtifactContent: base64Content
    ])

//    println JsonOutput.prettyPrint(jsonOutput)
    message.setHeader("Content-Type", "application/json")
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    return message
}

