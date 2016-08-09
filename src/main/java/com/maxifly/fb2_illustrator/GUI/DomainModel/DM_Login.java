package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.vapi.Connect;
import com.maxifly.vapi.UrlCreator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

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
    private String token1 = null;

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
                if (newValue.contains("access_token")) {
                    log.debug("TOKEN!!!!");


                    //TODO Вытащить логин и токен
                    token1 = "kuku";
                    factory_gui.getDm_statusBar().setLogin("kuku","token_kuku");
                }

            }
        });
    }

    /**
     * Осущетвляет коннект к ВК
     * @return токен
     */
    public String connect() throws InterruptedException {

        log.info("Get access token");
        // получаем URL аутентификации
        String sUrl = UrlCreator.getAuthUrl(Constants.getApplId(), Constants.getScopes());
        log.debug("sUrl: {}", sUrl);

        webEngine.load(sUrl);
//int i = 1;
//        while(i <=100000) {
//            log.debug("url: {}",this.currentLocationProperty.getValue());
//i++;
//            Thread.sleep(1000);
//        }


//TODO Вернуть токен
        return null;
    }

    public String getToken1() {
        return token1;
    }
}
