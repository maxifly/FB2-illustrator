package com.maxifly.fb2_illustrator.model;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.ImageUtils;
import com.maxifly.fb2_illustrator.MyException;
import com.sun.imageio.plugins.common.ImageUtil;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Maximus on 19.04.2016.
 */
public class Illustration implements Comparable<Illustration> {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(Illustration.class.getName());

    private Path file;
    private Path scaleFile;

    private String def_description;
    private String illustrated_description = null;
    private Integer id;

    private Set<SearchTemplate_POJO> searchTemplates;

    private Project project; // Ссылка на проект, в который включена иллюстрация


    public Illustration(Integer id, String def_description) {
        this.id = id;
        this.def_description = def_description;
        this.searchTemplates = new HashSet<>();
    }


    public Illustration(Integer id, Path file, String def_description) {
        this(id, def_description);
        this.file = file;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public Set<SearchTemplate_POJO> getSearchTemplates() {
        return searchTemplates;
    }

    /**
     * Проверяет, подходит ли иллюстрация под текст параграфа
     *
     * @param paragrafText текст параграфа
     * @return - подходит или нет
     */
    public boolean isSuitable(ValueToTest paragrafText) {
        for (SearchTemplate_POJO searchTemplate : searchTemplates) {
            try {
                if (searchTemplate.checkString(paragrafText)) {
                    if (this.illustrated_description == null) this.illustrated_description = searchTemplate.getDescription();
                    return true;}
            } catch (Check_Exception e) {
                log.error("Exception when check {}: {}", searchTemplate, e);
            }
        }
        return false;
    }


    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public Path getScaleFile() {

        return (scaleFile!=null)?scaleFile:file;
    }

    public void setScaleFile(Path scaleFile) {
        this.scaleFile = scaleFile;
    }

    public void resizeFile(int weight, int hight, Path illDir) throws MyException {
        this.file.getFileName();
        Path scaleFilePath = illDir.resolve(this.file.getFileName());

        Image originalImage = null;
        try {
            originalImage = ImageUtils.loadImage(this.file.toFile());

            RenderedImage newImage = ImageUtils.createResizedCopy(originalImage, weight, hight, true);
            if (newImage != null) {
                ImageUtils.writeImage(newImage, scaleFilePath.toFile());
                scaleFile = scaleFilePath;
            } else {
                scaleFile = this.file;
            };
        } catch (MyException e) {
            log.error("Error when resize image:",e);
            throw e;
        }
    }



    public String getDescription() {
        if (illustrated_description == null) {
            return def_description;
        } else {
            return illustrated_description;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addSearchTempale(SearchTemplate_POJO searchTemplate) {
        this.searchTemplates.add(searchTemplate);
    }

    public void setDef_description(String def_description) {
        this.def_description = def_description;
    }

    public String getDef_description() {
        return def_description;
    }

    @Override
    public String toString() {
        if (this.illustrated_description == null) {
            return "Illustration{" +
                    " id: " + this.id +
                    ", def_description='" + def_description + '\'' +
                    '}';
        } else {
            return "Illustration{" +
                    " id: " + this.id +
                    "def_description='" + def_description
                    + "' illustrated_description='" +
                    illustrated_description +
                    "'}";
        }


    }

    @Override
    public int compareTo(Illustration o) {
        return this.id.compareTo(o.getId());
    }
}
