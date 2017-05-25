package org.example.rules;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;

public class RuleExecutionPayloadGenProcessCoolstore {
	public static String marshallxml() {
		
		// Create your object here
		ShoppingCartItem sci = new ShoppingCartItem();
		ShoppingCart sc = new ShoppingCart();
				
		sci.setItemId("123");
		sci.setName("Test");
		sci.setPrice(10.0);
		sci.setQuantity(1);
		sci.setShoppingCart(sc);
		sci.setPromoSavings(0d);
		
		// KieCommands provides more commands than "CommandFactory.", such as newAgendaGroupSetFocus
		KieCommands cmdFactory = KieServices.Factory.get().getCommands();
		
		// Command Setup
		List<Command> commands = new ArrayList<Command>();
		commands.add((Command) cmdFactory.newInsert(sc, "sc-identifier"));
		commands.add((Command) cmdFactory.newInsert(sci, "sci-identifier"));
		commands.add((Command) cmdFactory.newFireAllRules("fire-identifier"));
		commands.add((Command) cmdFactory.newStartProcess("com.redhat.coolstore.PriceProcess"));
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
		RuleExecutionPayloadGenProcessCoolstore.marshallxml();
	}
}
