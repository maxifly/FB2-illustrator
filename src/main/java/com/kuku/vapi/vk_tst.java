package com.kuku.vapi;


import com.kuku.vapi.model.ScopeElement;

/**
 * Created by Maximus on 17.06.2016.
 */
public class vk_tst {

    public static void main(String[] params) throws Exception {
        ScopeElement[] scopes = {ScopeElement.photos, ScopeElement.groups, ScopeElement.e_mail};
        Connect connect = new Connect("5509552",scopes,"maxpant@mail.ru", "");

        connect.getAccessToken(60000);

    }
}
