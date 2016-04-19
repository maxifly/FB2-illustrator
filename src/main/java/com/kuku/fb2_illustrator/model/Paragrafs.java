package com.kuku.fb2_illustrator.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

/**
 * Created by Maximus on 18.04.2016.
 */
public class Paragrafs {
    private Map<Object, Integer> paragrafByNum = new HashMap<>();
    private Map<Integer,Object> numByParagraf = new HashMap<>();
    private Integer index = 0;

    public void addParagraf(Object paragraf) {
      this.index ++;
       paragrafByNum.put(paragraf,index);
        numByParagraf.put(index,paragraf);
    }

    public Integer getParagrafNumber(Object paragraf) {
       return paragrafByNum.get(paragraf);
    }
}
