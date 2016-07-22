package com.maxifly.vapi.model.REST;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maximus on 20.06.2016.
 */
public class REST_Items<T> {
    public int count;
    public List<T> items = new ArrayList<>();
}
