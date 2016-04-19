package com.kuku.fb2_illustrator.model;

import com.kuku.fb2_illustrator.fb2_xml.model.PType;


/**
 * Created by Maximus on 19.04.2016.
 */
public class Illustration {
    private String file;
    private String  descriopion;

    public Illustration(String file, String descriopion) {
        this.file = file;
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


}
