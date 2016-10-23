package com.maxifly.fb2_illustrator;


import com.maxifly.vapi.model.ScopeElement;

import java.io.File;
import java.util.Locale;

public class Constants {

    private static final String applId = "5509552"; // Идентификатор приложения в ВК
    public static final String drag_string = "fb2_ill:"; // Признак объекта участвующего в drug and drop при смене порядка иллюстраций


    static final Locale LOCALE_APP = Locale.ENGLISH;

    public static Locale getLocaleApp() {
        return LOCALE_APP;
    }

    /**
     * Возвращает идентификатор приложения в ВК
     *
     * @return
     */
    public static String getApplId() {
        return applId;
    }

    /**
     * Возвращает права, необходимые для работы приложения в ВК
     *
     * @return
     */
    public static ScopeElement[] getScopes() {
        ScopeElement[] scopes = {ScopeElement.photos, ScopeElement.groups, ScopeElement.e_mail};
        return scopes;
    }


    /**
     * Убеждается, что есть каталог для хранения установок
     *
     * @return Каталог для хранения установок
     * @throws MyException
     */
    public static File ensureAppDataDir() throws MyException {
        String workDirName = null;

        String appData = System.getenv("APPDATA");
        if (appData == null) {
            workDirName = System.getProperty("user.home") + File.separator + "fb2_illustrator";

        } else {
            workDirName = appData + File.separator + "fb2_illustrator";
        }

        File dir = new File(workDirName);

        if (dir.isDirectory()) {
            return dir;
        } else {
            if (dir.exists()) {
                throw new MyException("Can not create directory " + workDirName);
            } else {
                if (dir.mkdir()) {
                    return dir;
                } else {
                    throw new MyException("Can not create directory " + workDirName);
                }
            }
        }
    }

    /**
     * Убеждается, что есть папка для хранения пректов
     * @return
     * @throws MyException
     */
    public static File ensureAppProjectDir() throws MyException {
        String workDirName;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            workDirName = System.getProperty("user.home")
                    + File.separator + "Documents"
                    + File.separator + "fb2_illustrator"
                    + File.separator + "projects";
        } else {
            workDirName = System.getProperty("user.home")
                    + File.separator + "fb2_illustrator"
                    + File.separator + "projects";
        }
        File dir = new File(workDirName);
        if (dir.isDirectory()) {
            return dir;
        } else {
            if (dir.exists()) {
                throw new MyException("Can not create directory " + workDirName);
            } else {
                if (dir.mkdir()) {
                    return dir;
                } else {
                    throw new MyException("Can not create directory " + workDirName);
                }
            }
        }


    }

}
