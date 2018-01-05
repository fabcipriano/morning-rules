/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.facio.rules.model;

import java.math.BigDecimal;
import static junit.framework.TestCase.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.io.ResourceFactory;

/**
 *
 * @author fabiano
 */
public class PurchaseTest {

    public PurchaseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTotal method, of class Purchase.
     */
    @Test
    public void testDrools6Programming() {
        KieSession ksession = null;

        try {
            KieServices ks = KieServices.Factory.get();

            //magic (read kmodule.xml) to create container and bases
            //KieContainer kc = ks.getKieClasspathContainer();            
            ReleaseId releaseId1 = ks.newReleaseId("br.com.facio", "purchase", "0.0.1");
            KieFileSystem kfs = generateDynamicKModule(ks, releaseId1);
            
            KieBuilder kb = ks.newKieBuilder(kfs);
            kb.buildAll(); // kieModule is automatically deployed to KieRepository if successfully built.
            if (kb.getResults().hasMessages(Level.ERROR)) {
                throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
            }
            
            // Create a session and fire rules
            KieContainer kc = ks.newKieContainer(releaseId1);

            ksession = kc.newKieSession();

            ksession.addEventListener(new DebugAgendaEventListener());
            ksession.addEventListener(new DebugRuleRuntimeEventListener());

            //purchase > $15 and <= $25
            Purchase firstPurchase = new Purchase(new BigDecimal(16), 1, false);

            FactHandle insert = ksession.insert(firstPurchase);
            ksession.fireAllRules();

            //OLD Style
            //KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            //builder.add(ResourceFactory.newClassPathResource("discountRules.drl"), ResourceType.DRL);            
            //if (builder.hasErrors()) {
            //    throw new RuntimeException(builder.getErrors().toString());
            //}            
            //KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to execute Drools Engine");
        } finally {
            if (ksession != null) {
                ksession.dispose();
            }
        }

    }

    private KieFileSystem generateDynamicKModule(KieServices ks,  ReleaseId releaseId1) {
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.generateAndWritePomXML(releaseId1);
        kfs.writeKModuleXML(generateEmptyKModule(ks).toXML());
        kfs.write("src/main/resources/kiemodulemodel/dynamic.drl", ResourceFactory.newClassPathResource("discountRules.drl"));
        return kfs;
    }

    private KieModuleModel generateEmptyKModule(KieServices ks) {
        KieModuleModel kModuleModel = ks.newKieModuleModel();
        return kModuleModel;
    }

}
