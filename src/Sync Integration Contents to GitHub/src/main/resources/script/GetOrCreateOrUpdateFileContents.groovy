/*
Refer the link below to learn more about how to Create or update file contents in GitHub.
https://docs.github.com/en/rest/repos/contents?apiVersion=2022-11-28#create-or-update-file-contents
*/
import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput

def Message createFileContents(Message message) {
    //Headers
    def prop = message.getProperties()
    def token = prop.get("GITHUB_ACCESS_TOKEN").toString()
    def authorization = "Bearer $token"

    message.setHeader("Content-Type", "application/json")
    message.setHeader("Accept", "application/vnd.github+json")
    message.setHeader("Authorization", authorization)

    //Body parameters
    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def msg = prop.get("GITHUB_COMMIT_MESSAGE").toString()
    def version = prop.get("Version").toString()
    def commitMessage = msg + " - Version $version"

    def jsonOutput = JsonOutput.toJson([
            message: commitMessage,
            content: base64Content
    ])

    println JsonOutput.prettyPrint(jsonOutput)
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    return message
}

def Message updateFileContents(Message message) {
    //Headers
    def prop = message.getProperties()
    def token = prop.get("GITHUB_ACCESS_TOKEN").toString()
    def authorization = "Bearer $token"

    message.setHeader("Content-Type", "application/json")
    message.setHeader("Accept", "application/vnd.github+json")
    message.setHeader("Authorization", authorization)

    //Body parameters
    def body = message.getBody(String)
    def base64Content = body.replaceAll("\\r|\\n", "")

    def msg = prop.get("GITHUB_COMMIT_MESSAGE").toString()
    def version = prop.get("Version").toString()
    def commitMessage = msg + " - Version $version"
    def blobSHA = prop.get("blobSHA").toString()

    def jsonOutput = JsonOutput.toJson([
            message: commitMessage,
            content: base64Content,
            sha: blobSHA
    ])

    println JsonOutput.prettyPrint(jsonOutput)
    message.setBody(JsonOutput.prettyPrint(jsonOutput))
    return message
}

def Message getFileContents(Message message) {
    //Headers
    def prop = message.getProperties()
    def token = prop.get("GITHUB_ACCESS_TOKEN").toString()
    def authorization = "Bearer $token"

    message.setHeader("Content-Type", "application/json")
    message.setHeader("Accept", "application/vnd.github+json")
    message.setHeader("Authorization", authorization)

    def body = message.getBody(String)
    return message
}
