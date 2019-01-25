from py4j.java_gateway import JavaGateway
import json
from modeltrain import svmfun
gateway = JavaGateway()
random = gateway.jvm.java.util.Random()
addition_app = gateway.entry_point

# Gets Data from Potholes Raw Table
rawData = addition_app.addition("{\"query\":\"query{\\n  PotHolesRaw{\\n    Id\\n    Latitude\\n    Longitude\\n    Acc_X\\n    Acc_Y\\n    Acc_Z\\n    UserId\\n  }\\n}\",\"variables\":null}")
testing=json.loads(rawData)
mainData=testing["data"]
mainData2=mainData["PotHolesRaw"]

rawList = []
rawLoc = []
for x in mainData2:
	rawList.append([x["Acc_X"],x["Acc_Y"],x["Acc_Z"]])
	rawLoc.append([x["Latitude"],x["Longitude"]])

output = svmfun(rawList)

mainPotholequery = "{\"query\":\"mutation insert_PotHolesMain{\\n  insert_PotHolesMain(objects: [\\n    {\\n      Id1 : \\\"880\\\"\\n      Latitude : \\\"808\\\"\\n      Longitude :\\\"9823\\\",\\n      Acc_X:\\\"796 \\\",\\n      Acc_Y:\\\"903283\\\",\\n      Acc_Z:\\\"89271\\\",\\n      Confidence: \\\"0.5\\\"\\n      \\n    \\n    }\\n  ]\\n  )\\n  {\\n    affected_rows\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"insert_PotHolesMain\"}"

for idx, val in enumerate(output):
    if val == 1:
    	addition_app.addition("{\"query\":\"mutation insert_PotHolesMain{\\n  insert_PotHolesMain(objects: [\\n    {\\n      Latitude : \\\"808\\\"\\n      Longitude :\\\"9823\\\",\\n      Acc_X:\\\"796 \\\",\\n      Acc_Y:\\\"903283\\\",\\n      Acc_Z:\\\"89271\\\",\\n      Confidence: \\\"0.5\\\"\\n      \\n    \\n    }\\n  ]\\n  )\\n  {\\n    affected_rows\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"insert_PotHolesMain\"}")
    	testing=json.loads(rawData)
    	mainData=testing["data"]




