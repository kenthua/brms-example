package org.example.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.drools.core.command.impl.GenericCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

import com.redhat.test.iteration.Account;
import com.redhat.test.iteration.Customer;


public class RuleExecutionKieClient {

	public static final String USERNAME = "brmsAdmin";
	public static final String PASSWORD = "passw0rd!";
	public static final String SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";
	public static final String CONTAINER = "Iteration";
	//public static final String KIESESSION = "myStateless";

	public static void main(String args[])  {
		

		// configure client

		KieServicesClient client = configure(SERVER_URL, USERNAME, PASSWORD);
		RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

		// generate commands
		List<GenericCommand<?>> commands = new ArrayList<GenericCommand<?>>();

		Customer c = new Customer();
		c.setId("1");
		c.setFirstName("John");
		List<Account> aList = new ArrayList<Account>();
 		c.setAccounts(aList);
 		Account a1 = new Account();
 		a1.setBalance(10.0);
 		a1.setType("CHECKING");
 		Account a2 = new Account();
 		a2.setBalance(20.0);
 		a2.setType("CHECKING");
 		aList.add(a1);
 		aList.add(a2);
		commands.add((GenericCommand<?>) CommandFactory.newInsert(c, "insert-identifier"));
		commands.add((GenericCommand<?>) CommandFactory.newFireAllRules("fire-identifier"));
		BatchExecutionCommand command = CommandFactory.newBatchExecution(commands, "defaultKieSession");

		// old way
		//String s = BatchExecutionHelper.newXStreamMarshaller().toXML(command);		
		//System.out.println(s);

		// marshal object to xml
		/* If you want to marshall commands to XML manually, then ALWAYS use Marshaller coming directly from the Kie Server API
		 when using native rest client, it is not necessary though
	   	Marshaller marshaller = MarshallerFactory.getMarshaller(MarshallingFormat.JSON, RESTClient.class.getClassLoader());
	    */
	   	Marshaller marshaller = MarshallerFactory.getMarshaller(MarshallingFormat.XSTREAM, RuleExecutionKieClient.class.getClassLoader());
		String out = marshaller.marshall(command);
		System.out.println(out);

	   	Marshaller marshallerJson = MarshallerFactory.getMarshaller(MarshallingFormat.JSON, RuleExecutionKieClient.class.getClassLoader());
		String outJson = marshallerJson.marshall(command);
		System.out.println("\n\n" + outJson);
		
		System.out.println("===server response====");
	
		ServiceResponse<String> response = ruleClient.executeCommands(CONTAINER, out);
		System.out.println(response.getResult());

	}

	public static KieServicesClient configure(String url, String username, String password) {
		
		//default marshalling format is JAXB
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
		
		//alternatives - uncomment below if changing the data format is desired
		
		//config.setMarshallingFormat(MarshallingFormat.XSTREAM);
		//config.setMarshallingFormat(MarshallingFormat.JSON);

	
		Set<Class<?>> allClasses = new HashSet<Class<?>>();
		allClasses.add(Account.class);
		allClasses.add(Customer.class);
		allClasses.add(org.drools.core.command.runtime.rule.FireAllRulesCommand.class);
		allClasses.add(org.drools.core.command.runtime.rule.InsertObjectCommand.class);
		allClasses.add(org.drools.core.common.DefaultFactHandle.class);
		allClasses.add(org.drools.core.command.runtime.rule.GetObjectCommand.class);
		config.addJaxbClasses(allClasses);
		return KieServicesFactory.newKieServicesClient(config);
		//
	}
}
