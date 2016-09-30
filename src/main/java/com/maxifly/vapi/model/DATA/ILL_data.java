package com.maxifly.vapi.model.DATA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maximus on 11.07.2016.
 */
public class ILL_data {
    public Integer fb_ill;
    public String  prj;
    public Integer num;
    public String dsc;
    public List<ILL_search> srch = new ArrayList();
}
