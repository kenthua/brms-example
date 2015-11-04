package com.redhat.test.brms.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.runtime.KieSession;

import com.redhat.test.brms.model.Account;
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
				
				
				
				ksession.addEventListener( new DefaultAgendaEventListener() {
				    public void beforeMatchFired(BeforeMatchFiredEvent event) {
				        super.beforeMatchFired( event );
				        System.out.println("Before Match: " + event );
				    }
					public void afterMatchFired(AfterMatchFiredEvent event) {
				        super.afterMatchFired( event );
				        System.out.println("After Match: " + event );
				    }
				    public void matchCreated(MatchCreatedEvent event) {
				        super.matchCreated(event);
				        System.out.println("Match Created: " + event );
				    }
				    public void matchCancelled(MatchCancelledEvent event) {
				        super.matchCancelled(event);
				        System.out.println("Match Cancelled: " + event );
				    }
				    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
				        super.afterRuleFlowGroupActivated(event);
				        System.out.println("After Rule Flow Group Activated: " + event );
				    }
				    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
				        super.afterRuleFlowGroupDeactivated(event);
				        System.out.println("After Rule Flow Group Deactivated: " + event );
				    }
				    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
				        super.agendaGroupPopped(event);
				        System.out.println("Agenda Group Popped: " + event );
				    }
				    public void agendaGroupPushed(AgendaGroupPushedEvent event) {
				        super.agendaGroupPushed(event);
				        System.out.println("Agenda Group Pushed: " + event );
				    }
				    
				});
				
				/*
				ksession.addEventListener(new DebugRuleRuntimeEventListener() {
					public void objectInserted(ObjectInsertedEvent event) {
						super.objectInserted(event);
						System.out.println("Object inserted: " + event);
					}
					public void objectUpdated(ObjectUpdatedEvent event) {
						super.objectUpdated(event);
						System.out.println("Object updated: " + event);
					}
					public void objectDeleted(ObjectDeletedEvent event) {
						super.objectDeleted(event);
						System.out.println("Object deleted: " + event);
					}					
				});
				*/
				ksession.addEventListener(new ProcessEventListener() {
					
					@Override
					public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beforeProcessStarted(ProcessStartedEvent arg0) {
						System.out.println("Before Process Started: " + arg0);
						
					}
					
					@Override
					public void beforeProcessCompleted(ProcessCompletedEvent arg0) {
						System.out.println("Before Process Completed: " + arg0);
						System.out.println("Before Process Completed PID: " + arg0.getProcessInstance().getId());

					}
					
					@Override
					public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterVariableChanged(ProcessVariableChangedEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterProcessStarted(ProcessStartedEvent arg0) {
						System.out.println("After Process Started: " + arg0);
						
					}
					
					@Override
					public void afterProcessCompleted(ProcessCompletedEvent arg0) {
						System.out.println("After Process Completed: " + arg0);
						System.out.println("After Process Completed ID: " + arg0.getProcessInstance().getId());
						System.out.println("After Process Completed Name: " + arg0.getProcessInstance().getProcessName());
						System.out.println("After Process Completed PID: " + arg0.getProcessInstance().getProcessId());
						System.out.println("After Process Completed State: " + arg0.getProcessInstance().getState());
					}
					
					@Override
					public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterNodeLeft(ProcessNodeLeftEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
				ksession.fireAllRules();				
		} finally {
			if ( ksession != null ) {
				ksession.dispose();
			}
		}
	
}
	public static void main(String[] args) {
		System.out.println(">> BRMSServiceImpl Generate Test Data");

		List<Customer> cs = new ArrayList<Customer>();
		
		Account a1 = new Account();
		a1.setNumber("C-12345");
		a1.setType(Account.CHECKING);
		a1.setBalance(0.0);
		
		Account a2 = new Account();
		a2.setNumber("S-12345");
		a2.setType(Account.SAVINGS);
		a2.setBalance(100.0);
		
		Account a3 = new Account();
		a3.setNumber("C-67890");
		a3.setType(Account.CHECKING);
		a3.setBalance(200.00);
		
		Customer c1 = new Customer();
		c1.setFirstName("John");
		c1.setLastName("Doe");
		c1.addAccount(a1);
		c1.addAccount(a2);
	
		Customer c2 = new Customer();
		c2.setFirstName("Jane");
		c2.setLastName("Doe");
		c2.addAccount(a3);
		
		
		
		cs.add(c1);	
		cs.add(c2);
		
		BRMSServiceImpl b = new BRMSServiceImpl();
		b.test(cs);
		System.out.println(">> BRMSServiceImpl END");
	}

}