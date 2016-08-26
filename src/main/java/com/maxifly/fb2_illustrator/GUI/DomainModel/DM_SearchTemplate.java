package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.GUI.CheckResult;
import com.maxifly.fb2_illustrator.image_xml.model.SearchTemplateType;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Maximus on 26.08.2016.
 */
public class DM_SearchTemplate {

    private SearchTemplate_POJO searchTemplate;
    StringProperty template = new SimpleStringProperty();
    StringProperty description = new SimpleStringProperty();

    ObjectProperty<TemplateType> templateTypeObjectProperty = new SimpleObjectProperty<>();

    public StringProperty template_Propery() {
        return this.template;
    }
    public StringProperty description_Propery() {
        return this.description;
    }
    public ObjectProperty<TemplateType> templateType_Propery() {
        return this.templateTypeObjectProperty;
    }


    public void setSearchTemplate(SearchTemplate_POJO searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    public SearchTemplate_POJO getSearchTemplate() {
        return searchTemplate;
    }

    public CheckResult check() {
        return new CheckResult(true); //TODO Сделать проверку регулярного выражения
    }

    public void save() {
        searchTemplate.description = description.getValue();
        searchTemplate.template = template.getValue();
        searchTemplate.templateType = templateTypeObjectProperty.get();
    }

    public void cancel() {
        description.setValue(searchTemplate.description);
        template.setValue(searchTemplate.template);
        templateTypeObjectProperty.setValue(searchTemplate.templateType);
    }
}
