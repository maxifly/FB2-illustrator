package com.maxifly.fb2_illustrator;

import com.google.gson.*;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Maximus on 15.09.2016.
 */
public class Project_J_Serializer implements JsonSerializer<Project>, JsonDeserializer<Project> {
    @Override
    public JsonElement serialize(Project project, Type type, JsonSerializationContext jsonSerializationContext) {
//        private String id = "123456789";
//        private Set<SearchTemplate_POJO> bookNameTemplates = new HashSet<>();
//        private List<Illustration> illustrations = new ArrayList(); //TODO тут надо как-то сохранять порядок вставки
//
//    public void addBookNameTempale(SearchTemplate_POJO bookNameTemplate) {
//        this.bookNameTemplates.add(bookNameTemplate);
//    }

        JsonObject result = new JsonObject();

        result.add("id", new JsonPrimitive(project.getId()));


        if(project.getBookNameTemplates() != null) {
            JsonElement searchTemplates = jsonSerializationContext.serialize(project.getBookNameTemplates());
            result.add("book_name_templates", searchTemplates);
        }

        if(project.getIllustrations() != null) {
            JsonElement searchTemplates = jsonSerializationContext.serialize(project.getIllustrations());
            result.add("illustrations", searchTemplates);
        }

        return result;
    }

    @Override
    public Project deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();


        String id =  jsonObject.get("id").getAsString();
        Project project = new Project();

        project.setId(id);

        JsonElement je = jsonObject.get("book_name_templates");
        if(je != null && !je.isJsonNull()) {
            JsonArray templates = je.getAsJsonArray();

            for(JsonElement jel:templates){
                SearchTemplate_POJO stp = jsonDeserializationContext.deserialize(jel, SearchTemplate_POJO.class);
                project.addBookNameTempale(stp);
            }
        }

        List<Illustration> illList = new ArrayList<>();

        je = jsonObject.get("illustrations");
        if(je != null && !je.isJsonNull()) {
            JsonArray templates = je.getAsJsonArray();

            for(JsonElement jel:templates){
                Illustration ill = jsonDeserializationContext.deserialize(jel, Illustration.class);
                illList.add(ill);
            }
        }

        Collections.sort(illList);

        for (Illustration ill:illList) {
            project.addIll(ill);
        }


        return project;
    }


}
