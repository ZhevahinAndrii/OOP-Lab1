package com.aircompany.servlets.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestPack {
    private String table;

    public String getTable(){
        return table;
    }
    public void setTable(String table){
        this.table = table;
    }

    public RequestPack(){}

    public static String processRequest(BufferedReader reader) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
}