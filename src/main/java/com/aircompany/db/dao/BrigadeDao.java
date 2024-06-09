package com.aircompany.db.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.aircompany.db.entity.Brigade;
import com.aircompany.db.entity.Entity;
public class BrigadeDao extends EntityDao{
    public BrigadeDao(Connection connection) {
        super(connection, DaoManager.BRIGADES_TABLE);
    }

    public void create(Brigade entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table
                + " (name, is_static, id) VALUES (?, ?, ?)");
        statement.setString(1, entity.getName());
        statement.setBoolean(2, entity.isStaticCrew());
        statement.setString(3, entity.getId());
        statement.executeUpdate();
    }

    public Entity read(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            Brigade entity = new Brigade(UUID.fromString(resultSet.getString("id")));
            entity.setName(resultSet.getString("name"));
            entity.setStaticCrew(resultSet.getBoolean("is_static"));
            return entity;
        }
        return null;
    }

    public void update(Brigade entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("UPDATE " + table
                + " SET name = ?, is_static = ? WHERE id = ?");
        statement.setString(1, entity.getName());
        statement.setBoolean(2, entity.isStaticCrew());
        statement.setString(3, entity.getId());
        statement.executeUpdate();
    }

    public void delete(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        statement.executeUpdate();
        FlightDao dao = new FlightDao(connection);
        dao.cascadeDelete("brigade_id", id);
        CrewDao crewDao = new CrewDao(connection);
        crewDao.updateBrigade(id);
    }

    public List<Entity> readAll() throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table);
        ResultSet resultSet = statement.executeQuery();
        List<Entity> entities = new ArrayList<>();
        while (resultSet.next()){
            Brigade entity = new Brigade(UUID.fromString(resultSet.getString("id")));
            entity.setName(resultSet.getString("name"));
            entity.setStaticCrew(resultSet.getBoolean("is_static"));
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
            Brigade entity = new Brigade(UUID.fromString(resultSet.getString("id")));
            entity.setName(resultSet.getString("name"));
            entity.setStaticCrew(resultSet.getBoolean("is_static"));
            entities.add(entity);
        }
        return entities;
    }

}
