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


public class RuleExecutionKieClientCoolstore {

	public static final String USERNAME = "user";
	public static final String PASSWORD = "passw0rd";
	public static final String SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";
	public static final String CONTAINER = "com.redhat:coolstore:2.0.1";
	public static final String KIESESSION = "coolstore-stateful-kie-session";

	public static void main(String args[])  {
		

		// configure client
		KieServicesClient client = configure(SERVER_URL, USERNAME, PASSWORD);
		RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

		// Create your object here
		ShoppingCartItem sci = new ShoppingCartItem();
		ShoppingCartItem sci2 = new ShoppingCartItem();

		ShoppingCart sc = new ShoppingCart();

		sci.setItemId("329299");
		sci.setName("Red Fedora");
		sci.setPrice(34.99);
		sci.setQuantity(1);
		sci.setShoppingCart(sc);
		sci.setPromoSavings(0d);

		sci2.setItemId("165954");
		sci2.setName("16 oz. Vortex Tumbler");
		sci2.setPrice(6.00);
		sci2.setQuantity(1);
		sci2.setShoppingCart(sc);
		sci2.setPromoSavings(0d);
		
		// KieCommands provides more commands than "CommandFactory.", such as newAgendaGroupSetFocus
		KieCommands cmdFactory = KieServices.Factory.get().getCommands();
		
		// Command Setup
		List<Command> commands = new ArrayList<Command>();
		//commands.add(cmdFactory.newInsert(sci, "sci-identifier"));
		//commands.add(cmdFactory.newInsert(sci2, "sci2-identifier"));
		commands.add(cmdFactory.newInsert(sci));
		commands.add(cmdFactory.newInsert(sci2));
		commands.add(cmdFactory.newInsert(sc, "sc-identifier"));
		commands.add(cmdFactory.newStartProcess("com.redhat.coolstore.PriceProcess"));
		commands.add(cmdFactory.newFireAllRules());
		BatchExecutionCommand command = cmdFactory.newBatchExecution(commands, KIESESSION);

		// marshal object to xml
		/* If you want to marshall commands to XML manually, then ALWAYS use Marshaller coming directly from the Kie Server API
		 when using native rest client, it is not necessary though
	    */
	   	Marshaller marshaller = MarshallerFactory.getMarshaller(MarshallingFormat.XSTREAM, RuleExecutionKieClientCoolstore.class.getClassLoader());
		String out = marshaller.marshall(command);
		System.out.println(out);

	   	Marshaller marshallerJson = MarshallerFactory.getMarshaller(MarshallingFormat.JSON, RuleExecutionKieClientCoolstore.class.getClassLoader());
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
		config.setMarshallingFormat(MarshallingFormat.XSTREAM);
		
		// switch to JSON by changing above ruleClient.executeCommands(CONTAINER, outJson);
		//config.setMarshallingFormat(MarshallingFormat.JSON);

		Set<Class<?>> extraClasses = new HashSet<>();
		extraClasses.add(ShoppingCart.class);
		extraClasses.add(ShoppingCartItem.class);
		config.addExtraClasses(extraClasses);

		//allClasses.add(org.drools.core.command.runtime.rule.FireAllRulesCommand.class);
		//allClasses.add(org.drools.core.command.runtime.rule.InsertObjectCommand.class);
		//allClasses.add(org.drools.core.common.DefaultFactHandle.class);
		//allClasses.add(org.drools.core.command.runtime.rule.GetObjectCommand.class);
		//config.addJaxbClasses(allClasses);
		
		return KieServicesFactory.newKieServicesClient(config);
	}
}
