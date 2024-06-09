package com.aircompany.db.dao;

import com.aircompany.db.entity.Crewmate;
import com.aircompany.db.entity.Entity;
import com.aircompany.db.entity.Plane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrewDao extends EntityDao{
    public CrewDao(Connection connection) {
        super(connection, DaoManager.CREW_TABLE);
    }

    public void create(Crewmate entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table
                + " (name, qualification, brigade, id) VALUES (?, ?, ?, ?)");
        statement.setString(1,entity.getName());
        statement.setString(2,entity.getQualification().toString());
        statement.setString(3,entity.getBrigadeId());
        statement.setString(4,entity.getId());
        statement.executeUpdate();
    }

    public Entity read(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            Crewmate entity = new Crewmate(UUID.fromString(resultSet.getString("id")));
            entity.setName(resultSet.getString("name"));
            entity.setQualification(Crewmate.Qualification.valueOf(resultSet.getString("qualification")));
            entity.setBrigadeId(resultSet.getString("brigade"));
            return entity;
        }
        return null;
    }

    public void update(Crewmate entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("UPDATE " + table
                + " SET name = ?, qualification = ?, brigade = ? WHERE id = ?");
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getQualification().toString());
        statement.setString(3, entity.getBrigadeId());
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
            Crewmate entity = new Crewmate(UUID.fromString(resultSet.getString("id")));
            entity.setName(resultSet.getString("name"));
            entity.setQualification(Crewmate.Qualification.valueOf(resultSet.getString("qualification")));
            entity.setBrigadeId(resultSet.getString("brigade"));
            entities.add(entity);
        }
        return entities;
    }

    public List<Entity> readByName(String name) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE name=?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Crewmate entity = new Crewmate(UUID.fromString(resultSet.getString("id")));
            entity.setName(resultSet.getString("name"));
            entity.setQualification(Crewmate.Qualification.valueOf(resultSet.getString("qualification")));
            entity.setBrigadeId(resultSet.getString("brigade"));
            entities.add(entity);
        }
        return entities;
    }

    public List<Entity> readByQualification(String qualification) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE qualification=?");
        statement.setString(1, qualification);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Crewmate entity = new Crewmate(UUID.fromString(resultSet.getString("id")));
            entity.setName(resultSet.getString("name"));
            entity.setQualification(Crewmate.Qualification.valueOf(resultSet.getString("qualification")));
            entity.setBrigadeId(resultSet.getString("brigade"));
            entities.add(entity);
        }
        return entities;
    }

    public void updateBrigade(String brigadeId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE " + table
                + " SET brigade = ? WHERE brigade = ?");
        statement.setString(1, "");
        statement.setString(2, brigadeId);
        statement.executeUpdate();
    }
}
