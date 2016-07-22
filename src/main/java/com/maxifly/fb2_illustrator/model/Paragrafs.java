package com.maxifly.fb2_illustrator.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maximus on 18.04.2016.
 */
public class Paragrafs {
    private Map<Paragraf, Integer> paragrafByNum = new HashMap<>();
    private Map<Integer,Paragraf> numByParagraf = new HashMap<>();
    private Map<Object,Paragraf> paragrafByBookElement = new HashMap<>();

    private Integer index = 0;

    public void addParagraf(Paragraf paragraf) {
       paragrafByNum.put(paragraf,index);
       numByParagraf.put(index,paragraf);
       paragrafByBookElement.put(paragraf.getBookElement(),paragraf);
       paragraf.setIndexInParagrafs(index);
       this.index ++;
    }

    public Integer getParagrafNumber(Paragraf paragraf) {
       return paragrafByNum.get(paragraf);
    }

    public Integer getSize() {
        return index;
    }

    public Paragraf getParagraf(Integer index) {
        return numByParagraf.get(index);
    }

    public Paragraf getParagrafByBookElement(Object bookElement) {
        return paragrafByBookElement.get(bookElement);
    }
}
