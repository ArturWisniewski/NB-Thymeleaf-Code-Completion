package org.netbeans.modules.thymeleafcodecompletion;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.openide.util.Exceptions;

/**
 * The basic implementation for providing code completion items. provide items
 * that are available to users when they invoke code completion in a text
 * document. The registered implementation will be used for documents that are
 * of the specified mime-type. In this case "text/html".
 *
 * @author Artur Wiśniewski
 */
@MimeRegistration(mimeType = "text/html", service = CompletionProvider.class)
public class ThymeleafCompletionProvider implements CompletionProvider {

    /**
     * Creates new Completion task but only if query type is
     * COMPLETION_QUERY_TYPE
     *
     * @param queryType type of the query
     * @param jTextComponent TODO
     * @return AsyncCompletionTask or null
     */
    @Override
    public CompletionTask createTask(int queryType, JTextComponent jTextComponent) {
        if (queryType != CompletionProvider.COMPLETION_QUERY_TYPE) {
            return null;
        }
        return new AsyncCompletionTask(getAsyncCompletionQuery(), jTextComponent);
    }

    /**
     * TODO: javadoc
     *
     * @param jTextComponent TODO
     * @param string TODO
     * @return int
     */
    @Override
    public int getAutoQueryTypes(JTextComponent jTextComponent, String string) {
        return 0;
    }

    /**
     * Logic behind adding completion items (attributes) to hint list
     *
     * @return
     */
    private AsyncCompletionQuery getAsyncCompletionQuery() {
        return new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, Document document, int caretOffset) {
                String filter = null;
                int startOffset = caretOffset - 1;
                try {
                    final StyledDocument styledDocument = (StyledDocument) document;
                    
                    //TODO: rewrite this in terms of tags not lines
                    
                    //completion only if caret in tag
                    if (!CompletionUtils.isCarretInsideHTMLTag(styledDocument, caretOffset)) {
                        completionResultSet.finish();
                        return;
                    }
                    final int lineStartOffset = CompletionUtils.getRowFirstNonWhite(styledDocument, caretOffset);

                    //  System.out.println( "DEBUG: co" + caretOffset + " lo" + lineStartOffset);
                    final char[] line = styledDocument.getText(lineStartOffset, caretOffset - lineStartOffset).toCharArray();
                    final int whiteOffset = CompletionUtils.indexOfWhite(line);
                    filter = new String(line, whiteOffset + 1, line.length - whiteOffset - 1);
                    if (whiteOffset > 0) {
                        startOffset = lineStartOffset + whiteOffset + 1;
                    } else {
                        startOffset = lineStartOffset;
                    }
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                }
                if (filter != null) {
                    String[] attributes = ThymeleaData.getThymeleafAttributes();
                    for (int i = 0; i < attributes.length; i++) {
                        final String attribute = attributes[i];
                        if (!attribute.equals("") && (attribute.startsWith(filter) || attribute.startsWith("th:" + filter))) {
                            completionResultSet.addItem(new ThymeleafCompletionItem(attribute, startOffset, caretOffset));
                        }
                    }
                }
                completionResultSet.finish();
            }
        };
    }

}
