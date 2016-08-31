package com.maxifly.fb2_illustrator.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Maxim.Pantuhin on 25.08.2016.
 */
public class Project {
    private String id = "123456789";
    private Set<SearchTemplate_POJO> bookNameTemplates = new HashSet<>();
    private List<Illustration> illustrations = new ArrayList(); //TODO тут надо как-то сохранять порядок вставки



    public void addBookNameTempale(SearchTemplate_POJO bookNameTemplate) {
        this.bookNameTemplates.add(bookNameTemplate);
    }

    public void addIll(Illustration illustration) {
        this.illustrations.add(illustration);
    }


}
