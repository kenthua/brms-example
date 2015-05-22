package org.example.rules;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.runtime.helper.BatchExecutionHelper;

import com.redhat.test.brmstest.fact.Account;
import com.redhat.test.brmstest.fact.Customer;

public class RuleExecutionXmlPayloadGen {
	public static void marshallxml() {
		
		// Create your object here
		Account a = new Account();
		Customer c = new Customer();
		
		a.setNumber("C-12345");
		a.setBalance(100.0);
		a.setType("CHECKING");
		c.setAccountCount(0);
		c.setFirstName("John");
		c.setLastName("Doe");
		a.setCustomer(c);
		
		// Command Setup
		List<Command> commands = new ArrayList<Command>();
		BatchExecutionCommand bec = CommandFactory.newBatchExecution(commands);
		Command fireAllRulesCommand = CommandFactory.newFireAllRules();
		
		// Insert your desired fact objects here
		InsertObjectCommand iocCustomer = new InsertObjectCommand(c);
		InsertObjectCommand iocAccount = new InsertObjectCommand(a);
		
		// Set ouput name if desired
		iocCustomer.setOutIdentifier("customer");
		iocAccount.setOutIdentifier("account");
		
		// Add your facts and rule fire method
		commands.add(iocCustomer);
		commands.add(iocAccount);
		commands.add(fireAllRulesCommand);
		
		// Generate XML payload string
		String result = BatchExecutionHelper.newXStreamMarshaller().toXML(bec);
		System.out.println(result);
	}
	
	public static void main(String arg[]) {
		RuleExecutionXmlPayloadGen.marshallxml();
	}
}
