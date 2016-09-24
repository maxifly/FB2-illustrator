package com.maxifly.vapi;

import com.maxifly.vapi.model.AuthHeader;
import com.maxifly.vapi.model.ScopeElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maximus on 17.06.2016.
 */
public class UrlCreator {
    private static final String METHOD_URL = "https://api.vk.com/method/";
    private static final String redirect_uri = "http://oauth.vk.com/blank.html";
    private static final String version = "5.53";
    private static final String scope = "8";
    //    private static final String display = "popup";
    private static final String display = "page";
    private static final String response_type = "token";
    private static final String METHOD_URI = "https://api.vk.com/method/";

    /**
     * Складывает права
     *
     * @param scopeElements - список требуемых прав
     * @return битовая маска прав
     */
    static String computeScope(ScopeElement[] scopeElements) {
        int scope = 0;

        for (int i = 0; i < scopeElements.length; i++) {

            scope += scopeElements[i].getCode();

        }

        if (scope == 0) scope = ScopeElement.photos.getCode();
        return String.valueOf(scope);
    }


    public static String getToken(String URL) {
        Pattern ptrn_token = Pattern.compile("#access_token=.+?(&|$)");
        Matcher matcher = ptrn_token.matcher(URL + "&");
        if (matcher.find()) {
            String grp = matcher.group();
            return grp.substring(14, grp.length() - 1);
        }
        return null;
    }

    public static String getEmail(String URL) {
        Pattern ptrn_token = Pattern.compile("&email=.+?(&|$)");
        Matcher matcher = ptrn_token.matcher(URL + "&");
        if (matcher.find()) {
            String grp = matcher.group();
            return grp.substring(7, grp.length() - 1);
        }
        return null;
    }

    public static String getFileType(String URL) {
        Pattern ptrn_token = Pattern.compile("\\.([^\\.]+?)$");
        Matcher matcher = ptrn_token.matcher(URL);
        if (matcher.find()) {
            String grp = matcher.group(1);
            return grp;
        }
        return null;
    }

    public static String getAuthUrl(String clientId, ScopeElement[] scopes) {
        // https://oauth.vk.com/authorize?client_id=idApp&scope=audio&redirect_url=https://oauth.vk.com/blank.html&display=page&v=5.4&response_type=token
        return "http://oauth.vk.com/authorize?" +
                "client_id=" + clientId +
                "&scope=" + computeScope(scopes) + // scope + //StringUtils.join(scopes, ",") +
                "&redirect_uri=" + redirect_uri +
                "&v=" + version +
                "&display=" + display +
                "&response_type=" + response_type;

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

    public static String getPhotos(String accessToken, long albumId, int offset, int pageSize) {
        return "https://api.vk.com/method/photos.get?" +
                "album_id=" + String.valueOf(albumId) +
                "&access_token=" + accessToken +
                "&v=" + version +
                "&offset=" + String.valueOf(offset) +
                "&count=" + String.valueOf(pageSize);


    }

    public static String getUploadServer(String accessToken, long albumId) {
        return "https://api.vk.com/method/photos.getUploadServer?" +
                "album_id=" + String.valueOf(albumId) +
                "&access_token=" + accessToken +
                "&v=" + version;


    }

    public static String photosSave(String accessToken, long albumId,
                                    Long group_id,
                                    Long server,
                                    String photos_list,
                                    String hash,
                                    String caption
    ) {
        StringBuilder sb = new StringBuilder("https://api.vk.com/method/photos.save?" +
                "album_id=" + String.valueOf(albumId) +
                "&access_token=" + accessToken +
                "&v=" + version);

        if (group_id != null) {
            sb.append("&group_id=").append(group_id);
        }

        sb.append("&server=").append(server).
                append("&photos_list=").append(photos_list).
                append("&hash=").append(hash).
                append("&caption=").append(caption);


        return sb.toString();


    }

    public static long getAlbumId(String albumPath) throws Exception {
        Pattern dg_pattern = Pattern.compile("^\\d+$");
        if (dg_pattern.matcher(albumPath).matches()) {
            return Long.valueOf(albumPath);
        }

        Pattern url_pattern = Pattern.compile("\\d+$");
        Matcher matcher = url_pattern.matcher(albumPath);
        if (matcher.find()) {
            return
                    Long.valueOf(
                            matcher.group(0));
        }

        throw new Exception("Can not parse album addr " + albumPath);
        // http://vk.com/albums320470599

    }

//    ip_h=470cfbb365f2340293&lg_h=6e19f1dfd460fde753
//
//    &_origin=https%3A%2F%2Foauth.vk.com
//    &to=aHR0cHM6Ly9vYXV0aC52ay5jb20vYXV0aG9yaXplP2NsaWVudF9pZD01NTA5NTUyJnJlZGlyZWN0X3VyaT1odHRwcyUzQSUyRiUyRm9hdXRoLnZrLmNvbSUyRmJsYW5rLmh0bWwmcmVzcG9uc2VfdHlwZT10b2tlbiZzY29wZT00JnY9NS41MiZzdGF0ZT0mZGlzcGxheT1wYWdl
//    &expire=0&email=maxpant%40mail.ru&pass=kukuku

//    https://login.vk.com/?act=login&soft=1&ip_h=470cfbb365f2340293&lg_h=c9a27eb4a43affaf75&_origin=http://oauth.vk.com&to=aHR0cDovL29hdXRoLnZrLmNvbS9hdXRob3JpemU/Y2xpZW50X2lkPTU1MDk1NTImcmVkaXJlY3RfdXJpPWh0dHAlM0ElMkYlMkZvYXV0aC52ay5jb20lMkZibGFuay5odG1sJnJlc3BvbnNlX3R5cGU9dG9rZW4mc2NvcGU9NCZ2PTUuNTImc3RhdGU9JmRpc3BsYXk9cGFnZQ--&expire=0&email=maxpant@mail.ru&pass=kukuku
}
