package org.netbeans.modules.thymeleafcodecompletion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JToolTip;
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
import org.openide.util.ImageUtilities;
import org.openide.util.Exceptions;

/**
 * Implements single item of the result list that can be displayed in the
 * completion popup.
 *
 * @author Artur Wiśniewski
 *
 */
public class ThymeleafCompletionItem implements CompletionItem {

    private String text;
    private static Color fieldColor = Color.decode("0x0000B2");
    private static ImageIcon fieldIcon = new ImageIcon(ImageUtilities.loadImage("org/netbeans/modules/thymeleafcodecompletion/Thymeleaf.png"));
    private int caretOffset;
    private int dotOffset;
    private static final String info = "Thymeleaf";

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
     * TODO: javadoc
     *
     * @return String containing Thymeleaf attribute 
     */
    public String getText() {
        return text;
    }

    /**
     * Action executed after selecting code completion hint for example by
     * pressing enter. This method defines behavior of code replacement.
     *
     * @param jtc TODO
     */
    @Override
    public void defaultAction(JTextComponent jtc) {
        try {
            StyledDocument doc = (StyledDocument) jtc.getDocument();
            Caret caret = jtc.getCaret();

            int endOffset = doc.getParagraphElement(dotOffset).getEndOffset();
            int indexOfWhite = CompletionUtils.indexOfAttributeEnd(doc.getText(dotOffset, endOffset - dotOffset).toCharArray());
            if (caretOffset != dotOffset) {
                doc.remove(dotOffset, indexOfWhite);
            }
            if (text.startsWith("th:")||text.startsWith("layout:")) {
                String attribute = text + "=\"\"";
                if(caretOffset == dotOffset){
                    attribute+=" ";
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
     * Get preferred width of the item by knowing its left and right html texts.
     *
     * @param graphics TODO
     * @param font TODO
     * @return int width
     */
    @Override
    public int getPreferredWidth(Graphics graphics, Font font) {
        return CompletionUtilities.getPreferredWidth(text, info, graphics, font);
    }

    /**
     * TODO: javadoc
     *
     * @param graphics TODO
     * @param defaultFont TODO
     * @param defaultColor TODO
     * @param backgroundColor TODO
     * @param width TODO
     * @param height TODO
     * @param selected TODO
     */
    @Override
    public void render(Graphics graphics, Font defaultFont, Color defaultColor, Color backgroundColor, int width, int height, boolean selected) {
        CompletionUtilities.renderHtml(fieldIcon, text, info, graphics, defaultFont, (selected ? Color.white : fieldColor), width, height, selected);
    }

    /**
     * TODO: javadoc
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
     * TODO: javadoc
     *
     * @return AsyncCompletionTask
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
     * TODO: javadoc
     *
     * @param ke KeyEvent
     */
    @Override
    public void processKeyEvent(KeyEvent ke) {
    }

    /**
     * TODO: javadoc
     *
     * @param jtc TODO
     * @return boolean
     */
    @Override
    public boolean instantSubstitution(JTextComponent jtc) {
        return false;
    }

    /**
     * TODO: javadoc
     *
     * @return int
     */
    @Override
    public int getSortPriority() {
        return 0;
    }

    /**
     * TODO: javadoc
     *
     * @return CharSequence
     */
    @Override
    public CharSequence getSortText() {
        return text;
    }

    /**
     * TODO: javadoc
     *
     * @return CharSequence
     */
    @Override
    public CharSequence getInsertPrefix() {
        return text;
    }

}
