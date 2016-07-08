package com.kuku.vapi;


import com.kuku.vapi.model.DATA.DATA_photo;
import com.kuku.vapi.model.PhotoSize;
import com.kuku.vapi.model.ScopeElement;

/**
 * Created by Maximus on 17.06.2016.
 */
public class vk_tst {

    public static void main(String[] params) throws Exception {
        ScopeElement[] scopes = {ScopeElement.photos, ScopeElement.groups, ScopeElement.e_mail};
        Connect connect = new Connect("5509552",scopes,"maxpant@mail.ru", "");

        String accessToken = connect.getAccessToken(60000);

        PhotoProcessor photoProcessor = new PhotoProcessor(accessToken,233176977, PhotoSize.photo_2560x2048);

        while(photoProcessor.hasNext()) {
            DATA_photo data_photo = photoProcessor.next();
            System.out.println(data_photo.text +"\n"+data_photo.url);
        }

    }
}
