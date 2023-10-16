import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput

def Message getFileContents(Message message) {
    
    def prop = message.getProperties()
    def token = prop.get("GITHUB_ACCESS_TOKEN").toString()
    def authorization = "Bearer $token"

    message.setHeader("Content-Type", "application/json")
    message.setHeader("Accept", "application/vnd.github+json")
    message.setHeader("Authorization", authorization)

    return message
}
