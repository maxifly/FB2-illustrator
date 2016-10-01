package com.maxifly.vapi.model.DATA;

import com.google.gson.*;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.vapi.model.Project_VK;

import java.lang.reflect.Type;

/**
 * Created by Maximus on 24.09.2016.
 */
public class Project_VKJ_Serialiser implements JsonSerializer<Project>, JsonDeserializer<Project_VK> {
    String filter_project_id = null;

    public Project_VKJ_Serialiser() {
    }

    public Project_VKJ_Serialiser(String filter_project_id) {
        this.filter_project_id = filter_project_id;
    }

    @Override
    public Project_VK deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            Integer fb_ill = jsonObject.get("fb_ill").getAsInt();
            Integer prj_info = jsonObject.get("prj_info").getAsInt();

            if (fb_ill != 1 && prj_info != 1) {
                return null;
            }

            String id = jsonObject.get("prj").getAsString();

            if (!(filter_project_id != null && filter_project_id.equals(id))) {
                return null;
            }

            Project_VK project = new Project_VK();
            project.setId(id);

            JsonElement je = jsonObject.get("srch");
            if (je != null && !je.isJsonNull()) {
                JsonArray templates = je.getAsJsonArray();

                for (JsonElement jel : templates) {
                    SearchTemplate_POJO stp = jsonDeserializationContext.deserialize(jel, SearchTemplate_POJO.class);
                    project.addBookNameTempale(stp);
                }
            }

            return project;
        } catch (JsonParseException e) {
            throw e;
        } catch (Exception e) {
            return null; // TODO Залогировать ошибку
        }
    }

    @Override
    public JsonElement serialize(Project project, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject result = new JsonObject();
        result.add("fb_ill", new JsonPrimitive(1));
        result.add("prj_info", new JsonPrimitive(1));

        result.add("prj", new JsonPrimitive(project.getId()));

        if (project.getProjectParagraf() != null) {
            result.add("prj_desc", new JsonPrimitive(project.getProjectParagraf()));
        }

        if (project.getBookNameTemplates() != null) {
            JsonElement searchTemplates = jsonSerializationContext.serialize(project.getBookNameTemplates());
            result.add("srch", searchTemplates);
        }

        return result;
    }
}
