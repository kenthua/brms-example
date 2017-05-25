JBoss BRMS Rule Services
========================

Tested on BRMS 6.4

KieClient for different scenarios
Payload Generator for different scenarios

Sample `spreadsheet.xls` for Decision Table

Client example from the following [article](https://access.redhat.com/solutions/1486613)  

Valid HTTP HEADER required for BRMS 6.2 via stand alone REST client [article](https://access.redhat.com/solutions/2129781)  

`X-KIE-ContentType = JSON`

`X-KIE-ContentType = XSTREAM`

`X-KIE-ContentType = XML`

Execute the POJO, Run As -> Java Application

Sample CoolStore Execution Server payload

	<batch-execution lookup="coolstore-stateful-kie-session">
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
	      <itemId>329299</itemId>
	      <name>Red Fedora</name>
	      <price>34.99</price>
	      <promoSavings>0.0</promoSavings>
	      <quantity>1</quantity>
	      <shoppingCart reference="../../../insert/com.redhat.coolstore.ShoppingCart"/>
	    </com.redhat.coolstore.ShoppingCartItem>
	  </insert>
	  <insert out-identifier="sci2-identifier" return-object="true" entry-point="DEFAULT">
	    <com.redhat.coolstore.ShoppingCartItem>
	      <itemId>165954</itemId>
	      <name>16 oz. Vortex Tumbler</name>
	      <price>6.0</price>
	      <promoSavings>0.0</promoSavings>
	      <quantity>1</quantity>
	      <shoppingCart reference="../../../insert/com.redhat.coolstore.ShoppingCart"/>
	    </com.redhat.coolstore.ShoppingCartItem>
	  </insert>
	  <fire-all-rules out-identifier="fire-identifier"/>
	  <start-process processId="com.redhat.coolstore.PriceProcess"/>
	</batch-execution>



