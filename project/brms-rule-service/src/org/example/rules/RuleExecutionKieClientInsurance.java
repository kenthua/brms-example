package org.example.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;
import com.redhat.demos.insurance.Driver;
import com.redhat.demos.insurance.Policy;


public class RuleExecutionKieClientInsurance {

	public static final String USERNAME = "user";
	public static final String PASSWORD = "passw0rd";
	public static final String SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";
	public static final String CONTAINER = "com.redhat.demos:insurance:1.0.1";
	public static final String KIESESSION = "defaultKieStatefulSession";

	public static void main(String args[])  {
		
		// configure client
		KieServicesClient client = configure(SERVER_URL, USERNAME, PASSWORD);
		RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

		// Create your object here
		Driver driver = new Driver();
		Policy policy = new Policy();
		
		policy.applyDiscount(0);
		policy.setBasePrice(0);
		policy.setDiscountPercent(0);
		policy.setApproved(false);
		policy.setType("COMPREHENSIVE");
		
		driver.setName("John Test");
		driver.setAge(25);
		driver.setLocationRiskProfile("LOW");
		driver.setPriorClaims(0);

		// KieCommands provides more commands than "CommandFactory.", such as newAgendaGroupSetFocus
		KieCommands cmdFactory = KieServices.Factory.get().getCommands();
		
		// Command Setup
		List<Command> commands = new ArrayList<Command>();
		commands.add(cmdFactory.newInsert(driver));
		commands.add(cmdFactory.newInsert(policy, "policy-out"));
		commands.add(cmdFactory.newFireAllRules());
		BatchExecutionCommand command = cmdFactory.newBatchExecution(commands, KIESESSION);

		// marshal object to xml
		/* If you want to marshall commands to XML manually, then ALWAYS use Marshaller coming directly from the Kie Server API
		 when using native rest client, it is not necessary though
	    */
	   	Marshaller marshaller = MarshallerFactory.getMarshaller(MarshallingFormat.XSTREAM, RuleExecutionKieClientInsurance.class.getClassLoader());
		String out = marshaller.marshall(command);
		System.out.println(out);

	   	Marshaller marshallerJson = MarshallerFactory.getMarshaller(MarshallingFormat.JSON, RuleExecutionKieClientInsurance.class.getClassLoader());
		String outJson = marshallerJson.marshall(command);
		System.out.println("\n\n" + outJson);
		
		System.out.println("===server response====");
	
		ServiceResponse<String> response = ruleClient.executeCommands(CONTAINER, command);
		System.out.println(response.getResult());
		System.out.println(response.getMsg());

	}

	public static KieServicesClient configure(String url, String username, String password) {
		
		//default marshalling format is JAXB
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
		
		//alternatives - uncomment below if changing the data format is desired
		
		// switch to xstream by changing above ruleClient.executeCommands(CONTAINER, out);
		//config.setMarshallingFormat(MarshallingFormat.XSTREAM);
		
		// switch to JSON by changing above ruleClient.executeCommands(CONTAINER, outJson);
		config.setMarshallingFormat(MarshallingFormat.JSON);
	
		Set<Class<?>> extraClasses = new HashSet<>();
		extraClasses.add(Policy.class);
		extraClasses.add(Driver.class);
		config.addExtraClasses(extraClasses);
		
		return KieServicesFactory.newKieServicesClient(config);
	}
}
