package org.example.rules;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.drools.compiler.runtime.pipeline.impl.DroolsJaxbHelperProviderImpl;
import org.drools.core.command.runtime.process.StartProcessCommand;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;

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
		List<Command<?>> commands = new ArrayList<Command<?>>();
		BatchExecutionCommand bec = CommandFactory.newBatchExecution(commands);
		
		Command<?> fireAllRulesCommand = new FireAllRulesCommand();
		
		// Insert your desired fact objects here
		InsertObjectCommand iocSc = new InsertObjectCommand(sc);
		InsertObjectCommand iocSci = new InsertObjectCommand(sci);
		StartProcessCommand spc = new StartProcessCommand("com.redhat.coolstore.PriceProcess");
		
		// Set output name if desired
		iocSc.setOutIdentifier("shoppingCart");
		iocSci.setOutIdentifier("shoppingCartItem");
		spc.setOutIdentifier("myProcess");
		
		// Add your facts and rule fire method
		commands.add(iocSc);
		commands.add(iocSci);
		commands.add(spc);
		commands.add(fireAllRulesCommand);

		// approach 1 - works
		List<String> classNames = new ArrayList<String>();
		classNames.add("com.redhat.coolstore.ShoppingCart");
		classNames.add("com.redhat.coolstore.ShoppingCartItem");
		JAXBContext jaxbContext = DroolsJaxbHelperProviderImpl.createDroolsJaxbContext(classNames, null);
		Marshaller marshaller = jaxbContext.createMarshaller();

		StringWriter xml = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(bec, xml);

		System.out.println(xml);

		return xml.toString();
	}
	
	public static void main(String arg[]) throws Exception {
		RuleExecutionJAXBPayloadGenProcess.marshallxml();
	}
}
