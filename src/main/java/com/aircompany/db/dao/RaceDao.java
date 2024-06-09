package com.aircompany.db.dao;

import com.aircompany.db.entity.Entity;
import com.aircompany.db.entity.Race;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RaceDao extends EntityDao{

    public RaceDao(Connection connection) {
        super(connection, DaoManager.RACES_TABLE);
    }
    public void create(Race entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table
                + " (departure_place, arrival_place, departure_time, arrival_time, passengers, luggage_weight, id) VALUES (?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, entity.getDeparturePlace());
        statement.setString(2, entity.getArrivalPlace());
        statement.setString(3, entity.getDepartureTime());
        statement.setString(4, entity.getArrivalTime());
        statement.setInt(5, entity.getPassengers());
        statement.setDouble(6, entity.getLuggageWeight());
        statement.setString(7, entity.getId());
        statement.executeUpdate();
    }
    public Entity read(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            Race entity = new Race(UUID.fromString(resultSet.getString("id")));
            entity.setDeparturePlace(resultSet.getString("departure_place"));
            entity.setArrivalPlace(resultSet.getString("arrival_place"));
            entity.setDepartureTime(resultSet.getString("departure_time"));
            entity.setArrivalTime(resultSet.getString("arrival_time"));
            entity.setPassengers(resultSet.getInt("passengers"));
            entity.setLuggageWeight(resultSet.getDouble("luggage_weight"));
            return entity;
        }
        return null;
    }

    public void update(Race entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("UPDATE " + table
                + " SET departure_place = ?, arrival_place = ?, departure_time = ?, arrival_time = ?, passengers = ?, luggage_weight = ? WHERE id = ?");
        statement.setString(1, entity.getDeparturePlace());
        statement.setString(2, entity.getArrivalPlace());
        statement.setString(3, entity.getDepartureTime());
        statement.setString(4, entity.getArrivalTime());
        statement.setInt(5, entity.getPassengers());
        statement.setDouble(6, entity.getLuggageWeight());
        statement.setString(7, entity.getId());
        statement.executeUpdate();
    }

    public void delete(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        statement.executeUpdate();
        FlightDao dao = new FlightDao(connection);
        dao.cascadeDelete("race_id", id);
    }

    public List<Entity> readAll() throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Race entity = new Race(UUID.fromString(resultSet.getString("id")));
            entity.setDeparturePlace(resultSet.getString("departure_place"));
            entity.setArrivalPlace(resultSet.getString("arrival_place"));
            entity.setDepartureTime(resultSet.getString("departure_time"));
            entity.setArrivalTime(resultSet.getString("arrival_time"));
            entity.setPassengers(resultSet.getInt("passengers"));
            entity.setLuggageWeight(resultSet.getDouble("luggage_weight"));
            entities.add(entity);
        }
        return entities;
    }

    public List<Entity> readByDeparture(String departurePlace) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE departure_place=?");
        statement.setString(1, departurePlace);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Race entity = new Race(UUID.fromString(resultSet.getString("id")));
            entity.setDeparturePlace(resultSet.getString("departure_place"));
            entity.setArrivalPlace(resultSet.getString("arrival_place"));
            entity.setDepartureTime(resultSet.getString("departure_time"));
            entity.setArrivalTime(resultSet.getString("arrival_time"));
            entity.setPassengers(resultSet.getInt("passengers"));
            entity.setLuggageWeight(resultSet.getDouble("luggage_weight"));
            entities.add(entity);
        }
        return entities;
    }

    public List<Entity> readByArrival(String arrival) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE arrival_place=?");
        statement.setString(1, arrival);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Race entity = new Race(UUID.fromString(resultSet.getString("id")));
            entity.setDeparturePlace(resultSet.getString("departure_place"));
            entity.setArrivalPlace(resultSet.getString("arrival_place"));
            entity.setDepartureTime(resultSet.getString("departure_time"));
            entity.setArrivalTime(resultSet.getString("arrival_time"));
            entity.setPassengers(resultSet.getInt("passengers"));
            entity.setLuggageWeight(resultSet.getDouble("luggage_weight"));
            entities.add(entity);
        }
        return entities;
    }

}