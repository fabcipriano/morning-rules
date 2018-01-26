package br.com.facio.rules;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
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
        long end = 0;
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieBase kieBase = kc.getKieBase();
        end = System.currentTimeMillis();
        logger.info("Rule CREATED in {} ms", (end - start));
        
        start = System.currentTimeMillis();
        try (FileOutputStream fos = new FileOutputStream("defaultRulesBase.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(kieBase);
            
        } catch (Exception e) {
            logger.error("failed to save rule base", e);
        } finally {
            end = System.currentTimeMillis();
            logger.info("Rule base SERIALIZED in {} ms", (end - start));
        }
    }

    public KieBase deserializeDefaultBase() {
        long start = 0;
        long end = 0;
        KieBase kieBase = null;
        
        start = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream("defaultRulesBase.ser");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            kieBase = (KieBase) ois.readObject();
            
        } catch (Exception e) {
            logger.error("failed to save rule base", e);
        } finally {
            end = System.currentTimeMillis();
            logger.info("Rule base DESERIALIZED in {} ms", (end - start));
        }
        
        return kieBase;
    }
}
