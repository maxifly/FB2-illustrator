package com.maxifly.fb2_illustrator;

import com.google.gson.*;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;

import java.lang.reflect.Type;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Created by Maximus on 10.09.2016.
 */
public class Ill_J_Serializer implements JsonSerializer<Illustration>, JsonDeserializer<Illustration> {
    @Override
    public JsonElement serialize(Illustration illustration, Type type, JsonSerializationContext jsonSerializationContext) {

//        private Path file;
//        private String def_description;
//        private Integer  id;
//
//        private Set<SearchTemplate_POJO> searchTemplates;

        JsonObject result = new JsonObject();

        if (illustration.getDescription() != null) {
            JsonPrimitive desc = new JsonPrimitive(illustration.getDescription());
            result.add("description", desc);
        }

        if (illustration.getId() != null) {
            JsonPrimitive id = new JsonPrimitive(illustration.getId());
            result.add("id", id);
        }

        if (illustration.getFile() != null) {
            JsonPrimitive file = new JsonPrimitive(illustration.getFile().toFile().getAbsolutePath());
            result.add("file", file);
        }

        if (illustration.getSearchTemplates() != null) {
            JsonElement searchTemplates = jsonSerializationContext.serialize(illustration.getSearchTemplates());
            result.add("templates", searchTemplates);
        }

        return result;
    }

    @Override
    public Illustration deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String desc = null;
        Integer id = jsonObject.get("id").getAsInt();

        JsonElement je = jsonObject.get("description");
        if (je != null && !je.isJsonNull()) {
            desc = je.getAsString();
        }
        Illustration ill = new Illustration(id, desc);

        je = jsonObject.get("file");
        if (je != null && !je.isJsonNull()) {
            ill.setFile(FileSystems.getDefault().getPath(je.getAsString()));
        }

        je = jsonObject.get("templates");
        if (je != null && !je.isJsonNull()) {
            JsonArray templates = je.getAsJsonArray();

            for (JsonElement jel : templates) {
                SearchTemplate_POJO stp = jsonDeserializationContext.deserialize(jel, SearchTemplate_POJO.class);
                ill.addSearchTempale(stp);
            }
        }

        return ill;
    }
}
