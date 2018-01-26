package br.com.facio;

import br.com.facio.rules.RulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(RulesEngine.class);

    public static void main( String[] args ) {
        logger.info( "Begin Morning Rules..." );
        RulesEngine engine = new RulesEngine();
        engine.serializeDefaultBase();
        
        logger.info( "End Morning Rules..." );
    }
}
