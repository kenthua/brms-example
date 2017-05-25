package org.example.rules;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.command.impl.GenericCommand;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

import com.redhat.test.brms.Account;
import com.redhat.test.brms.Customer;



public class RuleExecutionPayloadGenCustomer {
	public static String marshallxml() {
		
		// Create your object here
		Account a1 = new Account();
		Account a2 = new Account();
		Customer c = new Customer();
		
		a1.setNumber("C-12345");
		a1.setBalance(100.0);
		a1.setType("CHECKING");
		
		a2.setNumber("C-67890");
		a2.setBalance(200.0);
		a2.setType("CHECKING");
		
		c.setAccountCount(0);
		c.setFirstName("John");
		c.setLastName("Doe");
		a1.setCustomer(c);
		a2.setCustomer(c);
		
		// KieCommands provides more commands than "CommandFactory.", such as newAgendaGroupSetFocus
		KieCommands cmdFactory = KieServices.Factory.get().getCommands();
		
		// Command Setup
		List<Command> commands = new ArrayList<Command>();
		//agenda group focus
		//commands.add((GenericCommand<?>) cmdFactory.newAgendaGroupSetFocus("test"));
		
		commands.add(cmdFactory.newInsert(c, "customer-identifier"));
		commands.add(cmdFactory.newInsert(a1, "account1-identifier"));
		commands.add(cmdFactory.newInsert(a2, "account2-identifier"));
		commands.add(cmdFactory.newFireAllRules("fire-identifier"));
		BatchExecutionCommand command = cmdFactory.newBatchExecution(commands, "defaultKieSession");
		
		// Generate XML payload string
	   	Marshaller marshaller = MarshallerFactory.getMarshaller(MarshallingFormat.XSTREAM, RuleExecutionKieClientCustomer.class.getClassLoader());
		String out = marshaller.marshall(command);
		System.out.println(out);
		
		// Generate JSON payload string
	   	Marshaller marshallerJson = MarshallerFactory.getMarshaller(MarshallingFormat.JSON, RuleExecutionKieClientCustomer.class.getClassLoader());
	   	String outJson = marshallerJson.marshall(command);
		System.out.println("\n\n" + outJson);
  	
		return out;
	}
	
	public static void main(String arg[]) {
		RuleExecutionPayloadGenCustomer.marshallxml();
	}
}
