package by.epam.web.unit6.tag;

import by.epam.web.unit6.bean.Tarif;

import java.util.Iterator;
import java.util.List;

public class JSPListBean {

    private Iterator<Tarif> iterator;
    private List<Tarif> list;

    public JSPListBean(){}

    public JSPListBean(List list) {
        this.list = list;
    }

    public String getSize() {
        iterator = list.iterator();
        return Integer.toString(list.size());
    }

    public Tarif getElement() {
        return iterator.next();
    }

    public String getSpeed(){
        return Integer.toString(getElement().getSpeed());
    }

    public String getPrice(){
        return Double.toString(getElement().getPrice());
    }

    public String getDiscount(){
        return Double.toString(getElement().getDiscount());
    }




}
