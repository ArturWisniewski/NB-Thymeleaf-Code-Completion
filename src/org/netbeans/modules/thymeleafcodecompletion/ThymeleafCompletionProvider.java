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

    //TODO: tags allowed attributes as hint
    //             no attributes inside attributes
    //             methods inside attributes only 
    //             xmlns inside html tag only
    //             if no xmlns in html tag no thymeleaf code completion
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
                    final String currentTag = CompletionUtils.getCurrentTagName(styledDocument, caretOffset);
                    //completion only if caret in tag
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
                    if (!CompletionUtils.insideAttributesValue((StyledDocument) document, caretOffset)) {
                        String[] attributes = ThymeleafData.getThymeleafAttributes();
                        for (int i = 0; i < attributes.length; i++) {
                            final String attribute = attributes[i];
                            if (!attribute.equals("") && (attribute.startsWith(filter) || attribute.startsWith("th:" + filter) || attribute.startsWith("layout:" + filter))) {
                                completionResultSet.addItem(new ThymeleafCompletionItem(attribute, startOffset, caretOffset));
                            }
                        }
                    } else {
                        boolean voidDotOffset = false;
                        //if(filter.endsWith("=\"")){
                        if(filter.contains("=\"")){
                            filter = filter.substring(filter.indexOf("=\"")+2);
                            System.out.println("Filter:>"+filter+"<");
                            
                            voidDotOffset = true;
                        }
                        String[] methods = ThymeleafData.getThymeleafMethods();
                        for (int i = 0; i < methods.length; i++) {
                            final String method = methods[i];
                            if (!method.equals("") && (method.startsWith(filter) || method.startsWith("#" + filter))) {
                                ThymeleafCompletionItem tci  = new ThymeleafCompletionItem(method, startOffset, caretOffset);
                                if(voidDotOffset) tci.voidDotOffset();
                                completionResultSet.addItem(tci);
                            }
                        }
                    }
                }
                completionResultSet.finish();
            }
        };
    }

}
