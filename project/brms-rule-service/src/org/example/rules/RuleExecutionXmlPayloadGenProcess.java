package org.example.rules;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.command.runtime.process.StartProcessCommand;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.runtime.helper.BatchExecutionHelper;

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
		
		// Generate XML payload string
		String result = BatchExecutionHelper.newXStreamMarshaller().toXML(bec);
		System.out.println(result);
		
		String resultJson = BatchExecutionHelper.newJSonMarshaller().toXML(bec);
		System.out.println("\n\n" + resultJson);
		
		return result;
	}
	
	public static void main(String arg[]) {
		RuleExecutionXmlPayloadGenProcess.marshallxml();
	}
}
