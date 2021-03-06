package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.vapi.Connect;
import com.maxifly.vapi.UrlCreator;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maximus on 04.08.2016.
 */
public class DM_Login {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(DM_Login.class.getName());


    private Factory_GUI factory_gui;
    private WebEngine webEngine;
    private ReadOnlyStringProperty currentLocationProperty;
    private BooleanProperty isLogin = new SimpleBooleanProperty(false);

    public DM_Login(Factory_GUI factory_gui) {
        this.factory_gui = factory_gui;
    }

    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.currentLocationProperty = this.webEngine.locationProperty();
        currentLocationProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                log.debug("new URL: {}", newValue);
                if (newValue.contains("#access_token")) {
                    log.debug("TOKEN!!!!");

                    String token = UrlCreator.getToken(newValue);
                    String eMail = UrlCreator.getEmail(newValue);
                    String userId = UrlCreator.getUserId(newValue);

                    log.debug("TOKEN: {} Email: {}", token, eMail);
                    factory_gui.getDm_statusBar().setLogin(eMail, token);
                    factory_gui.getDm_statusBar().setUserId(Long.valueOf(userId));

                    setIsLogin(true);
                }

            }
        });
    }

    /**
     * Осущетвляет коннект к ВК
     *
     * @return токен
     */
    public String connect() throws InterruptedException {

        log.info("Get access token");
        // получаем URL аутентификации
        String sUrl = UrlCreator.getAuthUrl(Constants.getApplId(), Constants.getScopes());
        log.debug("sUrl: {}", sUrl);

        webEngine.load(sUrl);


        return null;
    }


    public boolean isLogin() {
        return isLogin.get();
    }

    public BooleanProperty isLoginProperty() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin.set(isLogin);
    }

    public void setIsLoginProperty(BooleanProperty isLoginProperty) {
        this.isLogin = isLoginProperty;
    }
}
