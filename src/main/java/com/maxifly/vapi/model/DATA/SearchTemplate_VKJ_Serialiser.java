package com.maxifly.vapi.model.DATA;

import com.google.gson.*;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;

import java.lang.reflect.Type;

/**
 * Created by Maximus on 24.09.2016.
 */
public class SearchTemplate_VKJ_Serialiser implements JsonSerializer<SearchTemplate_POJO>, JsonDeserializer<SearchTemplate_POJO> {
    @Override
    public SearchTemplate_POJO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String template = null;
        TemplateType templateType = null;
        String desc = null;

        JsonElement je = jsonObject.get("re");
        if(je != null && !je.isJsonNull()) {
            templateType = TemplateType.regexp;
            template = je.getAsString();
        }

        je = jsonObject.get("s");
        if(je != null && !je.isJsonNull()) {
            templateType = TemplateType.substr;
            template = je.getAsString();
        }

        je = jsonObject.get("d");
        if(je != null && !je.isJsonNull()) {
            desc = je.getAsString();
        }

       return new SearchTemplate_POJO(templateType,template,desc);
    }

    @Override
    public JsonElement serialize(SearchTemplate_POJO searchTemplate_pojo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();

        switch (searchTemplate_pojo.templateType){
            case regexp:
                result.add("re", new JsonPrimitive(searchTemplate_pojo.template));
                break;
            case substr:
                result.add("s", new JsonPrimitive(searchTemplate_pojo.template));
        }

        if (searchTemplate_pojo.description != null) {
            result.add("d", new JsonPrimitive(searchTemplate_pojo.template));
        }

        return result;
    }
}
