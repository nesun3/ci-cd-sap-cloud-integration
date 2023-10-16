import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput

def Message processData(Message message) {

    def body = message.getBody(String)
    def prop = message.getProperties()
    List transportStatusHistory = prop.get("transportStatusHistory")

    def jsonMap = [
            type       : "message",
            attachments: [
                    [
                            contentType: "application/vnd.microsoft.card.adaptive",
                            content    : [
                                    type      : "AdaptiveCard",
                                    body      : [
                                            [
                                                    type  : "TextBlock",
                                                    size  : "Medium",
                                                    weight: "Bolder",
                                                    text  : "Transport Status History"
                                            ],
                                            [
                                                    type : "TextBlock",
                                                    text : "Please Check",
                                                    color: "attention",
                                                    wrap : true
                                            ],
                                            [
                                                    type             : "Table",
                                                    gridStyle        : "accent",
                                                    firstRowAsHeaders: true,
                                                    columns          : [
                                                            [width: "auto"],
                                                            [width: "auto"],
                                                            [width: "auto"],
                                                            [width: "auto"],
                                                            [width: "auto"]
                                                    ],
                                                    rows             : [
                                                            [
                                                                    type : "TableRow",
                                                                    cells: [
                                                                            [
                                                                                    type : "TableCell",
                                                                                    items: [
                                                                                            [
                                                                                                    type  : "TextBlock",
                                                                                                    text  : "Artifact Type",
                                                                                                    wrap  : true,
                                                                                                    weight: "Bolder"
                                                                                            ]
                                                                                    ]
                                                                            ],
                                                                            [
                                                                                    type : "TableCell",
                                                                                    items: [
                                                                                            [
                                                                                                    type  : "TextBlock",
                                                                                                    text  : "Id",
                                                                                                    wrap  : true,
                                                                                                    weight: "Bolder"
                                                                                            ]
                                                                                    ]
                                                                            ],
                                                                            [
                                                                                    type : "TableCell",
                                                                                    items: [
                                                                                            [
                                                                                                    type  : "TextBlock",
                                                                                                    text  : "Version",
                                                                                                    wrap  : true,
                                                                                                    weight: "Bolder"
                                                                                            ]
                                                                                    ]
                                                                            ],
                                                                            [
                                                                                    type : "TableCell",
                                                                                    items: [
                                                                                            [
                                                                                                    type  : "TextBlock",
                                                                                                    text  : "Transport Status",
                                                                                                    wrap  : true,
                                                                                                    weight: "Bolder"
                                                                                            ]
                                                                                    ]
                                                                            ]
                                                                    ],
                                                                    style: "accent"
                                                            ],
                                                            transportStatusHistory.collect{ transport ->
                                                                    [
                                                                            type : "TableRow",
                                                                            cells: [
                                                                                    [
                                                                                            type : "TableCell",
                                                                                            items: [
                                                                                                    [
                                                                                                            type: "TextBlock",
                                                                                                            text: transport.ArtifactType,
                                                                                                            wrap: true
                                                                                                    ]
                                                                                            ]
                                                                                    ],
                                                                                    [
                                                                                            type : "TableCell",
                                                                                            items: [
                                                                                                    [
                                                                                                            type: "TextBlock",
                                                                                                            text: transport.Id.toString(),
                                                                                                            wrap: true
                                                                                                    ]
                                                                                            ]
                                                                                    ],
                                                                                    [
                                                                                            type : "TableCell",
                                                                                            items: [
                                                                                                    [
                                                                                                            type: "TextBlock",
                                                                                                            text: transport.Version,
                                                                                                            wrap: true
                                                                                                    ]
                                                                                            ]
                                                                                    ],
                                                                                    [
                                                                                            type : "TableCell",
                                                                                            items: [
                                                                                                    [
                                                                                                            type: "TextBlock",
                                                                                                            text: transport.TransportStatus,
                                                                                                            wrap: true
                                                                                                    ]
                                                                                            ]
                                                                                    ]

                                                                            ],
                                                                            style: transport.TransportStatus == "Success" ? "accent" : "warning"
                                                                    ]
                                                            }
                                                    ]
                                            ]
                                    ],
                                    "\$schema": "http://adaptivecards.io/schemas/adaptive-card.json", // Use backslash to escape $
                                    version   : "1.5",
                                    msteams   : [
                                            width: "full"
                                    ]
                            ]
                    ]
            ]
    ]

    def jsonString = JsonOutput.toJson(jsonMap)

    //println JsonOutput.prettyPrint(jsonString)
    message.setHeader("Content-Type", "application/json")
    message.setBody(JsonOutput.prettyPrint(jsonString))

    return message
}