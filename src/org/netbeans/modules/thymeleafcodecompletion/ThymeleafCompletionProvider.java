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
     * Creates a task that performs a query of the given type on the given
     * component. If query is not of COMPLETION_QUERY_TYPE returns null For more
     * check CopletionProvider javadoc.
     *
     * @param queryType Type of the query. It can be one of the
     * COMPLETION_QUERY_TYPE, COMPLETION_ALL_QUERY_TYPE,
     * DOCUMENTATION_QUERY_TYPE, or TOOLTIP_QUERY_TYPE (but not their
     * combination).
     * @param jTextComponent A text component where the query should be
     * performed.
     * @return A task performing the query.
     */
    @Override
    public CompletionTask createTask(int queryType, JTextComponent jTextComponent) {
        if (queryType != CompletionProvider.COMPLETION_QUERY_TYPE) {
            return null;
        }
        return new AsyncCompletionTask(getAsyncCompletionQuery(), jTextComponent);
    }

    /**
     * Determines whether text typed in a document should automatically pop up
     * the code completion window. This method is called by the code completion
     * infrastructure only to check whether text that has just been typed into a
     * text component triggers an automatic query invocation. For more check
     * CopletionProvider javadoc.
     *
     * @param jTextComponent A component in which the text was typed.
     * @param typedText Typed text.
     * @return Any combination of the COMPLETION_QUERY_TYPE,
     * COMPLETION_ALL_QUERY_TYPE, DOCUMENTATION_QUERY_TYPE, and
     * TOOLTIP_QUERY_TYPE values, or zero if no query should be automatically
     * invoked.
     */
    @Override
    public int getAutoQueryTypes(JTextComponent jTextComponent, String typedText) {
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

                final StyledDocument styledDocument = (StyledDocument) document;
                String filter = null;
                int startOffset = caretOffset - 1;

                try {
                    final String currentTag = CompletionUtils.getCurrentTagName(styledDocument, caretOffset);
                    if (currentTag.isEmpty()) {
                        completionResultSet.finish();
                        return;
                    }
                    final int lineStartOffset = CompletionUtils.getRowFirstNonWhite(styledDocument, caretOffset);
                    final char[] line = styledDocument.getText(lineStartOffset, caretOffset - lineStartOffset).toCharArray();
                    final int whiteOffset = CompletionUtils.getIndexOfLastSpace(line);
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
                    if (!CompletionUtils.insideAttribute((StyledDocument) document, caretOffset)) {
                        for (String attribute : ThymeleafData.getThymeleafAttributes()) {
                            if (!attribute.isEmpty() && filterMatchThymeleafAttribute(attribute, filter)) {
                                completionResultSet.addItem(new ThymeleafCompletionItem(attribute, startOffset, caretOffset));
                            }
                        }
                    } else {
                        if (filter.contains("=\"")) {
                            int index = filter.indexOf("=\"") + 2;
                            filter = filter.substring(index);
                            startOffset = startOffset + index;
                        }
                        for (String method : ThymeleafData.getThymeleafMethods()) {
                            if (!method.isEmpty() && filterMatchThymeleafMethod(method, filter)) {
                                completionResultSet.addItem(new ThymeleafCompletionItem(method, startOffset, caretOffset));
                            }
                        }
                    }
                }
                completionResultSet.finish();
            }

            private boolean filterMatchThymeleafAttribute(String attribute, String filter) {
                return attribute.startsWith(filter)
                        || attribute.startsWith("th:" + filter)
                        || attribute.startsWith("layout:" + filter)
                        || attribute.startsWith("data-th" + filter)
                        || attribute.startsWith("data-layout" + filter);
            }

            private boolean filterMatchThymeleafMethod(String method, String filter) {
                return method.startsWith(filter) || method.startsWith("#" + filter);
            }
        };
    }
}
