package org.example.rules;

import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class RuleExecutionKieClient {
	
	public static String callKieServer() {
				
		String payload = RuleExecutionXmlPayloadGenProcess.marshallxml();
		
		// for drools 6.2 / BRMS 6.1
		KieServicesConfiguration config =  KieServicesFactory.
                newRestConfiguration("http://localhost:8080/kie-server/services/rest/server",
                        "erics",
                        "jbossbrms1!");
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
 		ServiceResponse<String> response = client.executeCommands("coolstore", payload);
 		String strResponse = response.getResult();
 		System.out.println(">> Response: " + strResponse);
 		
 		return strResponse;

 		// for drools 6.3
 		// there will be a RuleServicesClient to allow for passing in the containerID and the batchexecutioncommand as is

 	    /*
 	    ReleaseId releaseId = new ReleaseId("com.redhat", "coolstore", "2.0.1");
 	    String CONTAINER_ID = "coolstore";

 		KieServicesConfiguration config =  KieServicesFactory.
                newRestConfiguration("http://localhost:8080/kie-server/services/rest/server",
                        "erics",
                        "jbossbrms1!");
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
		RuleServicesClient rc = kieServicesClient.getServicesClient(RuleServicesClient.class);
		ServiceResponse<String> response = rc.executeCommands(CONTAINER_ID, bec); 
 		String strResponse = response.getResult();
 		System.out.println(">> Response: " + strResponse);
		*/
 		
	}
	
	public static void main(String arg[]) {
		RuleExecutionKieClient.callKieServer();
	}
}
