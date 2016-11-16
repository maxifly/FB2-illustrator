package com.maxifly.fb2_illustrator.model;

import com.maxifly.fb2_illustrator.Utils;

/**
 * Created by Maximus on 22.10.2016.
 */
public class ComparedText
        implements ValueToTest {
    private String original;
    private String normilize;

    public ComparedText(String original) {
        this.original = original;
        normilize = Utils.normalize(original);
    }

    @Override
    public String getNormalize() {
        return normilize;
    }

    @Override
    public String getOriginal() {
        return original;
    }
}
