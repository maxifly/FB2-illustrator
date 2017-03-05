package com.maxifly.fb2_illustrator;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maximus on 17.10.2016.
 */
public class Utils {

    public static String normalize(String str) {
        return str.replaceAll("[-,!:;\\(\\)'\"\\.\\?\\s\\n\\r\\t]+", " ").trim().toLowerCase();

    }


    /**
     * Показывает страницу
     * @throws MyException
     */
    public static void showUrlInBrowser(String url) throws MyException {
        try {
            if (isBrowsingSupported()) {
                Desktop desktop = java.awt.Desktop.getDesktop();
                desktop.browse(new URI(url));
           }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new MyException("Can not open documentation", e);
        }
    }


    private static boolean isBrowsingSupported() {
        if (!Desktop.isDesktopSupported()) {
            return false;
        }
        boolean result = false;
        Desktop desktop = java.awt.Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            result = true;
        }
        return result;

    }

}
