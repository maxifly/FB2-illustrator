package com.kuku.fb2_illustrator.model;

import com.kuku.fb2_illustrator.fb2_xml.model.PType;

import javax.xml.bind.JAXBElement;

/**
 * Created by Maximus on 24.04.2016.
 */
public class Paragraf {
    private JAXBElement<PType> pType;

    public Paragraf(JAXBElement<PType> pType) {
        this.pType = pType;
    }

    public Paragraf() {
    }
}
