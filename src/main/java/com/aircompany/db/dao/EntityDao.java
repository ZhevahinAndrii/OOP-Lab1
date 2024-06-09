package com.aircompany.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityDao {
    protected final Connection connection;
    protected final String table;

    public EntityDao(Connection connection, String table){
        this.connection = connection;
        this.table = table;
    }

    public List<String> readIds() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM " + table);
        ResultSet resultSet = statement.executeQuery();
        List<String> ids = new ArrayList<>();
        while (resultSet.next()){
            ids.add(resultSet.getString("id"));
        }
        return ids;
    }
}