from py4j.java_gateway import JavaGateway
import json
from modeltrain import svmfun
import time
from threading import Thread

gateway = JavaGateway()
random = gateway.jvm.java.util.Random()
addition_app = gateway.entry_point

while True:
	# Gets Data from Potholes Raw Table
	rawData = addition_app.addition("{\"query\":\"query{\\n  PotHolesRaw{\\n    Id\\n    Latitude\\n    Longitude\\n    Acc_X\\n    Acc_Y\\n    Acc_Z\\n    UserId\\n  }\\n}\",\"variables\":null}")
	testing=json.loads(rawData)
	mainData=testing["data"]
	mainData2=mainData["PotHolesRaw"]

	rawList = []
	rawLoc = []
	rawId = []
	
	

	for x in mainData2:
		rawList.append([x["Acc_X"],x["Acc_Y"],x["Acc_Z"]])
		rawLoc.append([x["Latitude"],x["Longitude"]])
		a = str(x["Id"])
		
		

	output = svmfun(rawList)
	addition_app.addition("{\"query\":\"mutation delete_PotHolesRaw{\\n  delete_PotHolesRaw(\\n    where : {\\n      Id:{_lt:1000000000 }\\n    }\\n  ){\\n    affected_rows\\n  }\\n}\",\"variables\":null,\"operationName\":\"delete_PotHolesRaw\"}")
	for idx, val in enumerate(output):
	    if val == 1:
	    	# addition_app.addition("{\"query\":\"mutation insert_PotHolesMain{\\n  insert_PotHolesMain(objects: [\\n    {\\n      Latitude : \\\"%s\\\"%(rawLoc[idx][0])\\n      Longitude :\\\"%s\\\"%(rawLoc[idx][1]),\\n      Acc_X:\\\"%s \\\"%(rawList[idx][0]),\\n      Acc_Y:\\\"%s\\\"%(rawList[idx][1]),\\n      Acc_Z:\\\"%s\\\"%(rawList[idx][2]),\\n      Confidence: \\\"0.5\\\"\\n      \\n    \\n    }\\n  ]\\n  )\\n  {\\n    affected_rows\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"insert_PotHolesMain\"}")
	    	addition_app.addition("{\"query\":\"mutation insert_PotHolesMain{\\n  insert_PotHolesMain(objects: [\\n    {\\n      Latitude : \\\""+rawLoc[idx][0]+"\\\"\\n      Longitude :\\\""+rawLoc[idx][1]+"\\\",\\n      Acc_X:\\\""+rawList[idx][0]+"\\\",\\n      Acc_Y:\\\""+rawList[idx][1]+"\\\",\\n      Acc_Z:\\\""+rawList[idx][2]+"\\\",\\n      Confidence: \\\"0.5\\\"\\n      \\n    \\n    }\\n  ]\\n  )\\n  {\\n    affected_rows\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"insert_PotHolesMain\"}")
	    	testing=json.loads(rawData)
	    	mainData=testing["data"]
	print("Success")

	time.sleep(300)
