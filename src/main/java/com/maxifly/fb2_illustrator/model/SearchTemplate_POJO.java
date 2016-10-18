package com.maxifly.fb2_illustrator.model;

import com.maxifly.fb2_illustrator.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maximus on 02.05.2016.
 */
public class SearchTemplate_POJO {
    private TemplateType templateType;
    private String template;
    private String description = null;


    private String normaliseTemplate;
    private Pattern pattern;

    public SearchTemplate_POJO(TemplateType templateType, String template, String description) {
        this.templateType = templateType;
        this.template = template;
        this.description = description;
    }


    public TemplateType getTemplateType() {
        return templateType;
    }

    public String getTemplate() {
        return template;
    }

    public String getDescription() {
        return description;
    }


    public void setTemplateType(TemplateType templateType) {
        normaliseTemplate = null;
        pattern = null;
        this.templateType = templateType;
    }

    public void setTemplate(String template) {
        normaliseTemplate = null;
        pattern = null;
        this.template = template;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Проверка образца по шаблону
      * @param str образец
     * @return true - подходит
     */
    public boolean checkString(String str) {
        switch (templateType) {
            case regexp:
                if (checkRegular(str)) return true;
                else return false;
            case substr:
                return checkSimple(str);
        }
        return false;
    }

    /**
     * Проверка образца по шаблону, при этом считается,
     * что образец может быть подстрокой шаблона, если шаблон не регулярное выражение
     * @param str - образец
     * @return true - совпадение
     */
    public boolean checkAsSubstring(String str) throws Exception {
        switch (templateType) {
            case regexp:
                return checkRegular(str);
            case substr:
                return checkSimpleAsSubstr(str);
        }
        return false;
    }
    private void genNormaliseTemplate() {
        if (normaliseTemplate == null) {
            normaliseTemplate = Utils.clearPunctuation(template);
        }
    }

    private void compilePattern() {
        if (pattern == null) {
            pattern = Pattern.compile(this.template);
        }
    }

    private boolean checkSimpleAsSubstr(String str) {
        genNormaliseTemplate();
        return !"".equals(normaliseTemplate) && (normaliseTemplate.contains(str));
    }

    private boolean checkSimple(String str) {
        genNormaliseTemplate();
        return !"".equals(normaliseTemplate) && (str.contains(normaliseTemplate));
    }

    private boolean checkRegular(String str) throws  { //TODO Добавить исключение и потом обработать его при проверке вхождения иллюстрации
            compilePattern();
            return pattern.matcher(str).matches();
    }




}
