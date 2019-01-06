package org.netbeans.modules.thymeleafcodecompletion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JToolTip;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.completion.Completion;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 * Implements single item of the result list that can be displayed in the
 * completion popup.
 *
 * @author Artur Wiśniewski
 *
 */
public class ThymeleafCompletionItem implements CompletionItem {

    private static final Color FIELD_COLOR = UIManager.getColor("List.foreground");
    private static final Color FIELD_COLOR_SELECTED = UIManager.getColor("List.selectionForeground");
    private static final ImageIcon FIELD_ICON = new ImageIcon(ImageUtilities.loadImage("org/netbeans/modules/thymeleafcodecompletion/Thymeleaf.png"));
    private static final String INFO = "Thymeleaf";

    private final String text;
    private final int caretOffset;
    private final int dotOffset;

    /**
     * Constructor
     *
     * @param text attribute String
     * @param dotOffset position before word
     * @param caretOffset current caret position
     */
    public ThymeleafCompletionItem(String text, int dotOffset, int caretOffset) {
        this.text = text;
        this.caretOffset = caretOffset;
        this.dotOffset = dotOffset;
    }

    /**
     * Returns String containing Thymeleaf attribute
     *
     * @return attribute
     */
    public String getText() {
        return text;
    }

    /**
     * Gets invoked when user presses VK_ENTER key or when he double-clicks on
     * item. This method gets invoked from AWT thread.
     *
     * @param jTextComponent text component for which the completion was
     * invoked.
     */
    @Override
    public void defaultAction(JTextComponent jTextComponent) {
        try {
            StyledDocument doc = (StyledDocument) jTextComponent.getDocument();
            Caret caret = jTextComponent.getCaret();

            int endOffset = doc.getParagraphElement(dotOffset).getEndOffset();
            int indexOfWhite = CompletionUtils.getIndexOfAttributesEnd(doc.getText(dotOffset, endOffset - dotOffset).toCharArray());
            if (caretOffset != dotOffset) {
                doc.remove(dotOffset, indexOfWhite);
            }
            if (text.startsWith("th:") || text.startsWith("layout:") || text.startsWith("data")) {
                String attribute = text + "=\"\"";
                if (caretOffset == dotOffset) {
                    attribute += " ";
                }
                doc.insertString(dotOffset, attribute, null);
                caret.setDot(caret.getDot() - 1);
            } else {
                doc.insertString(dotOffset, text, null);
                if (text.startsWith("#") || text.startsWith("$")) {
                    caret.setDot(caret.getDot() - 1);
                }
            }
            Completion.get().hideAll();
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Get the preferred visual width of this item. The visual height of the
     * item is fixed to 16 points.
     *
     * @param graphics graphics that can be used for determining the preferred
     * width e.g. getting of the font metrics.
     * @param font default font used for rendering.
     * @return Integer width.
     */
    @Override
    public int getPreferredWidth(Graphics graphics, Font font) {
        return CompletionUtilities.getPreferredWidth(text, INFO, graphics, font);
    }

    /**
     * Render this item into the given graphics.
     *
     * @param graphics graphics to render the item into.
     * @param defaultFont default font used for rendering.
     * @param defaultColor default color used for rendering.
     * @param backgroundColor color used for background.
     * @param width width of the area to render into.
     * @param height height of the are to render into.
     * @param selected whether this item is visually selected in the list into
     * which the items are being rendered.
     */
    @Override
    public void render(Graphics graphics, Font defaultFont, Color defaultColor, Color backgroundColor, int width, int height, boolean selected) {
        CompletionUtilities.renderHtml(FIELD_ICON, text, INFO, graphics, defaultFont, (selected ? FIELD_COLOR_SELECTED : FIELD_COLOR), width, height, selected);
    }

    /**
     * Returns a task used to obtain a documentation associated with the item if
     * there is any.
     *
     * @return CompletionTask
     */
    @Override
    public CompletionTask createDocumentationTask() {
        return new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, Document document, int i) {
                completionResultSet.setDocumentation(new ThymeleafCompletionDocumentation(ThymeleafCompletionItem.this));
                completionResultSet.finish();
            }
        });
    }

    /**
     * Returns a task used to obtain a tooltip hint associated with the item if
     * there is any.
     *
     * @return CompletionTask
     */
    @Override
    public CompletionTask createToolTipTask() {
        return new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, Document document, int i) {
                JToolTip toolTip = new JToolTip();
                toolTip.setTipText("Press Enter to insert \"" + text + "\"");
                completionResultSet.setToolTip(toolTip);
                completionResultSet.finish();
            }
        });
    }

    /**
     * Process the key pressed when this completion item was selected in the
     * completion popup window. This method gets invoked from AWT thread.
     *
     * @param ke non-null key event of the pressed key. It should be consumed in
     * case the item is sensitive to the given key. The source of this event is
     * the text component to which the corresponding action should be performed.
     */
    @Override
    public void processKeyEvent(KeyEvent ke) {
    }

    /**
     * When enabled for the item the instant substitution should process the
     * item in the same way like when the item is displayed and Enter key gets
     * pressed by the user. Instant substitution is invoked when there would be
     * just a single item displayed in the completion popup window. The
     * implementation can invoke the defaultAction(JTextComponent) if necessary.
     * This method gets invoked from AWT thread.
     *
     * @param jTextComponent on-null text component for which the completion was
     * invoked.
     * @return true if the instant substitution was successfully done. false
     * means that the instant substitution should not be done for this item and
     * the completion item should normally be displayed.
     */
    @Override
    public boolean instantSubstitution(JTextComponent jTextComponent) {
        return false;
    }

    /**
     * Returns the item's priority. A lower value means a lower index of the
     * item in the completion result list.
     *
     * @return Integer index value.
     */
    @Override
    public int getSortPriority() {
        return 100;
    }

    /**
     * Returns a text used to sort items alphabetically.
     *
     * @return non-null character sequence containing text.
     */
    @Override
    public CharSequence getSortText() {
        return text;
    }

    /**
     * Returns a text used for finding of a longest common prefix after the TAB
     * gets pressed or when the completion is opened explicitly. The completion
     * infrastructure will evaluate the insert prefixes of all the items present
     * in the visible result and finds the longest common prefix. Generally the
     * returned text does not need to contain all the information that gets
     * inserted when the item is selected. For example in java completion the
     * field name should be returned for fields or a method name for methods
     * (but not parameters) or a non-FQN name for classes.
     *
     * @return non-null character sequence containing the insert prefix.
     * Returning an empty string will effectively disable the TAB completion as
     * the longest common prefix will be empty.
     */
    @Override
    public CharSequence getInsertPrefix() {
        return text;
    }

}
