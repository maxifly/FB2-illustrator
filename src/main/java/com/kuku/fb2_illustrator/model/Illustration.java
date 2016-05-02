package com.kuku.fb2_illustrator.model;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Created by Maximus on 19.04.2016.
 */
public class Illustration {
    private Path file;
    private String def_description;
    private String  id;

    private Set<SearchTemplate_POJO> searchTemplates;


    public Illustration(String id, Path file, String def_description) {
        this.file = file;
        this.id = id;
        this.def_description = def_description;
        this.searchTemplates = new HashSet<>();
    }

    /**
     * Проверяет, подходит ли иллюстрация под текст параграфа
     * @param paragrafText текст параграфа
     * @return - подходит или нет
     */
    public boolean isSuitable(String paragrafText) {
       // TODO переписать проверку, подходит ли иллюстрация под текст параграфа
       return paragrafText.contains(this.def_description);
    }


    public Path getFile() {
        return file;
    }

    public String getDescription() {
        return def_description;
    }

    public String getId() {
        return id;
    }

    public void addSearchTempale(SearchTemplate_POJO searchTemplate) {
        this.searchTemplates.add(searchTemplate);
    }

    @Override
    public String toString() {
        return "Illustration{" +
                "def_description='" + def_description + '\'' +
                '}';
    }
}
