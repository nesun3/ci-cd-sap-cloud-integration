import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.MarkupBuilder

def Message processData(Message message) {

    def body = message.getBody(java.io.Reader)

    Writer writer = new StringWriter()
    def indentPrinter = new IndentPrinter(writer, ' ')
    def builder = new MarkupBuilder(indentPrinter)

    def prop = message.getProperties()
    List resourcesAll = prop.get("resourcesAll")

    builder.Resources{
        resourcesAll.each{ resource->
            Resource{
                Name(resource.Name)
                ResourceType(resource.ResourceType)
                Path(resource.Path)
            }
        }
    }

    message.setBody(writer.toString())
    return message
}
