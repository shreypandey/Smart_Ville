from py4j.java_gateway import JavaGateway
import json
gateway = JavaGateway()
random = gateway.jvm.java.util.Random()
addition_app = gateway.entry_point

# Gets Data from Potholes Raw Table
rawData = addition_app.addition("{\"query\":\"query{\\n  PotHolesRaw{\\n    Id\\n    Latitude\\n    Longitude\\n    Acc_X\\n    Acc_Y\\n    Acc_Z\\n    UserId\\n  }\\n}\",\"variables\":null}")
testing=json.loads(rawData)
mainData=testing["data"]
mainData2=mainData["PotHolesRaw"]

for x in mainData2:
	print(x["Id"])