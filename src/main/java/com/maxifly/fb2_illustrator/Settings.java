package com.maxifly.fb2_illustrator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maximus on 25.10.2016.
 */
public class Settings {
    private Double winSize_W = 800D;
    private Double winSize_H = 600D;
    private String projectsDir;

    private LinkedList<String> last_users = new LinkedList<>();
    private List<String> preset_users = new ArrayList<>();


    /**
     * Создает новый объект с установками
     *
     * @return
     * @throws MyException
     * @throws IOException
     */
    public static Settings createNewSettings() throws MyException, IOException {
        Settings settings = new Settings();
        File projectDir = Constants.ensureAppProjectDir();
        settings.setProjectsDir(projectDir.getCanonicalPath());
        return settings;
    }

    public static Settings readSettings(File appDataDir) throws IOException {
        File settingFile = new File(appDataDir, "fb2_ill.cfg");

        if (settingFile.exists()) {
            String string_settings = FileOperations.readAll(settingFile);

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Settings stt =  gson.fromJson(string_settings, Settings.class);
            stt.fill_preset();
            return stt;

        } else {
            return null;
        }

    }

    public void writeSettings(File appDataDir) throws IOException {
        File settingFile = new File(appDataDir, "fb2_ill.cfg");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String settings_string = gson.toJson(this);
        FileOperations.writeAll(settingFile, settings_string);
    }

    public Double getWinSize_W() {
        return winSize_W;
    }

    public void setWinSize_W(Double winSize_W) {
        this.winSize_W = winSize_W;
    }

    public Double getWinSize_H() {
        return winSize_H;
    }

    public void setWinSize_H(Double winSize_H) {
        this.winSize_H = winSize_H;
    }

    public String getProjectsDir() {
        return projectsDir;
    }

    public void setProjectsDir(String projectsDir) {
        this.projectsDir = projectsDir;
    }







    /**
     * Заполняет предустановленые списки
     */
    private void fill_preset() {
        preset_users.clear();
        preset_users.add("pr_1");
        preset_users.add("pr_2");
    }
}
