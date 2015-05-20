package com.redhat.test.brms.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;

import org.kie.api.runtime.KieSession;

import com.redhat.test.brms.model.Customer;
import com.redhat.test.brms.util.BRMSUtil;

@Stateful
public class BRMSServiceImpl implements Serializable {

	private static final long serialVersionUID = 6821952169434330759L;

	
	//@Inject
	//private BRMSUtil brmsUtil; 
	

	public BRMSServiceImpl() {

	}
	
	public void test(List<Customer> cs) {
		KieSession ksession = null;

		try {			
				BRMSUtil bu = new BRMSUtil();
				ksession = bu.getStatefulSession();
				
				if(cs != null && cs.size() >0) {
					for(com.redhat.test.brms.model.Customer c : cs) {		
						com.redhat.test.brmstest.fact.Customer factCustomer = new com.redhat.test.brmstest.fact.Customer();
						factCustomer.setFirstName(c.getFirstName());
						factCustomer.setLastName(c.getLastName());
						factCustomer.setAccountCount(0);
						ksession.insert(factCustomer);
						if(c.getAccounts().size() > 0) {
							for(com.redhat.test.brms.model.Account a : c.getAccounts()) {
								com.redhat.test.brmstest.fact.Account factAccount = new com.redhat.test.brmstest.fact.Account();
								factAccount.setBalance(a.getBalance());
								factAccount.setType(a.getType());
								factAccount.setCustomer(factCustomer);
								factAccount.setNumber(a.getNumber());
								ksession.insert(factAccount);						
							}
						}
					}
				}
				ksession.fireAllRules();				
		} finally {
			if ( ksession != null ) {
				ksession.dispose();
			}
		}
	
}
		

}