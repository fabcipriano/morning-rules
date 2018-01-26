package br.com.facio.rules;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fabianocp
 */
public class RulesEngine {
    
    private final Logger logger = LoggerFactory.getLogger(RulesEngine.class);


    public void serializeDefaultBase() {
        long start = System.currentTimeMillis();
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieBase kieBase = kc.getKieBase();
        
        try (FileOutputStream fos = new FileOutputStream("defaultRulesBase.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(kieBase);
            
        } catch (Exception e) {
            logger.error("failed to save rule base", e);
        } finally {
            long end = System.currentTimeMillis();
            logger.info("Rule base serialized in {} ms", (end - start));
        }
    }
}
