package com.maxifly.vapi;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ConnectException;

import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.vapi.model.ScopeElement;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.HttpParams;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;


import com.maxifly.vapi.model.AuthHeader;

public class Connect {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(Connect.class.getName());

    private DefaultHttpClient httpClient;
    private final String clientId;
    private final ScopeElement[] scopes;
    private final String login;
    private final String password;

    private static final CookieSpecFactory acceptAllCookiesSpec = new CookieSpecFactory() {
        public CookieSpec newInstance(HttpParams params) {
            return new BrowserCompatSpec() {
                @Override
                public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                    // accept all cookies
                }
            };
        }
    };

    public Connect(final String clientId, final ScopeElement[] scopes, final String login, final String password) {
        this.clientId = clientId;
        this.scopes = scopes;
        this.login = login;
        this.password = password;
        setHttpClient();
    }

    public String getAccessToken(int timeoutMillisec) throws IOException, InterruptedException, GetAccessTokenException {
        long cutOfTime = System.currentTimeMillis() + timeoutMillisec;
        int tryCount = 1;
        int sleep = 0;
        String accessToken = null;
        do {
            try {
                accessToken = getAccessToken();
                break;
            } catch (ConnectException e) {
                log.warn("Get access token exception: " + e);
                sleep = tryCount++ * 1000;
                log.debug("Sleep ? millisec",sleep);
                Thread.sleep(sleep);
            }
        } while (cutOfTime > System.currentTimeMillis());

        if (accessToken == null) {
            throw new GetAccessTokenException("timeout " + timeoutMillisec + " millisec.");

        }

        return accessToken;
    }

    public String getAccessToken() throws ClientProtocolException, IOException, InterruptedException {
        log.info("Get access token");
        // получаем URL аутентификации
        String sUrl = UrlCreator.getAuthUrl(clientId, scopes);
        log.debug("sUrl: {}", sUrl);
        // получаем параметры из заголовка аутентификации
        Thread.sleep(500);
        AuthHeader ah = getAuthHeader(sUrl);
        log.debug("AuthHeader: {}", ah);
        // формируем URL на коннект
        Thread.sleep(500);
        String loginUrl = UrlCreator.getLoginUrl(ah, login, password);
        log.debug("LoginUrl: {}", loginUrl);
        // получаем URL на права приложения
        Thread.sleep(500);
        String appRightsUrl = getAppRightsUrl(loginUrl);
        log.debug("AppRightsUrl: {}", appRightsUrl);
        // получаем URL на получение AccessToken
        Thread.sleep(500);
        String accessTokenUrlUrl = getAccessTokenUrlUrl(appRightsUrl);
        log.debug("AccessTokenUrlUrl: {}", accessTokenUrlUrl);
        // получаем AccessToken
        Thread.sleep(500);
        String access_token = getAccessToken(accessTokenUrlUrl);
        log.debug("AccessToken: {}", access_token);
        return access_token;
    }


    public AuthHeader getAuthHeader(String path) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(path);
        HttpResponse response = httpClient.execute(post);
        post.abort();
        String HeaderLocation = converHttpEntityToString(response.getEntity());
        //log.debug("HeaderLocation: {}", HeaderLocation);

        String ip_h = findKey(HeaderLocation, "name=\"ip_h\" value=\"", "\"");
        String to = findKey(HeaderLocation, "name=\"to\" value=\"", "\"");
        String lg_h = findKey(HeaderLocation, "name=\"lg_h\" value=\"", "\"");
        String origin = findKey(HeaderLocation, "name=\"_origin\" value=\"", "\"");

        AuthHeader header = new AuthHeader(ip_h, to, lg_h, origin);
        return header;
    }

    public String getAccessToken(final String path) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(path);
        HttpResponse response = httpClient.execute(post);
        post.abort();
        // Теперь в след редиректе необходимый токен
        String headerLocation = response.getFirstHeader("location").getValue();
        // Просто спарсим его сплитами
        String accesstoken = headerLocation.split("#")[1].split("&")[0].split("=")[1];

        return accesstoken;
    }

    public String getAppRightsUrl(final String path) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(path);
        HttpResponse response = httpClient.execute(post);
        post.abort();
        // Получили редирект на подтверждение требований приложения
        return response.getFirstHeader("location").getValue();
    }

    public String getAccessTokenUrlUrl(final String path) throws ClientProtocolException, IOException {
        HttpResponse response = execute(path);
        Header h = response.getFirstHeader("location");

        if (h != null) {
            // права на приложение уже потдверждены получим Access Token
            return h.getValue();
        } else {
            // получим URL на подтверждение прав приложения
            log.debug("Try get application rights url");
            String httpEntity = converHttpEntityToString(response.getEntity());
            String url = findKey(httpEntity, "form method=\"post\" action=\"", "\"");
            //String url = findKey(httpEntity, "location.href = \"", "\"");
            //String url = findKey(httpEntity, "allow()", "\"");
            log.debug("Appl rights url " + url);

            // подтвердим права приложения
            response = execute(url);

            // попробуем еще раз получить Access Token
            response = execute(path);
            h = response.getFirstHeader("location"); //TODO Может быть null
            return h.getValue();
        }
    }

    private HttpResponse execute(final String path) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(path);
        HttpResponse response = httpClient.execute(post);
        post.abort();
        return response;
    }

    public DefaultHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient() {
        this.httpClient = new DefaultHttpClient();
        this.httpClient.getCookieSpecs().register("easy", acceptAllCookiesSpec);
        this.httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
    }


    private String converHttpEntityToString(HttpEntity ent) {
        BufferedInputStream bis;
        StringBuilder sb = new StringBuilder();
        try {
            bis = new BufferedInputStream(ent.getContent());
            byte[] buffer = new byte[1024];
            int count;
            while ((count = bis.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, count, "utf-8"));
            }
            bis.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String findKey(String source, String patternbegin, String patternend) {
        int startkey = source.indexOf(patternbegin);
        if (startkey > -1) {
            int stopkey = source.indexOf(patternend,
                    startkey + patternbegin.length());
            if (stopkey > -1) {
                String key = source.substring(startkey + patternbegin.length(),
                        stopkey);
                return key;
            }
        }
        return null;
    }

}
