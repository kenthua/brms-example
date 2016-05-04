package org.example.rules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.drools.core.command.impl.GenericCommand;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.KieCommands;
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
		
		// KieCommands provides more commands than "CommandFactory.", such as newAgendaGroupSetFocus
		KieCommands cmdFactory = KieServices.Factory.get().getCommands();
		
		// Command Setup
		List<GenericCommand<?>> commands = new ArrayList<GenericCommand<?>>();
		commands.add((GenericCommand<?>) cmdFactory.newInsert(sc, "sc-identifier"));
		commands.add((GenericCommand<?>) cmdFactory.newInsert(sci, "sci-identifier"));
		commands.add((GenericCommand<?>) cmdFactory.newFireAllRules("fire-identifier"));
		commands.add((GenericCommand<?>) cmdFactory.newStartProcess("com.redhat.coolstore.PriceProcess"));
		BatchExecutionCommand command = cmdFactory.newBatchExecution(commands, "defaultKieSession");

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
