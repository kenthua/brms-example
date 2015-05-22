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

