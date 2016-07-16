package com.kuku.vapi;

import com.kuku.vapi.model.InternetIllustration;

import java.util.Comparator;

/**
 * Created by Maximus on 16.07.2016.
 */
public class InternetIllComparator implements Comparator<InternetIllustration> {
    @Override
    public int compare(InternetIllustration o1, InternetIllustration o2) {
        if (o1.getIllNum() < o2.getIllNum()) {
            return -1;
        }

        if (o1.getIllNum() == o2.getIllNum()) {
            if (o1.getIllSubNum() < o2.getIllSubNum()) {
                return -1;
            } else if (o1.getIllSubNum() == o2.getIllSubNum()) {
                return 0;
            }
        }

        return 1;

    }
}
