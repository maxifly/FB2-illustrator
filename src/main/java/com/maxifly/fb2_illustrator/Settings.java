package com.maxifly.fb2_illustrator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.LastAddrs;
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

    private Integer bookSize_V = 800;
    private Integer bookSize_H = 600;
    private String projectsDir;

    private LastAddrs lastUserAddrs = new LastAddrs(5);
    private List<String> preset_users = new ArrayList<>();
    private LastAddrs lastGroupAddrs = new LastAddrs(5);
    private List<String> preset_groups = new ArrayList<>();
    private LastAddrs lastAlbumAddrs = new LastAddrs(5);

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

    public Integer getBookSize_V() {
        return bookSize_V;
    }

    public void setBookSize_V(Integer bookSize_V) {
        this.bookSize_V = bookSize_V;
    }

    public Integer getBookSize_H() {
        return bookSize_H;
    }

    public void setBookSize_H(Integer bookSize_H) {
        this.bookSize_H = bookSize_H;
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


    public LastAddrs getLastUserAddrs() {
        return lastUserAddrs;
    }

    public LastAddrs getLastGroupAddrs() {
        return lastGroupAddrs;
    }

    public LastAddrs getLastAlbumAddrs() {
        return lastAlbumAddrs;
    }

    public List<String> getPreset_users() {
        return preset_users;
    }

    public List<String> getPreset_groups() {
        return preset_groups;
    }

    /**
     * Заполняет предустановленые списки
     */
    private void fill_preset() {
        preset_users.clear();
        preset_groups.clear();

//        preset_users.add("pr_1");
//        preset_users.add("pr_2");
    }
}
