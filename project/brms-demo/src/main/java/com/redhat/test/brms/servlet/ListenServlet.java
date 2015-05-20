package com.redhat.test.brms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redhat.test.brms.model.Account;
import com.redhat.test.brms.model.Customer;
import com.redhat.test.brms.service.BRMSServiceImpl;



/**
 * Servlet implementation class ListenServlet
 */
@WebServlet("/ListenServlet")
public class ListenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(">> ListenServlet:doGet START");
		
		System.out.println(">> ListenServlet Generate Test Data");

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
		System.out.println(">> ListenServlet:doGet END");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
