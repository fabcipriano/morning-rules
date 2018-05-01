package br.com.facio.rules.template;

import br.com.facio.rules.model.Header;
import br.com.facio.rules.model.Order;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

/**
 *
 * @author fabiano
 */
public class RuleTemplateExecutor {

    public void run() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("TemplatesKS");
        KieBase kieBase = ksession.getKieBase();
        System.out.println("base.: " + kieBase.toString());
        
        // now create some test data
        Header h = new Header("Brasil", "Minas Gerais", "Uberaba");
        ksession.insert(new Order(h, true, false, 30));
//        final List<String> list = new ArrayList<String>();
//        ksession.setGlobal("list", list);

        ksession.fireAllRules();
    }

}
