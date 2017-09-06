package org.netbeans.modules.thymeleafcodecompletion;

import java.net.URL;

import javax.swing.Action;

import org.netbeans.spi.editor.completion.CompletionDocumentation;

/**
 * Class responsible for displaying documentation in the documentation popup.
 *
 * @author Artur Wiśniewski
 */
public class ThymeleafCompletionDocumentation implements CompletionDocumentation {

    /**
     * Completion Item containing Thymeleaf attribute.
     */
    private final ThymeleafCompletionItem item;

    /**
     *
     * @param item item in code completion window list
     */
    public ThymeleafCompletionDocumentation(ThymeleafCompletionItem item) {
        this.item = item;
    }

    /**
     * Returns documentation text from ThymeleafData class displayed in
     * documentation popup.
     *
     * @return String documentation text or "No documentation available."
     * message
     */
    @Override
    public String getText() {
        String documentation = ThymeleafData.getDoc(item.getText());
        return documentation != null ? documentation : "No documentation available.";
    }

    /**
     * Returns a URL of the item's external representation that can be displayed
     * in an external browser or null if the item has no external
     * representation.
     *
     * @return null - not implemented
     */
    @Override
    public URL getURL() {
        return null;
    }

    /**
     * Returns a documentation item representing an object linked from the
     * item's HTML text.
     *
     * @param link
     * @return null - not implemented
     */
    @Override
    public CompletionDocumentation resolveLink(String link) {
        return null;
    }

    /**
     * Returns an action that opens the item's source representation in the
     * editor or null if the item has no source representation.
     *
     * @return null - not implemented
     */
    @Override
    public Action getGotoSourceAction() {
        return null;
    }

}
