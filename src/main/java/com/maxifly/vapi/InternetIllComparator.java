package com.maxifly.vapi;

import com.maxifly.vapi.model.Illustration_VK;

import java.util.Comparator;

/**
 * Created by Maximus on 16.07.2016.
 */
public class InternetIllComparator implements Comparator<Illustration_VK> {
    @Override
    public int compare(Illustration_VK o1, Illustration_VK o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
