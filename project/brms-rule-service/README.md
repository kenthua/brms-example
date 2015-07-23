JBoss BRMS Rule Services
========================

RuleExecutionXmlPayloadGen - Generates the XML payload expected by the JBoss BRMS Rule Exeuction Server.
The fact model you want to edit must be in the pom.xml, which means it needs to be in your local Maven repo as well.
Execute the POJO, Run As -> Java Application

Take the output string and use it in your desired REST client (i.e. SOAPUI, Chrome - Advanced Rest Client extension)

	<batch-execution>
	  <insert out-identifier="customer" return-object="true" entry-point="DEFAULT">
	    <com.redhat.test.brmstest.fact.Customer>
	      <accountCount>0</accountCount>
	      <firstName>John</firstName>
	      <lastName>Doe</lastName>
	    </com.redhat.test.brmstest.fact.Customer>
	  </insert>
	  <insert out-identifier="account" return-object="true" entry-point="DEFAULT">
	    <com.redhat.test.brmstest.fact.Account>
	      <balance>100.0</balance>
	      <customer reference="../../../insert/com.redhat.test.brmstest.fact.Customer"/>
	      <number>C-12345</number>
	      <type>CHECKING</type>
	    </com.redhat.test.brmstest.fact.Account>
	  </insert>
	  <fire-all-rules/>
	</batch-execution>

Sample CoolStore Execution Server payload

	<batch-execution>
	  <insert out-identifier="shoppingCart" return-object="true" entry-point="DEFAULT">
	    <com.redhat.coolstore.ShoppingCart>
	      <cartItemPromoSavings>0.0</cartItemPromoSavings>
	      <cartItemTotal>0.0</cartItemTotal>
	      <cartTotal>0.0</cartTotal>
	      <shippingPromoSavings>0.0</shippingPromoSavings>
	      <shippingTotal>0.0</shippingTotal>
	    </com.redhat.coolstore.ShoppingCart>
	  </insert>
	  <insert out-identifier="shoppingCartItem" return-object="true" entry-point="DEFAULT">
	    <com.redhat.coolstore.ShoppingCartItem>
	      <itemId>123</itemId>
	      <name>Test</name>
	      <price>10.0</price>
	      <promoSavings>0.0</promoSavings>
	      <quantity>1</quantity>
	      <shoppingCart reference="../../../insert/com.redhat.coolstore.ShoppingCart"/>
	    </com.redhat.coolstore.ShoppingCartItem>
	  </insert>
	  <insert out-identifier="shoppingCartItem" return-object="true" entry-point="DEFAULT">
	    <com.redhat.coolstore.ShoppingCartItem>
	      <itemId>321</itemId>
	      <name>Test2</name>
	      <price>30.0</price>
	      <promoSavings>0.0</promoSavings>
	      <quantity>1</quantity>
	      <shoppingCart reference="../../../insert/com.redhat.coolstore.ShoppingCart"/>
	    </com.redhat.coolstore.ShoppingCartItem>
	  </insert>
	  <start-process processId="com.redhat.coolstore.PriceProcess" out-identifier="process-out"/>
	  <fire-all-rules/>
	</batch-execution>


