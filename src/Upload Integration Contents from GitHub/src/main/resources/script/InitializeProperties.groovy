import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def prop = message.getProperties()

    def summaryReport = new StringBuilder()
    message.setProperty('summaryReport', summaryReport)

    def  httpLogs = new StringBuilder()
    message.setProperty('httpLogs', httpLogs)

    def uploadStatusHistory  = []
    message.setProperty('uploadStatusHistory',uploadStatusHistory)

    def resourcesAll = []
    def integrationContentType  = prop.get("INTEGRATION_CONTENT_TYPE")
    if(integrationContentType.trim().toUpperCase() == "RESOURCES"){
        //def path = "IntegrationFlows/UnZip/TEST_01/src/main/resources/script/script1.js,IntegrationFlows/UnZip/TEST_01/src/main/resources/script/script1.groovy,IntegrationFlows/UnZip/TEST_01/src/main/resources/script/script2.groovy"
        def path = prop.get("GITHUB_PATH")
        def pathList = path.split(',')
        pathList.each{ item->
            def map =[:]
            def pathValues = item.split('src/main/resources')
            if(pathValues.size() == 2){
                def resource = pathValues[1].split('/')
                def fileNameWithExtension = resource[resource.size()-1]
                //println  "FileNameWithExtension: "+fileNameWithExtension
                def fileNameList = fileNameWithExtension.split('\\.')
                def fileExtension = fileNameList.size()==2?fileNameList[fileNameList.size()-1]:''
                //println "FileExtension: "+fileExtension
                def resourceType = ''
                switch(fileExtension) {
                    case 'xsl':
                        resourceType = 'xslt'
                        break
                    case 'gsh':
                        resourceType = 'groovy'
                        break;
                    case 'groovy':
                        resourceType = fileExtension
                        break;
                    case 'js':
                        resourceType = fileExtension
                        break;
                    case 'jar':
                        resourceType = fileExtension
                        break
                    case 'edmx':
                        resourceType = fileExtension
                        break
                    case 'mmap':
                        resourceType = fileExtension
                        break
                    case 'wsdl':
                        resourceType = fileExtension
                        break
                    case 'xsd':
                        resourceType = fileExtension
                        break
                    default:
                        resourceType = ''
                        break
                }
                if(resourceType != ''){
                    map.put("Name",fileNameWithExtension)
                    map.put("ResourceType",resourceType)
                    map.put("Path", item)
                    resourcesAll.add(map)
                }
            }
            else
                throw new Exception("\'Error: GitHub Path to the location of the file \"$item\" is incorrect.\'")

        }
        //println resourcesAll
    }

    message.setProperty('resourcesAll',resourcesAll)

    return message
}
