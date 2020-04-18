package edu.mum.ea.socialnetwork.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static  <T> T parseJson(String json, Class<T> targetType) {

        ObjectMapper objectMapper=new ObjectMapper();
        try {
            return objectMapper.readValue(json, targetType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static  String parseLocation(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = null;
        try {
            actualObj = mapper.readTree(json);
            JsonNode countryNameNode = actualObj.get("country_name");
            JsonNode cityNode = actualObj.get("city");

            if(cityNode != null && countryNameNode != null) {
                return cityNode.textValue() + ", " + countryNameNode.textValue();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
