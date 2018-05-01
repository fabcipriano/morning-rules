package br.com.facio.rules.template;

import br.com.facio.rules.model.Header;
import java.io.InputStream;

import org.drools.decisiontable.ExternalSpreadsheetCompiler;

/**
 *
 * @author fabiano
 */
public class RuleTemplateExecutor {

    private static final String XLS_FILE = "/templates/data/promocoes.xls";
    private static final String SIMPLE_TEMPLATE_FILE = "/templates/promocao.drt";

    public void run() {
        System.out.println("Running ....");
        final InputStream xlsStream = this.getClass().getResourceAsStream( XLS_FILE );
        final InputStream templateStream = this.getClass().getResourceAsStream( SIMPLE_TEMPLATE_FILE );
        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        String dlrContent = converter.compile(xlsStream, templateStream, 2, 2);

        System.out.println("DLR compiled.:\r\n" + dlrContent);

        // now create some test data
        Header h = new Header("Brasil", "Minas Gerais", "Uberaba");
    }

}
