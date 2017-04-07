package org.netbeans.modules.thymeleafcodecompletion;

import java.util.HashMap;

/**
 * Thymeleaf attributes and documentation
 *
 * @author Artur Wiśniewski
 *
 */
public class ThymeleafData {

    /**
     * Array of Thymeleaf attributes.
     */
    private static String[] thymeleafAttributes = {
       
        "xmlns:th=\"http://www.thymeleaf.org\"",
        "xmlns=\"http://www.w3.org/1999/xhtml\"",
        "xmlns:sec=\"http://www.thymeleaf.org/thymeleaf-extras-springsecurity3\"",
        "xmlns:sec=\"http://www.thymeleaf.org/thymeleaf-extras-springsecurity4\"",
        
        "layout:decorator",
        "layout:fragment",
        "layout:include",
        "layout:replace",

        "th:abbr",
        "th:accept",
        "th:accept-charset",
        "th:accesskey",
        "th:action",
        "th:align",
        "th:alt",
        "th:alt-title",
        "th:archive",
        "th:assert",
        "th:async",
        "th:attr",
        "th:attrappend",
        "th:attrprepend",
        "th:audio",
        "th:autocomplete",
        "th:autofocus",
        "th:autoplay",
        "th:axis",
        
        "th:background",
        "th:bgcolor",
        "th:border",
        "th:block", //none in eclipse plugin
        
        "th:case",
        "th:cellpadding",
        "th:cellspacing",
        "th:challenge",
        "th:charset",
        "th:checked",
        "th:cite",
        "th:class",
        "th:classappend",
        "th:classid",
        "th:codebase",
        "th:codetype",
        "th:cols",
        "th:colspan",
        "th:compact",
        "th:content",
        "th:contenteditable",
        "th:contextmenu",
        "th:controls",
        
        "th:data",
        "th:datetime",
        "th:declare",
        "th:default",
        "th:defer",
        "th:dir",
        "th:disabled",
        "th:draggable",
        "th:dropzone",
        
        "th:each",
        "th:enctype",
        
        "th:for",
        "th:form",
        "th:formaction",
        "th:formenctype",
        "th:formmethod",
        "th:formnovalidate",
        "th:formtarget",
        "th:fragment",
        "th:frame",
        "th:frameborder",
        
        "th:headers",
        "th:height",
        "th:hidden",
        "th:high",
        "th:href",
        "th:hreflang",
        "th:hspace",
        "th:http-equiv",
        
        "th:icon",
        "th:id",
        "th:if",
        "th:include",
        "th:inline",
        "th:ismap",
        "th:insert", //thymeleaf ver 3
        
        "th:keytype",
        "th:kind",
        
        "th:label",
        "th:lang",
        "th:lang-xmllang",
        "th:list",
        "th:longdesc",
        "th:loop",
        "th:low",
        
        "th:manifest",
        "th:marginheight",
        "th:marginwidth",
        "th:max",
        "th:maxlength",
        "th:media",
        "th:method",
        "th:min",
        "th:multiple",
        
        "th:name",
        "th:novalidate",
        "th:nowarp",
        
        "th:object",
        
        "th:onabort",
        "th:onafterprint",
        "th:onbeforeprint",
        "th:onbeforeunload",
        "th:onblur",
        "th:oncanplay",
        "th:oncanplaythrough",
        "th:onchange",
        "th:onclick",
        "th:oncontextmenu",
        "th:ondblclick",
        "th:ondrag",
        "th:ondragend",
        "th:ondragenter",
        "th:ondragleave",
        "th:ondragover",
        "th:ondragstart",
        "th:ondrop",
        "th:ondurationchange",
        "th:onemptied",
        "th:onended",
        "th:onerror",
        "th:onfocus",
        "th:onformchange",
        "th:onforminput",
        "th:onhashchange",
        "th:oninput",
        "th:oninvalid",
        "th:onkeydown",
        "th:onkeypress",
        "th:onkeyup",
        "th:onload",
        "th:onloadeddata",
        "th:onloadedmetadata",
        "th:onloadstart",
        "th:onmessage",
        "th:onmousedown",
        "th:onmousemove",
        "th:onmouseout",
        "th:onmouseover",
        "th:onmouseup",
        "th:onmousewheel",
        "th:onoffline",
        "th:ononline",
        "th:onpause",
        "th:onplay",
        "th:onplaying",
        "th:onpopstate",
        "th:onprogress",
        "th:onratechange",
        "th:onreadystatechange",
        "th:onredo",
        "th:onreset",
        "th:onresize",
        "th:onscroll",
        "th:onseeked",
        "th:onseeking",
        "th:onselect",
        "th:onshow",
        "th:onstalled",
        "th:onstorage",
        "th:onsubmit",
        "th:onsuspend",
        "th:ontimeupdate",
        "th:onundo",
        "th:onunload",
        "th:onvolumechange",
        "th:onwaiting",
        
        "th:open",
        "th:optimum",
        
        "th:pattern",
        "th:placeholder",
        "th:poster",
        "th:preload",
        "th:pubdate",
        
        "th:radiogroup",
        "th:readonly",
        "th:rel",
        "th:remove",
        "th:replace",
        "th:required",
        "th:rev",
        "th:reversed",
        "th:rows",
        "th:rowspan",
        "th:rules",
        
        "th:sandbox",
        "th:scheme",
        "th:scope",
        "th:scoped",
        "th:scrolling",
        "th:seamless",
        "th:selected",
        "th:size",
        "th:sizes",
        "th:span",
        "th:spellcheck",
        "th:src",
        "th:srclang",
        "th:standby",
        "th:start",
        "th:step",
        "th:style",
        "th:styleappend",
        "th:substituteby",
        "th:summary",
        "th:switch",
        
        "th:tabindex",
        "th:target",
        "th:text",
        "th:title",
        "th:type",
        
        "th:unless",
        "th:usemap",
        "th:utext",
        
        "th:value",
        "th:valuetype",
        "th:vspace",
        
        "th:width",
        "th:with",
        "th:wrap",
        
        "th:xmlbase",
        "th:xmllang",
        "th:xmlspace"

    };

    /**
     * Mapping doc to attributes
     */
    private static HashMap<String, String> docs = new HashMap<String, String>();

    static {
        docs.put("th:alt", "Sets the alt attribute to the result of the expression. \n\nSee also:\n\tth:alt-title");
        docs.put("th:alt-title", "Sets both the alt and title attributes. \nAuthor:\n\tDaniel Fern&aacutendez\nSince:\n\t1.0");
        docs.put("th:assert", "A comma-separated list of expressions which should \n be evaluated and produce true for every evaluation, \n raising an exception if not. \nAuthor:\n\tDaniel Fern&aacutendez\nSince:\n\t2.1.0");
        docs.put("th:attr", "See also:\n\tth:attrappend, th:attrprepend\nReference:\n\tUsing Thymeleaf section 5 on Setting Attribute \n\t Values");
        docs.put("th:attrappend", "See also:\n\tth:attr, th:attrprepend, th:classappend\nReference:\n\tUsing Thymeleaf section 5.4 on Appending And \n\t Prepending");
        docs.put("th:attrprepend", "See also:\n\tth:attr, th:attrappend\nReference:\n\tUsing Thymeleaf section 5.4 on Appending And \n\t Prepending");
        docs.put("th:case", "See also:\n\tth:switch\nReference:\n\tUsing Thymeleaf section 7.2 on Switch \n\t Statements");
        docs.put("th:classappend", "See also:\n\tth:attrappend, th:class\nReference:\n\tUsing Thymeleaf section 5.4 on Appending And \n\t Prepending");
        docs.put("th:each", "Reference:\n\tUsing Thymeleaf section 6.2 on Keeping Iteration \n\t Status");
        docs.put("th:fragment", "See also:\n\tth:include, th:substituteby\nReference:\n\tUsing Thymeleaf section 8.1 on Including \n\t Template Fragments");
        docs.put("th:if", "See also:\n\tth:unless\nReference:\n\tUsing Thymeleaf section 7.1 on Simple \n\t Conditionals");
        docs.put("th:include", "See also:\n\tth:replace, th:substituteby, th:fragment\nReference:\n\tUsing Thymeleaf section 8.1 on Including \n\t Template Fragments");
        docs.put("th:inline", "Reference:\n\tUsing Thymeleaf section 11 on Inlining\n\nPossible value(s):\n\ttext, javascript, dart");
        docs.put("th:lang", "Sets the lang attribute to the result of the \n expression. \n\nSee also:\n\tth:lang-xmllang");
        docs.put("th:lang-xmllang", "Sets both the lang and xml:lang attributes. \nAuthor:\n\tDaniel Fern&aacutendez\nSince:\n\t1.0");
        docs.put("th:object", "See also:\n\tth:with\nReference:\n\tUsing Thymeleaf section 4.3 on the asterisk \n\t syntax");
        docs.put("th:remove", "Reference:\n\tUsing Thymeleaf section 8.2 on Removing \n\t Template Fragments\n\nPossible value(s):\n\tall, body, tag, all-but-first");
        docs.put("th:replace", "See also:\n\tth:include, th:fragment\nReference:\n\tUsing Thymeleaf section 8.1 on Including \n\t Template Fragments");
        docs.put("th:styleappend", "See also:\n\tth:attrappend, th:style\nReference:\n\tUsing Thymeleaf section 5.4 on Appending And \n\t Prepending");
        docs.put("th:substituteby", "See also:\n\tth:include, th:fragment\nReference:\n\tUsing Thymeleaf section 8.1 on Including \n\t Template Fragments");
        docs.put("th:switch", "See also:\n\tth:case, th:if\nReference:\n\tUsing Thymeleaf section section 7.2 on Switch \n\t Statements");
        docs.put("th:text", "See also:\n\tth:utext\nReference:\n\tUsing Thymeleaf section 3 on Using Texts");
        docs.put("th:title", "Sets the title attribute to the result of the \n expression. \n\nSee also:\n\tth:alt-title");
        docs.put("th:unless", "See also:\n\tth:if\nReference:\n\tUsing Thymeleaf section 7.1 on Simple \n\t Conditionals");
        docs.put("th:utext", "See also:\n\tth:text\nReference:\n\tUsing Thymeleaf section 3.2 on Unescaped Text");
        docs.put("th:with", "See also:\n\tth:object\nReference:\n\tUsing Thymeleaf section 9 on Local Variables");
        docs.put("th:xmlbase", "See also:\n\tth:xmllang, th:xmlspace");
        docs.put("th:xmllang", "See also:\n\tth:xmlbase, th:xmlspace");
        docs.put("th:xmlspace", "See also:\n\tth:xmlbase, th:xmllang");
        docs.put("layout:decorator", "Specifies the name of the decorator template to \n apply to a content template. \nThe mechanism for resolving decorator templates is \n the same as that used by Thymeleaf to resolve pages \n in the th:fragment and th:include processors. \nAuthor:\n\tEmanuel Rabina");
        docs.put("layout:fragment", "Marks sections of the template that can be replaced \n by sections in the content template (if decorating) or \n passed along to included pages (if including), which \n share the same name. \nAuthor:\n\tEmanuel Rabina\n\nCannot appear in tag(s):\n\ttitle");
        docs.put("layout:include", "Similar to Thymeleaf's th:include, but allows the \n passing of entire element fragments to the included \n template. Useful if you have some HTML that you \n want to reuse, but whose contents are too complex \n to determine or construct with context variables \n alone. \nAuthor:\n\tEmanuel Rabina");
        docs.put("layout:replace", "Similar to Thymeleaf's th:replace, but allows the \n passing of entire element fragments to the included \n template. Useful if you have some HTML that you \n want to reuse, but whose contents are too complex \n to determine or construct with context variables \n alone. \nAuthor:\n\tEmanuel Rabina");
    }

    ;

    /**
     *  Returns array containing Thymeleaf attributes.
     * @return String[] array of attributes.
     */
    public static String[] getThymeleafAttributes() {
        return thymeleafAttributes;
    }

    /**
     * Returns description for given Thymeleaf attribute.
     *
     * @param attribute for which we need documentation
     * @return String containing documentation description
     */
    public static String getDoc(String attribute) {
        return docs.get(attribute);
    }

}
