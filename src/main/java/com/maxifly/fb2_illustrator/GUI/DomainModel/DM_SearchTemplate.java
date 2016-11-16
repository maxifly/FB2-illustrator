package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.GUI.CheckResult;
import com.maxifly.fb2_illustrator.image_xml.model.SearchTemplateType;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Maximus on 26.08.2016.
 */
public class DM_SearchTemplate extends DM_Abstract {

    private SearchTemplate_POJO searchTemplate;
    private StringProperty template = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    private ObjectProperty<TemplateType> templateTypeObjectProperty = new SimpleObjectProperty<>();

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
        if (template.getValue() == null || template.getValue().equals("")) {
            return new CheckResult(false, "Шаблон пустой");
        } else {

            if (TemplateType.regexp == templateTypeObjectProperty.get()) {
                try {
                    Pattern.compile(template.getValue());
                } catch (PatternSyntaxException pse) {
                    return new CheckResult(false, "Ошибка регулярного выражения: " + pse.getMessage());
                }


            }
        }

        return new CheckResult(true);

    }

    public void save() {
        searchTemplate.setDescription(description.getValue());
        searchTemplate.setTemplate(template.getValue());
        searchTemplate.setTemplateType(templateTypeObjectProperty.get());
    }

    public void cancel() {
        description.setValue(searchTemplate.getDescription());
        template.setValue(searchTemplate.getTemplate());
        templateTypeObjectProperty.setValue(searchTemplate.getTemplateType());
    }

    public void refresh() {
        cancel();
    }

}
