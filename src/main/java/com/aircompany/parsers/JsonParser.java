package com.aircompany.parsers;

import com.aircompany.db.entity.*;
import com.aircompany.servlets.utils.RequestPack;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonParser {

    public static String toJsonObject(Object entity) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public static String toJsonIds(List<String> ids) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ids);
    }

    public static RequestPack parseRequest(String json) throws  JsonProcessingException{
        if(json.isEmpty()){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<RequestPack> reference = new TypeReference<RequestPack>() {};
        return mapper.readValue(json, reference);
    }

    public static User parseUser(String json) throws JsonProcessingException {
        if(json.isEmpty()){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<User> reference = new TypeReference<User>() {};
        return mapper.readValue(json, reference);
    }

//    public static String toJsonObject(List<Entity> entities) throws Exception{
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(entities);
//    }

    public static Crewmate parseCrewmate(String json) throws Exception{
        if(json.isEmpty()){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Crewmate> reference = new TypeReference<Crewmate>() {};
        return mapper.readValue(json, reference);
    }

    public static Race parseRace(String json) throws Exception{
        if(json.isEmpty()){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Race> reference = new TypeReference<Race>() {};
        return mapper.readValue(json, reference);
    }
    public static Plane parsePlane(String json) throws Exception{
        if(json.isEmpty()){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Plane> reference = new TypeReference<Plane>() {};
        return mapper.readValue(json, reference);
    }
    public static Brigade parseBrigade(String json) throws Exception{
        if(json.isEmpty()){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Brigade> reference = new TypeReference<Brigade>() {};
        return mapper.readValue(json, reference);
    }
    public static Flight parseFlight(String json) throws Exception{
        if(json.isEmpty()){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Flight> reference = new TypeReference<Flight>() {};
        return mapper.readValue(json, reference);
    }

}