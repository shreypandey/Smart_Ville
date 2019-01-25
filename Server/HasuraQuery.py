from py4j.java_gateway import JavaGateway
import json
gateway = JavaGateway()
random = gateway.jvm.java.util.Random()
addition_app = gateway.entry_point
value = addition_app.addition("{\"query\":\"query {\\n  hacktest {\\n    id\\n    name\\n  }\\n}\\n\",\"variables\":null}")
testing=json.loads(value)
testing1=testing["data"]
testing2=testing1["hacktest"]
print(testing2[1]["name"])