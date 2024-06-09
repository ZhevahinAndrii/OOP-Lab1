package com.aircompany.db.dao;

import com.aircompany.db.entity.Entity;
import com.aircompany.db.entity.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlightDao extends EntityDao{
    public FlightDao(Connection connection) {
        super(connection, DaoManager.FLIGHTS_TABLE);
    }

    public void create(Flight entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table
                + " (race_id, brigade_id, plane_id, id) VALUES (?, ?, ?, ?)");
        statement.setString(1, entity.getRaceId());
        statement.setString(2, entity.getBrigadeId());
        statement.setString(3, entity.getPlaneId());
        statement.setString(4, entity.getId());
        statement.executeUpdate();
    }

    public Entity read(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            Flight entity = new Flight(UUID.fromString(resultSet.getString("id")));
            entity.setRaceId(resultSet.getString("race_id"));
            entity.setBrigadeId(resultSet.getString("brigade_id"));
            entity.setPlaneId(resultSet.getString("plane_id"));
            return entity;
        }
        return null;
    }

    public void update(Flight entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("UPDATE " + table
                + " SET race_id = ?, brigade_id = ?, plane_id = ? WHERE id = ?");
        statement.setString(1, entity.getRaceId());
        statement.setString(2, entity.getBrigadeId());
        statement.setString(3, entity.getPlaneId());
        statement.setString(4, entity.getId());
        statement.executeUpdate();
    }

    public void delete(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        statement.executeUpdate();
    }

    public List<Entity> readAll() throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Flight entity = new Flight(UUID.fromString(resultSet.getString("id")));
            entity.setRaceId(resultSet.getString("race_id"));
            entity.setBrigadeId(resultSet.getString("brigade_id"));
            entity.setPlaneId(resultSet.getString("plane_id"));
            entities.add(entity);
        }
        return entities;
    }

    public List<String> getIds(String table) throws SQLException {
        switch (table){
            case "race":
                RaceDao raceDao = new RaceDao(connection);
                return raceDao.readIds();
            case "plane":
                PlaneDao planeDao = new PlaneDao(connection);
                return planeDao.readIds();
            case "brigade":
                BrigadeDao brigadeDao = new BrigadeDao(connection);
                return brigadeDao.readIds();
        }
        return new ArrayList<>();
    }

    public void cascadeDelete(String field, String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM " + table + " WHERE " + field + " = ?");
        statement.setString(2, id);
        statement.executeUpdate();
    }
}