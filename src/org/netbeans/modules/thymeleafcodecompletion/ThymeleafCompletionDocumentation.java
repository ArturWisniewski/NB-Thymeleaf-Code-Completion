package org.netbeans.modules.thymeleafcodecompletion;

import java.net.URL;
import javax.swing.Action;
import org.netbeans.spi.editor.completion.CompletionDocumentation;

/**
 * Class responsible for displaying documentation. 
 * @author Artur Wiśniewski
 */
public class ThymeleafCompletionDocumentation implements CompletionDocumentation {

    /**
     *  Completion Item containing Thymelef attribute.
     */
    private ThymeleafCompletionItem item;

    /**
     *
     * @param item TODO
     */
    public ThymeleafCompletionDocumentation(ThymeleafCompletionItem item) {
        this.item = item;
    }

    /**
     *
     * @return String
     */
    @Override
    public String getText() {
        return ThymeleaData.getDoc(item.getText());
    }

    /**
     *
     * @return null
     */
    @Override
    public URL getURL() {
        return null;
    }

    /**
     *
     * @param string TODO
     * @return null
     */
    @Override
    public CompletionDocumentation resolveLink(String string) {
        return null;
    }

    /**
     *
     * @return null
     */
    @Override
    public Action getGotoSourceAction() {
        return null;
    }

}
