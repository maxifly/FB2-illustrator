package com.kuku.fb2_illustrator.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

/**
 * Created by Maximus on 18.04.2016.
 */
public class Paragrafs {
    private Map<Paragraf, Integer> paragrafByNum = new HashMap<>();
    private Map<Integer,Paragraf> numByParagraf = new HashMap<>();
    private Integer index = 0;

    public void addParagraf(Paragraf paragraf) {
       paragrafByNum.put(paragraf,index);
       numByParagraf.put(index,paragraf);
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
}
