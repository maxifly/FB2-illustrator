package com.maxifly.fb2_illustrator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maximus on 17.10.2016.
 */
public class Utils {

    public static String normalize(String str) {
       return str.replaceAll("[-,!:;\\(\\)'\"\\.\\?\\s\\n\\r\\t]+"," ").trim().toLowerCase();

    }

}
