package com.kuku.vapi;



/**
 * Created by Maximus on 17.06.2016.
 */
public class vk_tst {

    public static void main(String[] params) throws Exception {
        String[] scopes = {"kuku"};
        Connect connect = new Connect("5509552",scopes,"maxpant@mail.ru", "kukuku");

        connect.getAccessToken();

    }
}
