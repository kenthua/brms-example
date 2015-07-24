package org.example.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.runtime.helper.BatchExecutionHelper;

import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;

public class RuleExecutionXmlPayloadGenProcess {
	public static void marshallxml() {
		
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
		
		Command<?> startProcessCommand = CommandFactory.newStartProcess("com.redhat.coolstore.PriceProcess", new HashMap());
		Command<?> fireAllRulesCommand = new FireAllRulesCommand();
		
		// Insert your desired fact objects here
		InsertObjectCommand iocSc = new InsertObjectCommand(sc);
		InsertObjectCommand iocSci = new InsertObjectCommand(sci);
		
		// Set output name if desired
		iocSc.setOutIdentifier("shoppingCart");
		iocSci.setOutIdentifier("shoppingCartItem");
		
		
		// Add your facts and rule fire method
		commands.add(iocSc);
		commands.add(iocSci);
		//commands.add(startProcessCommand);
		commands.add(fireAllRulesCommand);
		
		// Generate XML payload string
		String result = BatchExecutionHelper.newXStreamMarshaller().toXML(bec);
		System.out.println(result);
		
		String resultJson = BatchExecutionHelper.newJSonMarshaller().toXML(bec);
		System.out.println("\n\n" + resultJson);
	}
	
	public static void main(String arg[]) {
		RuleExecutionXmlPayloadGenProcess.marshallxml();
	}
}
