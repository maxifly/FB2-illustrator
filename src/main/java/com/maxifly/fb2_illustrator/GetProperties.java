package com.maxifly.fb2_illustrator;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class GetProperties {
    String login;
    String pass;
    String url;

    private static final String sFileName = "config.properties";
    private static String sDirSeparator = System.getProperty("file.separator");
    public static final String sDefaultPath = sDirSeparator + "src" + sDirSeparator + "main" + sDirSeparator + "resources";

    public void ReadConfig() {
       /*
        String user;
        String pass;
        String dbname;
         */
        // Чтение конфигурационных параметров
        Properties prop = new Properties();

        // определяем текущий каталог
        File currentDir = new File(".");

        try {
            // определяем полный путь к файлу
            String sFilePath = currentDir.getCanonicalPath() + sDirSeparator + sDefaultPath + sDirSeparator + sFileName;

            System.out.println("sFilePath = " + sFilePath);

            prop.load(new FileInputStream(sFilePath));

            login = prop.getProperty("login");
            pass = prop.getProperty("pass");
            url = prop.getProperty("url");
/*
            System.out.println("user = " + user);
            System.out.println("pass = " + pass);
            System.out.println("dbname = " + dbname);
*/
            //file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getUrl() {
        return url;
    }


}
