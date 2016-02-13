package org.example.rules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.drools.core.command.impl.GenericCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;

public class RuleExecutionJAXBPayloadGenProcess {
	public static String marshallxml() throws ClassNotFoundException, JAXBException, IOException {
		
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

		// Generate JAXB payload string
		Set<Class<?>> allClasses = new HashSet<Class<?>>();
		allClasses.add(ShoppingCart.class);
		allClasses.add(ShoppingCartItem.class);

		Marshaller marshallerJaxb = MarshallerFactory.getMarshaller(allClasses, MarshallingFormat.JAXB, RuleExecutionJAXBPayloadGenProcess.class.getClassLoader());
		String out = marshallerJaxb.marshall(command);
		System.out.println(out);

		return out.toString();
	}
	
	public static void main(String arg[]) throws Exception {
		RuleExecutionJAXBPayloadGenProcess.marshallxml();
	}
}
