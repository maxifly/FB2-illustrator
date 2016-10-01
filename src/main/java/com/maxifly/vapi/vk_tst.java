package com.maxifly.vapi;


import com.maxifly.fb2_illustrator.BookProcessor;
import com.maxifly.fb2_illustrator.BookProcessor_FB20;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Illustrations;
import com.maxifly.vapi.model.DATA.DATA_photo;
import com.maxifly.vapi.model.Illustration_VK;
import com.maxifly.vapi.model.PhotoSize;
import com.maxifly.vapi.model.ScopeElement;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Created by Maximus on 17.06.2016.
 */
public class vk_tst {

    public static void main(String[] params) throws Exception {
        ScopeElement[] scopes = {ScopeElement.photos, ScopeElement.groups, ScopeElement.e_mail};
        Connect connect = new Connect("5509552",scopes,"maxpant@mail.ru", "kukuku");

        String accessToken = connect.getAccessToken(60000);

        PhotoProcessor photoProcessor = new PhotoProcessor(accessToken,233176977, PhotoSize.photo_2560x2048);
        IllFilter illFilter = new IllFilter("123456"); //TODO Это по сути просто тест
        PhotoLoader photoLoader = new PhotoLoader(Files.createTempDirectory("fbill_"));



        while(photoProcessor.hasNext()) {
            DATA_photo data_photo = photoProcessor.next();
            System.out.println(data_photo.text +"\n"+data_photo.url);
            illFilter.add(data_photo);
        }

        System.out.println("Illustrations count " + illFilter.getIllustrations().size());

        photoLoader.setIllustrationList(illFilter.getIllustrations());
        photoLoader.download();

        int i = 1;

        List<Illustration_VK> illustrationList = illFilter.getIllustrations();

        Collections.sort(illustrationList,new InternetIllComparator());

        Illustrations illustrations = new Illustrations();

        for (Illustration illustration : illustrationList) {
            illustrations.addIllustration(illustration);
        }



        Path inputFile = (FileSystems.getDefault().getPath("priv_fales", "test2.fb2"));
        Path outputFile = (FileSystems.getDefault().getPath("priv_fales", "test_out2.fb2"));
        BookProcessor bookParse = new BookProcessor_FB20();
        bookParse.processBook(illustrations,inputFile,outputFile);



    }
}
