package by.epam.web.unit6.tag;

import by.epam.web.unit6.bean.Tarif;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListJSPTag extends BodyTagSupport {
    private int num;
    private JSPListBean list;

    public JSPListBean getList() {
        return list;
    }

    public void setList(JSPListBean list) {
        this.list = list;
    }

    public void setNum(String num) {
        this.num =new Integer(num);
    }


    public int doStartTag() throws JspTagException {
        int i = 1;
        try {
            JspWriter out = pageContext.getOut();
           /* out.write(" <div class=\"table-responsive\">");
            out.write("<table class=\"table table-striped\">");
            out.write("<tr class=\"active\">");
            out.write("<th>№</th>");
            out.write("<th> ${name} </th>");
            out.write("<th> ${descr} </th>");
            out.write("<th> ${speed} </th>");
            out.write("<th> ${price}, $</th>");
            out.write("<th> ${discount} </th>");
            out.write("<th></th>");
            out.write("</tr>");
*/


            while (num >= 1) {
                Tarif tarif = list.getElement();
                num = num - 1;
                out.write("<tr><td>");
                out.write(Integer.toString(i));
                out.write("</td><td>");
                out.write(tarif.getName());
                out.write("</td><td>");
                out.write(tarif.getDescription());
                out.write("</td><td>");
                out.write(Integer.toString(tarif.getSpeed()));
                out.write("</td><td>");
                out.write(Double.toString(tarif.getPrice()));
                out.write("</td><td>");
                out.write(Double.toString(tarif.getDiscount()));
                out.write("</td></tr>");
                i++;
            }

        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }


    public int doEndTag() throws JspException {
        try {
           // pageContext.getOut().write("</table></div>");
            pageContext.getOut().write("");
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }

}




