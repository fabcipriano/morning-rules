package br.com.facio;

import br.com.facio.rules.RulesEngine;
import br.com.facio.rules.model.Purchase;
import br.com.facio.rules.template.RuleTemplateExecutor;
import java.math.BigDecimal;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(RulesEngine.class);

    public static void main( String[] args ) {
        logger.info( "Begin Morning Rules testing Template..." );
        RuleTemplateExecutor template = new RuleTemplateExecutor();
        
        template.run();
        
        logger.info( "End Morning Rules..." );
    }

    private static void serializeRules(String[] args) {
        RulesEngine engine = new RulesEngine();
        if (args.length == 0) {
            logger.info( "--- CREATE AND SERIALIZE" );
            engine.serializeDefaultBase();
        } else {
            logger.info( "--- DESERIALIZE" );
            KieBase base = null;
            for (int i = 0; i < 100; i++) {
                base = engine.deserializeDefaultBase();
            }

            KieSession newKieSession = base.newKieSession();
            Purchase firstPurchase = new Purchase(new BigDecimal(666), 1, false);

            FactHandle insert = newKieSession.insert(firstPurchase);
            newKieSession.fireAllRules();
        }
    }
}
