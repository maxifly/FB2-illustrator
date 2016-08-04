package com.maxifly.fb2_illustrator;


import com.maxifly.vapi.model.ScopeElement;

import java.util.Locale;

public class Constants {

    private static final String applId = "5509552"; // Идентификатор приложения в ВК


    static final Locale LOCALE_APP = Locale.ENGLISH;

    public static Locale getLocaleApp() {
        return LOCALE_APP;
    }

    /**
     * Возвращает идентификатор приложения в ВК
     * @return
     */
    public static String getApplId() {
        return applId;
    }

    /**
     * Возвращает права, необходимые для работы приложения в ВК
     * @return
     */
    public static ScopeElement[] getScopes() {
        ScopeElement[] scopes = {ScopeElement.photos, ScopeElement.groups, ScopeElement.e_mail};
        return scopes;
    }
}
