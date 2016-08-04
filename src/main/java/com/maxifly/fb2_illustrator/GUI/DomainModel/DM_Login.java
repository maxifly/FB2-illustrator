package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.vapi.Connect;
import com.maxifly.vapi.UrlCreator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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



    private WebEngine webEngine;

    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    /**
     * Осущетвляет коннект к ВК
     * @return токен
     */
    public String connect() {

        log.info("Get access token");
        // получаем URL аутентификации
        String sUrl = UrlCreator.getAuthUrl(Constants.getApplId(), Constants.getScopes());
        log.debug("sUrl: {}", sUrl);

        webEngine.load(sUrl);
//TODO Вернуть токен
        return null;
    }
}
