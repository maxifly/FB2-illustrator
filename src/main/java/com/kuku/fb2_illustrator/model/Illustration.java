package com.kuku.fb2_illustrator.model;

import com.kuku.fb2_illustrator.fb2_xml.model.PType;

import java.nio.file.Path;


/**
 * Created by Maximus on 19.04.2016.
 */
public class Illustration {
    private Path file;
    private String  descriopion;
    private String  id;


    public Illustration(String id, Path file, String descriopion) {
        this.file = file;
        this.id = id;
        this.descriopion = descriopion;
    }

    /**
     * Проверяет, подходит ли иллюстрация под текст параграфа
     * @param paragrafText текст параграфа
     * @return - подходит или нет
     */
    public boolean isSuitable(String paragrafText) {
       return paragrafText.contains(this.descriopion);
    }


    public Path getFile() {
        return file;
    }

    public String getDescriopion() {
        return descriopion;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Illustration{" +
                "descriopion='" + descriopion + '\'' +
                '}';
    }
}
