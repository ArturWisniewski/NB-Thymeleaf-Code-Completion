package org.netbeans.modules.thymeleafcodecompletion;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;

/**
 * Helper methods for auto completion
 *
 * @author Artur Wiśniewski
 *
 */
public class CompletionUtils {

    /**
     * TODO: javadoc
     *
     * @param doc
     * @param offset
     * @return
     * @throws BadLocationException
     */
    static int getRowFirstNonWhite(StyledDocument doc, int offset)
            throws BadLocationException {
        Element lineElement = doc.getParagraphElement(offset);//elemetn import
        int start = lineElement.getStartOffset();
        while (start + 1 < lineElement.getEndOffset()) {
            try {
                if (doc.getText(start, 1).charAt(0) != ' ') {
                    break;
                }
            } catch (BadLocationException ex) {
                throw (BadLocationException) new BadLocationException(
                        "calling getText(" + start + ", " + (start + 1)
                        + ") on doc of length: " + doc.getLength(), start
                ).initCause(ex);
            }
            start++;
        }
        return start;
    }

    /**
     * TODO: javadoc
     *
     * @param line
     * @return
     */
    static int indexOfWhite(char[] line) {
        int i = line.length;
        while (--i > -1) {
            final char c = line[i];
            if (Character.isWhitespace(c)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns index of end of attribute
     * 
     * @param line array of chars from begin of attribute to end of line
     * @return index of end of attribute or -1 if something went wrong
     */
    static int indexOfAttributeEnd(char[] line) {
        for (int i = 0; i < line.length; i++) {
            final char c = line[i];
            if (Character.isWhitespace(c) || c == '>') {
                return i;
            }
        }
        return -1;
    }

}
