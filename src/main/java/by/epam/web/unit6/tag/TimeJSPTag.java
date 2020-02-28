package by.epam.web.unit6.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeJSPTag extends TagSupport {
    public static Locale locale=Locale.ENGLISH;
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();//returns the instance of JspWriter
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY", locale);
            out.print(dateFormat.format(Calendar.getInstance().getTime()));//printing date and time using JspWriter
        } catch (Exception e) {
            System.out.println(e);
        }
        return SKIP_BODY;//will not evaluate the body content of the tag
    }




}
