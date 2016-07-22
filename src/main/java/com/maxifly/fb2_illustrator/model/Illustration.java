package com.maxifly.fb2_illustrator.model;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Maximus on 19.04.2016.
 */
public class Illustration {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(Illustration.class.getName());

    private Path file;
    private String def_description;
    private String illustrated_description = null;
    private String  id;

    private Set<SearchTemplate_POJO> searchTemplates;


    public Illustration(String id, String def_description) {
        this.id = id;
        this.def_description = def_description;
        this.searchTemplates = new HashSet<>();
    }


    public Illustration(String id, Path file, String def_description) {
        this(id,def_description);
        this.file = file;
    }

    /**
     * Проверяет, подходит ли иллюстрация под текст параграфа
     * @param paragrafText текст параграфа
     * @return - подходит или нет
     */
    public boolean isSuitable(String paragrafText) {


       // TODO переписать проверку, подходит ли иллюстрация под текст параграфа
        for (SearchTemplate_POJO searchTemplate : searchTemplates) {
            switch (searchTemplate.templateType) {
                case substr:
                    if (paragrafText.contains(searchTemplate.template)) {
                        this.illustrated_description = searchTemplate.description;
                        return true;
                    }
                    break;
                case regexp:
                    try {
                        //TODO Перенести компиляцию условия на момент разбора иллюстраций
                        Pattern pattern = Pattern.compile(searchTemplate.template);
                        Matcher matcher = pattern.matcher(paragrafText);
                        if (matcher.matches()) {
                            this.illustrated_description = searchTemplate.description;
                            return true;
                        }
                    }
                    catch (Exception e) {
                        log.error("Exception when check regexp: {}",e);
                        return false;
                    }
                    break;


                default:
                    log.warn("Search type {} unsupported",searchTemplate.templateType);

            }
        }
        return  false;
    }


    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public String getDescription() {
        if (illustrated_description == null) {
        return def_description; } else {
            return illustrated_description;
        }
    }

    public String getId() {
        return id;
    }

    public void addSearchTempale(SearchTemplate_POJO searchTemplate) {
        this.searchTemplates.add(searchTemplate);
    }

    @Override
    public String toString() {
        if (this.illustrated_description == null) {
            return "Illustration{" +
                    "def_description='" + def_description + '\'' +
                    '}';
        } else {
            return "Illustration{" +
                    "def_description='" + def_description
                    + "' illustrated_description='" +
                    illustrated_description +
                    "'}";
        }


    }
}
