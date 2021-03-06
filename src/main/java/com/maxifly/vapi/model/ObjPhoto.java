package com.maxifly.vapi.model;

/**
 * Created by Maximus on 19.06.2016.
 */
public class ObjPhoto {
    public int d; //	идентификатор фотографии.  положительное число
    //   public int album_id; 	идентификатор альбома, в котором находится фотография.   int (числовое значение)
//    public int owner_id 	идентификатор владельца фотографии. int (числовое значение)
//    user_id 	идентификатор пользователя, загрузившего фото (если фотография размещена в сообществе). Для фотографий, размещенных от имени сообщества, user_id=100.
//    положительное число
    public String text; //	текст описания фотографии.

    public String photo_75; //	url копии фотографии с максимальным размером 75x75px.
    public String photo_130; //	 	url копии фотографии с максимальным размером 130x130px.
    public String photo_604; //	 	url копии фотографии с максимальным размером 604x604px.
    public String photo_807; //		url копии фотографии с максимальным размером 807x807px.
    public String photo_1280; //		url копии фотографии с максимальным размером 1280x1024px.
    public String photo_2560; //		url копии фотографии с максимальным размером 2560x2048px.


}
