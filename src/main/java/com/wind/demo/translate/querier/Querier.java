package com.wind.demo.translate.querier;


import com.wind.demo.translate.http.AbstractHttpAttribute;
import com.wind.demo.translate.lang.LANG;

import java.util.ArrayList;
import java.util.List;

public final class Querier<T extends AbstractHttpAttribute> {
    private LANG from;
    private LANG to;
    private String text;
    private List<T> collection;

    public Querier() {
        collection = new ArrayList<T>();
    }

    public String execute() {
        String result = null;

        for (T element : collection) {
                result = element.run(from, to, text);
        }
        return result;
    }

    public void setParams(LANG from, LANG to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public void setParams(LANG source, String text) {
        this.from = source;
        this.text = text;
    }

    public void attach(T element){
        collection.add(element);
    }

    public void detach(T element) {
        collection.remove(element);
    }
}
