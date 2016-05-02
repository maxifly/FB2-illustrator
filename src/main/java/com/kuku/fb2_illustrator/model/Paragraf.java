package com.kuku.fb2_illustrator.model;

import com.kuku.fb2_illustrator.fb2_xml.model.PType;

import javax.xml.bind.JAXBElement;

/**
 * Created by Maximus on 24.04.2016.
 */
public class Paragraf {
    private JAXBElement<PType> pType;
    private int indexInParagrafs;

    public Paragraf(JAXBElement<PType> bookElement) {
        this.pType = bookElement;
    }

    public Paragraf() {
    }

    public void setIndexInParagrafs(int indexInParagrafs) {
        this.indexInParagrafs = indexInParagrafs;
    }

    public int getIndexInParagrafs() {
        return indexInParagrafs;
    }

    /**
     * Возвращает признак того, что параграф содержит переданный элемент книги
     * @param pType
     * @return
     */
    public boolean isContain(JAXBElement<PType> pType) {
        return (this.pType == pType);
    }

    public JAXBElement<PType> getBookElement() {
        return pType;
    }
}
