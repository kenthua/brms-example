JBoss BRMS Rule Services
========================

Tested on BRMS 6.2

KieClient created for XStream XML payload `RuleExecutionKieClient` calling BRMS Decision Server 

JAXB Generator created, `RuleExecutionJAXBPayloadGenProcess`, however a client needs to be written.

Client example from the following [article](https://access.redhat.com/solutions/1486613)  
Valid HTTP HEADER required for BRMS 6.2 via stand alone REST client [article](https://access.redhat.com/solutions/2129781)  

`RuleExecutionXmlPayloadGen` - Generates the XML payload expected by the JBoss BRMS Decision Server.
The fact model you want to edit must be in the pom.xml, which means it needs to be in your local Maven repo as well.
Execute the POJO, Run As -> Java Application

Sample CoolStore Execution Server payload

	<batch-execution lookup="defaultKieSession">
	  <insert out-identifier="sc-identifier" return-object="true" entry-point="DEFAULT">
	    <com.redhat.coolstore.ShoppingCart>
	      <cartItemPromoSavings>0.0</cartItemPromoSavings>
	      <cartItemTotal>0.0</cartItemTotal>
	      <cartTotal>0.0</cartTotal>
	      <shippingPromoSavings>0.0</shippingPromoSavings>
	      <shippingTotal>0.0</shippingTotal>
	    </com.redhat.coolstore.ShoppingCart>
	  </insert>
	  <insert out-identifier="sci-identifier" return-object="true" entry-point="DEFAULT">
	    <com.redhat.coolstore.ShoppingCartItem>
	      <itemId>123</itemId>
	      <name>Test</name>
	      <price>10.0</price>
	      <promoSavings>0.0</promoSavings>
	      <quantity>1</quantity>
	      <shoppingCart reference="../../../insert/com.redhat.coolstore.ShoppingCart"/>
	    </com.redhat.coolstore.ShoppingCartItem>
	  </insert>
	  <fire-all-rules out-identifier="fire-identifier"/>
	  <start-process processId="com.redhat.coolstore.PriceProcess"/>
	</batch-execution>



