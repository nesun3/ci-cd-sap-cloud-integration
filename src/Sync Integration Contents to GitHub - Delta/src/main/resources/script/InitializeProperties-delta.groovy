import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def prop = message.getProperties()

    def summaryReport = new StringBuilder()
    message.setProperty('summaryReport', summaryReport)

    def  httpLogs = new StringBuilder()
    message.setProperty('httpLogs', httpLogs)

    def packageAll = []
    message.setProperty('packageAll', packageAll)

    def integrationFlowUriAll = []
    message.setProperty('integrationFlowUriAll',integrationFlowUriAll)

    def integrationFlowAll = []
    message.setProperty('integrationFlowAll',integrationFlowAll)

    def syncStatusHistory  = []
    message.setProperty('syncStatusHistory',syncStatusHistory)

    return message
}
