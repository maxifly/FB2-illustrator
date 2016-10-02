package com.maxifly.vapi.model.DATA;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Maximus on 24.09.2016.
 */
public class PrjObj_VKJ_Serialiser implements JsonDeserializer<SimplePrjOpj> {
    String filter_project_id = null;

    public PrjObj_VKJ_Serialiser() {
    }

    public PrjObj_VKJ_Serialiser(String filter_project_id) {
        this.filter_project_id = filter_project_id;
    }

    @Override
    public SimplePrjOpj deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {

            JsonObject jsonObject = jsonElement.getAsJsonObject();

            Integer fb_ill = jsonObject.get("fb_ill").getAsInt();

            String project_id = jsonObject.get("prj").getAsString();


            if (!(this.filter_project_id != null && this.filter_project_id.equals(project_id))) {
                // Иллюстрация не подходит из-за фильтра
                return null;
            } else {
                return new SimplePrjOpj();
            }

        } catch (JsonParseException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
            return null; // TODO Залогировать ошибку
        }
    }




}
