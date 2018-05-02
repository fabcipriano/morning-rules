package br.com.facio.rules.template;

import br.com.facio.rules.model.Header;
import java.io.InputStream;
import java.io.StringReader;

import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

/**
 *
 * @author fabiano
 */
public class RuleTemplateExecutor {

    private static final String XLS_FILE = "/templates/data/promocoes.xls";
    private static final String SIMPLE_TEMPLATE_FILE = "/templates/promocao.drt";

    public void run() {
        System.out.println("Running ....");
        final InputStream xlsStream = this.getClass().getResourceAsStream(XLS_FILE);
        final InputStream templateStream = this.getClass().getResourceAsStream(SIMPLE_TEMPLATE_FILE);
        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        String dlrContent = converter.compile(xlsStream, templateStream, 2, 2);

        System.out.println("DLR compiled.:\r\n" + dlrContent);

        // now create some test data
        System.out.println(" ====== Running Template Rules ====== ");
        
        Header h = new Header("Brasil", "Minas Gerais", "Uberaba");        
        KieSession session = loadBaseAndReturnSession(dlrContent);
        session.insert(h);
        session.fireAllRules();
        System.out.println(" ====== End ====== ");
    }

    private KieSession loadBaseAndReturnSession(String drl) {
        KieServices ks = KieServices.Factory.get();
        KieContainer container = generateDynamicKModuleAndLoadDRL(ks, drl);

        return container.newKieSession();
    }

    private KieContainer generateDynamicKModuleAndLoadDRL(KieServices ks,  String drl) {
        ReleaseId releaseId1 = ks.newReleaseId("br.com.facio", "order", "0.1.0");
        KieFileSystem kfs = generateDynamicKModule(ks, releaseId1);
        kfs.write("src/main/resources/template/dynamic.drl", ResourceFactory.newReaderResource(new StringReader(drl)));
                       
        return compileDroolsBase(ks, kfs, releaseId1);
    }

    private KieContainer compileDroolsBase(KieServices ks, KieFileSystem kfs, ReleaseId releaseId1) throws RuntimeException {
        KieBuilder kb = ks.newKieBuilder(kfs);
        kb.buildAll(); 
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }

        KieContainer kc = ks.newKieContainer(releaseId1);
        return kc;
    }

    private KieFileSystem generateDynamicKModule(KieServices ks, ReleaseId releaseId1) {
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.generateAndWritePomXML(releaseId1);
        kfs.writeKModuleXML(generateEmptyKModule(ks).toXML());
        return kfs;
    }

    private KieModuleModel generateEmptyKModule(KieServices ks) {
        KieModuleModel kModuleModel = ks.newKieModuleModel();
        return kModuleModel;
    }

}
