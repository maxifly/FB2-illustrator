package com.maxifly.fb2_illustrator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Maximus on 25.10.2016.
 */
public class Settings {
    private Integer winSize_W = 800;
    private Integer winSize_H = 600;
    private String  projectsDir;

    /**
     * Создает новый объект с установками
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

    public Integer getWinSize_W() {
        return winSize_W;
    }

    public void setWinSize_W(Integer winSize_W) {
        this.winSize_W = winSize_W;
    }

    public Integer getWinSize_H() {
        return winSize_H;
    }

    public void setWinSize_H(Integer winSize_H) {
        this.winSize_H = winSize_H;
    }

    public String getProjectsDir() {
        return projectsDir;
    }

    public void setProjectsDir(String projectsDir) {
        this.projectsDir = projectsDir;
    }
}
