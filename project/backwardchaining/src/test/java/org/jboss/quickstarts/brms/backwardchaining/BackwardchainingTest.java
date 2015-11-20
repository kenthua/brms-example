/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.quickstarts.brms.backwardchaining;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.jboss.quickstarts.brms.backwardchaining.Location;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class BackwardchainingTest {

    private static KieSession ksession;

   

    @BeforeClass
    public static void setup() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        ksession = kContainer.newKieSession();
        ksession.insert( new Location("Office", "House") );
        ksession.insert( new Location("Kitchen", "House") );
        ksession.insert( new Location("Knife", "Kitchen") );
        ksession.insert( new Location("Cheese", "Kitchen") );
        ksession.insert( new Location("Desk", "Office") );
        ksession.insert( new Location("Chair", "Office") );
        ksession.insert( new Location("Computer", "Desk") );
        ksession.insert( new Location("Draw", "Desk") );
    }

    @Test
    public void testGo1() {
    	ksession.insert( "go1" );
        ksession.fireAllRules();
        System.out.println("---");
    }

    @Test
    public void testGo2() {
    	ksession.insert( "go2" );
        ksession.fireAllRules();
        System.out.println("---");
    }

    @Test
    public void testGo3() {
    	ksession.insert( "go3" );
        ksession.fireAllRules();
        System.out.println("--- No key in the Office, now key is being added in the draw --- fireAllRule() ---");
        ksession.insert( new Location("Key", "Draw") );
        ksession.fireAllRules();
        System.out.println("---");
    }
    @Test
    public void testGo4() {
    	ksession.insert( "go4" );
        ksession.fireAllRules();
        System.out.println("---");
    }
   
    @Test
    public void testGo5() {
    	ksession.insert( "go5" );
        ksession.fireAllRules();
        System.out.println("---");
    }

}
