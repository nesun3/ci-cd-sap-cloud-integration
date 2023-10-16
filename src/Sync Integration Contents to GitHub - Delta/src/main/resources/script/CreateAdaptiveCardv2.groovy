import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput

def Message processData(Message message) {

    def prop = message.getProperties()
    def msgId = prop.get("SAP_MessageProcessingLogID")

    List syncStatusHistory = prop.get("syncStatusHistory")
    def uploadFileType = prop.get("UPLOAD_FILE_TYPE")
    if(uploadFileType.trim().toUpperCase() =="UNZIP"){
        def newList = syncStatusHistory.collect { historyMap ->
            [ArtifactType: historyMap.ArtifactType, Id: historyMap.Id, Version: historyMap.Version, UploadStatus: historyMap.UploadStatus]
        }
        syncStatusHistory = newList.unique()
    }

    def sendToTeams = syncStatusHistory.size()>0?"YES":"NO"
    message.setProperty("sendToTeams", sendToTeams)

    if(syncStatusHistory.size()>0){

        def mapHeader = [
                "type" : "TableRow",
                "cells": [
                        [
                                "type" : "TableCell",
                                "items": [
                                        [
                                                "type"  : "TextBlock",
                                                "text"  : "Artifact Type",
                                                "wrap"  : true,
                                                "weight": "bolder"
                                        ]
                                ]
                        ],
                        [
                                "type" : "TableCell",
                                "items": [
                                        [
                                                "type"  : "TextBlock",
                                                "text"  : "Id",
                                                "wrap"  : true,
                                                "weight": "bolder"
                                        ]
                                ]
                        ],
                        [
                                "type" : "TableCell",
                                "items": [
                                        [
                                                "type"  : "TextBlock",
                                                "text"  : "Version",
                                                "wrap"  : true,
                                                "weight": "bolder"
                                        ]
                                ]
                        ],
                        [
                                "type" : "TableCell",
                                "items": [
                                        [
                                                "type"  : "TextBlock",
                                                "text"  : "Status",
                                                "wrap"  : true,
                                                "weight": "bolder"
                                        ]
                                ]
                        ]
                ],
                "style": "accent"
        ]

        def tableRows = []
        tableRows.push(mapHeader)

        def mapRow =[:]
        syncStatusHistory.collect { sync ->

            mapRow = [
                    type : "TableRow",
                    cells: [
                            [
                                    type : "TableCell",
                                    items: [
                                            [
                                                    type: "TextBlock",
                                                    text: sync.ArtifactType,
                                                    wrap: true
                                            ]
                                    ]
                            ],
                            [
                                    type : "TableCell",
                                    items: [
                                            [
                                                    type: "TextBlock",
                                                    text: sync.Id.toString(),
                                                    wrap: true
                                            ]
                                    ]
                            ],
                            [
                                    type : "TableCell",
                                    items: [
                                            [
                                                    type: "TextBlock",
                                                    text: sync.Version,
                                                    wrap: true
                                            ]
                                    ]
                            ],
                            [
                                    type : "TableCell",
                                    items: [
                                            [
                                                    type: "TextBlock",
                                                    weight: "bolder",
                                                    color: "warning",
                                                    text: sync.UploadStatus
                                            ]
                                    ]
                            ]

                    ],
                    style: sync.UploadStatus == "Success" ? "good" : "attention"
            ]
            tableRows.push(mapRow)
        }

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
                                                        size  : "large",
                                                        weight: "bolder",
                                                        text  : "Sync artifacts from tenant to GitHub Status"
                                                ],
                                                [
                                                        type  : "TextBlock",
                                                        size  : "medium",
                                                        text  : "- SAP CI Message ID is **$msgId**. \r- Please check MPL in SAP CI for more details.",
                                                        wrap : true
                                                ],

                                                [
                                                        type             : "Table",
                                                        gridStyle        : "accent",
                                                        firstRowAsHeaders: true,
                                                        columns          : [
                                                                [width: 1],
                                                                [width: 1.5],
                                                                [width: 0.5],
                                                                [width: 0.5]
                                                        ],
                                                        rows             : tableRows
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

    }

    return message
}