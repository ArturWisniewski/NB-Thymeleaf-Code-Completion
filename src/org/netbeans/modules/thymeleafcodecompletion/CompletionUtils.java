package org.netbeans.modules.thymeleafcodecompletion;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import org.openide.util.Exceptions;

/**
 * Helper methods for auto completion
 *
 * @author Artur Wiśniewski
 *
 */
public class CompletionUtils {

    /**
     * Gets index of first not space/tab element in line where caret is or caret
     * position if non found before its location
     *
     * @param doc edited document
     * @param offset current caret position
     * @return int index of first space or offset passed in if none before it
     * @throws BadLocationException
     */
    static int getRowFirstNonWhite(StyledDocument doc, int offset)
            throws BadLocationException {
        Element lineElement = doc.getParagraphElement(offset);//line start/stop offsets

        int start = lineElement.getStartOffset();
        int failsafe = start;
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
        return start > offset ? failsafe : start;
    }

    /**
     * Returns index of last white char in line
     *
     * @param line array of chars
     * @return int index or -1 if not found
     */
    static int getIndexOfLastSpace(char[] line) {
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
     * Gets index of the end of attribute
     *
     * @param line array of chars from begin of attribute to end of line
     * @return index of end of attribute or -1 if something went wrong
     */
    static int getIndexOfAttributesEnd(char[] line) {
        for (int i = 0; i < line.length; i++) {
            final char c = line[i];
            if (Character.isWhitespace(c) || c == '>') {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if caret is inside tag and returns tags name
     *
     * @param doc edited text
     * @param carretOffset current caret location offset
     * @return String tag name or empty if not inside tag
     * @throws BadLocationException
     */
    static String getCurrentTagName(StyledDocument doc, int carretOffset) throws BadLocationException {
        int lastWhiteSpace = carretOffset;
        while (carretOffset > 0) {
            String chars = doc.getText(carretOffset - 1, 1);
            if (chars.equals(">")) {
                break;
            } else if (chars.equals(" ")) {
                lastWhiteSpace = carretOffset;
            } else if (chars.equals("<")) {
                //DEBUG INFO - i know where tag is and can read it
                //System.out.println(carretOffset + " <----> " + lastWhiteSpace + " <----> " + (lastWhiteSpace - carretOffset));
                //System.out.println(doc.getText(carretOffset, lastWhiteSpace - carretOffset));
                return doc.getText(carretOffset, lastWhiteSpace - carretOffset);
            }
            carretOffset--;
        }
        return "";
    }

    static boolean insideAttributesValue(StyledDocument doc, int carretOffset)  {
        
        boolean insideQuotes = false;
        while (carretOffset > 0) {
            try {
                String chars = doc.getText(carretOffset - 1, 1);
                if (chars.equals("<") || chars.equals("\"") && insideQuotes) {
                    return false;
                } else if (chars.equals("\"") &&(doc.getText(carretOffset - 2, 1).equals("="))) {
                    return true;
                }else if(chars.equals("\"")) {
                    insideQuotes = true;
                }
                carretOffset--;
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return false;
    }

}
