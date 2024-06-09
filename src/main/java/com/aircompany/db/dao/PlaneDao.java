package com.aircompany.db.dao;

import com.aircompany.db.entity.Entity;
import com.aircompany.db.entity.Plane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class PlaneDao extends EntityDao{

    public PlaneDao(Connection connection) {
        super(connection, DaoManager.PLANES_TABLE);
    }

    public void create(Plane entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table
                + " (model, passenger_seats, max_luggage_weight, max_flight_in_mins, id) VALUES (?, ?, ?, ?, ?)");
        statement.setString(1,entity.getModel());
        statement.setInt(2,entity.getPassengerSeats());
        statement.setDouble(3,entity.getMaxLuggage());
        statement.setInt(4,entity.getMaxFlightInMins());
        statement.setString(5,entity.getId());
        statement.executeUpdate();
    }

    public Entity read(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            Plane entity = new Plane(UUID.fromString(resultSet.getString("id")));
            entity.setMaxLuggage(resultSet.getDouble("max_luggage_weight"));
            entity.setModel(resultSet.getString("model"));
            entity.setMaxFlightInMins(resultSet.getInt("max_flight_in_mins"));
            entity.setPassengerSeats(resultSet.getInt("passenger_seats"));
            return entity;
        }
        return null;
    }

    public void update(Plane entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("UPDATE " + table
                + " SET model = ?, passenger_seats = ?, max_luggage_weight = ?, max_flight_in_mins = ? WHERE id = ?");
        statement.setString(1, entity.getModel());
        statement.setInt(2, entity.getPassengerSeats());
        statement.setDouble(3, entity.getMaxLuggage());
        statement.setInt(4, entity.getMaxFlightInMins());
        statement.setString(5, entity.getId());
        statement.executeUpdate();
    }

    public void delete(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        statement.executeUpdate();
        FlightDao dao = new FlightDao(connection);
        dao.cascadeDelete("plane_id", id);
    }

    public List<Entity> readAll() throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Plane entity = new Plane(UUID.fromString(resultSet.getString("id")));
            entity.setMaxLuggage(resultSet.getDouble("max_luggage_weight"));
            entity.setModel(resultSet.getString("model"));
            entity.setMaxFlightInMins(resultSet.getInt("max_flight_in_mins"));
            entity.setPassengerSeats(resultSet.getInt("passenger_seats"));
            entities.add(entity);
        }
        return entities;
    }

    public List<Entity> readByModel(String model) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE model=?");
        statement.setString(1, model);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Plane entity = new Plane(UUID.fromString(resultSet.getString("id")));
            entity.setMaxLuggage(resultSet.getDouble("max_luggage_weight"));
            entity.setModel(resultSet.getString("model"));
            entity.setMaxFlightInMins(resultSet.getInt("max_flight_in_mins"));
            entity.setPassengerSeats(resultSet.getInt("passenger_seats"));
            entities.add(entity);
        }
        return entities;
    }

}