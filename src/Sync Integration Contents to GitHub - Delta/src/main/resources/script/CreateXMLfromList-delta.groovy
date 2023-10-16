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

def Message packageAll(Message message) {

    def body = message.getBody(java.io.Reader)

    def prop = message.getProperties()
    List packageAll = prop.get("packageAll")
    List integrationFlowAll = prop.get("integrationFlowAll")

    Writer writer = new StringWriter()
    def indentPrinter = new IndentPrinter(writer, ' ')
    def builder = new MarkupBuilder(indentPrinter)

    def uniquePackageIds = integrationFlowAll.collect { it.packageId }.unique()
    builder.Packages {
        if(uniquePackageIds.size() > 0){
            def filteredPackages = packageAll.findAll { pkg -> uniquePackageIds.contains(pkg.packageId) }
            filteredPackages.each { pkg ->
                Package {
                    Id(pkg.packageId)
                    Name(pkg.packageName)
                    Version(pkg.packageVersion)
                    URI(pkg.packageUri)
                }
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
