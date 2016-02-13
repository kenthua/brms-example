package org.example.rules;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.process.StartProcessCommand;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;

public class RuleExecutionXmlPayloadGenProcess {
	public static String marshallxml() {
		
		// Create your object here
		ShoppingCartItem sci = new ShoppingCartItem();
		ShoppingCart sc = new ShoppingCart();
		
		sc.setCartItemPromoSavings(0d);
		sc.setCartItemTotal(0d);
		sc.setCartTotal(0d);
		sc.setShippingPromoSavings(0d);
		sc.setShippingTotal(0d);
		
		sci.setItemId("123");
		sci.setName("Test");
		sci.setPrice(10.0);
		sci.setQuantity(1);
		sci.setShoppingCart(sc);
		sci.setPromoSavings(0d);
		
		
		// Command Setup
		List<GenericCommand<?>> commands = new ArrayList<GenericCommand<?>>();
		commands.add((GenericCommand<?>) CommandFactory.newInsert(sc, "sc-identifier"));
		commands.add((GenericCommand<?>) CommandFactory.newInsert(sci, "sci-identifier"));
		commands.add((GenericCommand<?>) CommandFactory.newFireAllRules("fire-identifier"));
		commands.add((GenericCommand<?>) CommandFactory.newStartProcess("com.redhat.coolstore.PriceProcess"));
		BatchExecutionCommand command = CommandFactory.newBatchExecution(commands, "defaultKieSession");
		
		
		// Generate XML payload string
	   	Marshaller marshaller = MarshallerFactory.getMarshaller(MarshallingFormat.XSTREAM, RuleExecutionKieClient.class.getClassLoader());
		String out = marshaller.marshall(command);
		System.out.println(out);
		
		// Generate JSON payload string
	   	Marshaller marshallerJson = MarshallerFactory.getMarshaller(MarshallingFormat.JSON, RuleExecutionKieClient.class.getClassLoader());
	   	String outJson = marshallerJson.marshall(command);
		System.out.println("\n\n" + outJson);
  	
		return out;
	}
	
	public static void main(String arg[]) {
		RuleExecutionXmlPayloadGenProcess.marshallxml();
	}
}
