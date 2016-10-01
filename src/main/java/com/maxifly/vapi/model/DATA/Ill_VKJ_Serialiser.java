package com.maxifly.vapi.model.DATA;

import com.google.gson.*;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.vapi.model.Illustration_VK;

import java.lang.reflect.Type;

/**
 * Created by Maximus on 24.09.2016.
 */
public class Ill_VKJ_Serialiser implements JsonSerializer<Illustration>, JsonDeserializer<Illustration_VK> {
    String filter_project_id = null;

    public Ill_VKJ_Serialiser() {
    }

    public Ill_VKJ_Serialiser(String filter_project_id) {
        this.filter_project_id = filter_project_id;
    }

    @Override
    public Illustration_VK deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {

            JsonObject jsonObject = jsonElement.getAsJsonObject();

            Integer fb_ill = jsonObject.get("fb_ill").getAsInt();
            Integer prj_info = jsonObject.get("ill_info").getAsInt();

            if (fb_ill != 1 && prj_info != 1) {
                return null;
            }

            String project_id = jsonObject.get("prj").getAsString();

            if (!(this.filter_project_id != null && this.filter_project_id.equals(project_id))) {
                // Иллюстрация не подходит из-за фильтра
                return null;
            }

            Integer ill_num = jsonObject.get("num").getAsInt();

            String description = null;
            JsonElement je = jsonObject.get("dsc");
            if (je != null && !je.isJsonNull()) {
                description = je.getAsString();
            }

            Illustration_VK illustration_vk = new Illustration_VK(ill_num, description, project_id);

            je = jsonObject.get("srch");
            if (je != null && !je.isJsonNull()) {
                JsonArray templates = je.getAsJsonArray();

                for (JsonElement jel : templates) {
                    SearchTemplate_POJO stp = jsonDeserializationContext.deserialize(jel, SearchTemplate_POJO.class);
                    illustration_vk.addSearchTempale(stp);
                }
            }

            return illustration_vk;

        } catch (JsonParseException e) {
            throw e;
        } catch (Exception e) {
            return null; // TODO Залогировать ошибку
        }
    }

    @Override
    public JsonElement serialize(Illustration illustration, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject result = new JsonObject();
        result.add("fb_ill", new JsonPrimitive(1));
        result.add("ill_info", new JsonPrimitive(1));

        result.add("prj", new JsonPrimitive(illustration.getProject().getId()));
        result.add("num", new JsonPrimitive(illustration.getId()));

        if (illustration.getDescription() != null) {
            result.add("dsc", new JsonPrimitive(illustration.getDescription()));
        }

        if (illustration.getSearchTemplates() != null) {
            JsonElement searchTemplates = jsonSerializationContext.serialize(illustration.getSearchTemplates());
            result.add("srch", searchTemplates);
        }

        return result;
    }


}
