package com.maxifly.fb2_illustrator.model;

/**
 * Created by Maximus on 02.05.2016.
 */
public class SearchTemplate_POJO {
    public TemplateType templateType;
    public String template;
    public String description = null;

    public SearchTemplate_POJO(TemplateType templateType, String template, String description) {
        this.templateType = templateType;
        this.template = template;
        this.description = description;
    }
}
