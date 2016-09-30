package com.maxifly.vapi.model.DATA;

import com.google.gson.*;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;

import java.lang.reflect.Type;

/**
 * Created by Maximus on 24.09.2016.
 */
public class Ill_VKJ_Serialiser implements JsonSerializer<Illustration> { //}, JsonDeserializer<Illustration> {



//    @Override
//    public Project deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//        JsonObject jsonObject = jsonElement.getAsJsonObject();
//
//        Integer fb_ill = jsonObject.get("fb_ill").getAsInt();
//        Integer prj_info = jsonObject.get("prj_info").getAsInt();
//
//        if (fb_ill != 1 && prj_info != 1) {
//            return null;
//        }
//
//        String id = jsonObject.get("prj").getAsString();
//
//        Project project = new Project();
//        project.setId(id);
//
//        JsonElement je = jsonObject.get("book_name_templates");
//        if(je != null && !je.isJsonNull()) {
//            JsonArray templates = je.getAsJsonArray();
//
//            for(JsonElement jel:templates){
//                SearchTemplate_POJO stp = jsonDeserializationContext.deserialize(jel, SearchTemplate_POJO.class);
//                project.addBookNameTempale(stp);
//            }
//        }
//
//        return project;
//
//    }

    @Override
    public JsonElement serialize(Illustration illustration, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject result = new JsonObject();
        result.add("fb_ill", new JsonPrimitive(1));


        result.add("prj", new JsonPrimitive(illustration.getProject().getId()));
        result.add("num", new JsonPrimitive(illustration.getId()));

        if (illustration.getDescription() != null) {
            result.add("dsc", new JsonPrimitive(illustration.getDescription()));
        }

        if(illustration.getSearchTemplates() != null) {
            JsonElement searchTemplates = jsonSerializationContext.serialize(illustration.getSearchTemplates());
            result.add("srch", searchTemplates);
        }

        return result;
    }


}
