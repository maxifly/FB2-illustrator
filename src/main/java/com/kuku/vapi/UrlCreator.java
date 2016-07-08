package com.kuku.vapi;

import com.kuku.vapi.model.AuthHeader;
import com.kuku.vapi.model.ScopeElement;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Maximus on 17.06.2016.
 */
public class UrlCreator {
    private static final String METHOD_URL = "https://api.vk.com/method/";
    private static final String redirect_uri = "http://oauth.vk.com/blank.html";
    private static final String version = "5.52";
    private static final String scope = "8";
    //    private static final String display = "popup";
    private static final String display = "page";
    private static final String response_type = "token";
    private static final String METHOD_URI = "https://api.vk.com/method/";

    /**
     * Складывает права
     * @param scopeElements - список требуемых прав
     * @return битовая маска прав
     */
    static String computeScope(ScopeElement[] scopeElements) {
        int scope = 0;

        for (int i=0;i<scopeElements.length;i++) {

            scope += scopeElements[i].getCode();

        }

        if (scope == 0) scope = ScopeElement.photos.getCode();
        return  String.valueOf(scope);
    }


    public static String getAuthUrl(String clientId, ScopeElement[] scopes) {
        // https://oauth.vk.com/authorize?client_id=idApp&scope=audio&redirect_url=https://oauth.vk.com/blank.html&display=page&v=5.4&response_type=token
        return  "http://oauth.vk.com/authorize?" +
                "client_id="+clientId+
                "&scope=" + computeScope(scopes) + // scope + //StringUtils.join(scopes, ",") +
                "&redirect_uri="+redirect_uri+
                "&v=" + version +
                "&display="+display+
                "&response_type="+response_type;

    }


    public static String getLoginUrl(final AuthHeader ah, final String login, final String password) {
        return "https://login.vk.com/?act=login" +
                "&soft=1" +
                "&ip_h=" + ah.getIp_h() +
                "&lg_h=" + ah.getLg_h() +
                "&_origin=" + ah.getOrigin() +

                // "&q=1"+

                // "&from_host=oauth.vk.com"+
                "&to=" + ah.getTo() +
                "&expire=0" +
                "&email=" + login +
                "&pass=" + password;
    }

    public static String getPhotos(String accessToken, int albumId, int offset, int pageSize )  {
       return "https://api.vk.com/method/photos.get?" +
               "album_id=" + String.valueOf(albumId) +
               "&access_token="+ accessToken +
               "&v=" + version +
               "&offset=" + String.valueOf(offset) +
               "&count=" + String.valueOf(pageSize);


    }
//    ip_h=470cfbb365f2340293&lg_h=6e19f1dfd460fde753
//
//    &_origin=https%3A%2F%2Foauth.vk.com
//    &to=aHR0cHM6Ly9vYXV0aC52ay5jb20vYXV0aG9yaXplP2NsaWVudF9pZD01NTA5NTUyJnJlZGlyZWN0X3VyaT1odHRwcyUzQSUyRiUyRm9hdXRoLnZrLmNvbSUyRmJsYW5rLmh0bWwmcmVzcG9uc2VfdHlwZT10b2tlbiZzY29wZT00JnY9NS41MiZzdGF0ZT0mZGlzcGxheT1wYWdl
//    &expire=0&email=maxpant%40mail.ru&pass=kukuku

//    https://login.vk.com/?act=login&soft=1&ip_h=470cfbb365f2340293&lg_h=c9a27eb4a43affaf75&_origin=http://oauth.vk.com&to=aHR0cDovL29hdXRoLnZrLmNvbS9hdXRob3JpemU/Y2xpZW50X2lkPTU1MDk1NTImcmVkaXJlY3RfdXJpPWh0dHAlM0ElMkYlMkZvYXV0aC52ay5jb20lMkZibGFuay5odG1sJnJlc3BvbnNlX3R5cGU9dG9rZW4mc2NvcGU9NCZ2PTUuNTImc3RhdGU9JmRpc3BsYXk9cGFnZQ--&expire=0&email=maxpant@mail.ru&pass=kukuku
}
