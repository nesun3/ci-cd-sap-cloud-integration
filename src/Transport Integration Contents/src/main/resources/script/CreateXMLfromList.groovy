import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.MarkupBuilder

def Message integrationFlowUriAll(Message message) {

    def body = message.getBody(java.io.Reader)

    def prop = message.getProperties()
    List integrationFlowUriAll = prop.get("integrationFlowUriAll")

    Writer writer = new StringWriter()
    def indentPrinter = new IndentPrinter(writer, ' ')
    def builder = new MarkupBuilder(indentPrinter)

    builder.IntegrationFlows{
        integrationFlowUriAll.each{ uri->
            IntegrationFlow{
                PackageId(uri.packageId)
                URI(uri.integrationFlowUri)
            }
        }
    }

    message.setBody(writer.toString())
    return message
}

def Message valueMappingUriAll(Message message) {

    def body = message.getBody(java.io.Reader)

    def prop = message.getProperties()
    List valueMappingUriAll = prop.get("valueMappingUriAll")

    Writer writer = new StringWriter()
    def indentPrinter = new IndentPrinter(writer, ' ')
    def builder = new MarkupBuilder(indentPrinter)

    builder.ValueMappings{
        valueMappingUriAll.each{ uri->
            ValueMapping{
                PackageId(uri.packageId)
                URI(uri.valueMappingUri)
            }
        }
    }

    message.setBody(writer.toString())
    return message
}

def Message packageAll(Message message) {

    def body = message.getBody(java.io.Reader)

    def prop = message.getProperties()
    List packageAll = prop.get("packageAll")

    Writer writer = new StringWriter()
    def indentPrinter = new IndentPrinter(writer, ' ')
    def builder = new MarkupBuilder(indentPrinter)

    builder.Packages{
        packageAll.each{ pkg->
            Package{
                Id(pkg.packageId)
                Name(pkg.packageName)
                Version(pkg.packageVersion)
                URI(pkg.packageUri)
            }
        }
    }

    message.setBody(writer.toString())
    return message
}

def Message integrationFlowAll(Message message) {

    def body = message.getBody(java.io.Reader)

    Writer writer = new StringWriter()
    def indentPrinter = new IndentPrinter(writer, ' ')
    def builder = new MarkupBuilder(indentPrinter)

    def prop = message.getProperties()
    List integrationFlowAll = prop.get("integrationFlowAll")

    def integrationFlowId  = prop.get("INTEGRATION_FLOW_ID")
    if(integrationFlowId && integrationFlowId.toString().trim().length() != 0){

        def inputList = integrationFlowId.split(',')
        def filteredIntegrationFlows = []
        filteredIntegrationFlows = integrationFlowAll.findAll { integrationFlow -> inputList.contains(integrationFlow.integrationFlowId) }
        builder.IntegrationFlows{
            filteredIntegrationFlows.each{ iflow->
                IntegrationFlow{
                    PackageId(iflow.packageId)
                    Id(iflow.integrationFlowId)
                    Name(iflow.integrationFlowName)
                    Version(iflow.integrationFlowVersion)
                    URI(iflow.integrationFlowUri)
                }
            }
        }

    }
    else{

        builder.IntegrationFlows{
            integrationFlowAll.each{ iflow->
                IntegrationFlow{
                    PackageId(iflow.packageId)
                    Id(iflow.integrationFlowId)
                    Name(iflow.integrationFlowName)
                    Version(iflow.integrationFlowVersion)
                    URI(iflow.integrationFlowUri)
                }
            }
        }

    }

    message.setBody(writer.toString())
    return message
}

def Message valueMappingAll(Message message) {

    def body = message.getBody(java.io.Reader)

    Writer writer = new StringWriter()
    def indentPrinter = new IndentPrinter(writer, ' ')
    def builder = new MarkupBuilder(indentPrinter)

    def prop = message.getProperties()
    List valueMappingAll = prop.get("valueMappingAll")

    def valueMappingId  = prop.get("VALUE_MAPPING_ID")
    if(valueMappingId && valueMappingId.toString().trim().length() != 0){

        def inputList = valueMappingId.split(',')
        def filteredValueMappings = []
        filteredValueMappings = valueMappingAll.findAll { valuemapping -> inputList.contains(valuemapping.valueMappingId) }
        builder.ValueMappings{
            filteredValueMappings.each{ valuemap->
                ValueMapping{
                    PackageId(valuemap.packageId)
                    Id(valuemap.valueMappingId)
                    Name(valuemap.valueMappingName)
                    Version(valuemap.valueMappingVersion)
                    Description(valuemap.valueMappingDescription)
                    URI(valuemap.valueMappingUri)
                }
            }
        }

    }
    else{

        builder.ValueMappings{
            valueMappingAll.each{ valuemap->
                ValueMapping{
                    PackageId(valuemap.packageId)
                    Id(valuemap.valueMappingId)
                    Name(valuemap.valueMappingName)
                    Version(valuemap.valueMappingVersion)
                    Description(valuemap.valueMappingDescription)
                    URI(valuemap.valueMappingUri)
                }
            }
        }

    }

    message.setBody(writer.toString())
    return message
}