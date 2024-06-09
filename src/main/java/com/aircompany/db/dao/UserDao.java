package com.aircompany.db.dao;

import com.aircompany.db.entity.Crewmate;
import com.aircompany.db.entity.Entity;
import com.aircompany.db.entity.Password;
import com.aircompany.db.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends EntityDao{
    public UserDao(Connection connection) {
        super(connection, DaoManager.USER_TABLE);
    }

    public void create(User entity, String password) throws Exception{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table
                + " (id, login, email, password, salt, role) VALUES (?, ?, ?, ?, ?, ?)");

        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(password, salt);

        statement.setString(1, entity.getId());
        statement.setString(2, entity.getLogin());
        statement.setString(3, entity.getEmail());
        statement.setString(4, hash);
        statement.setString(5, salt);
        statement.setString(6, entity.getRole());
        statement.executeUpdate();
    }

    public Entity read(String id) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            User entity = new User();
            entity.setId(resultSet.getString("id"));
            entity.setLogin(resultSet.getString("login"));
            entity.setEmail(resultSet.getString("email"));
            entity.setRole(resultSet.getString("role"));
            return entity;
        }
        return null;
    }

    public Password getPassword(String id) throws SQLException {
        String sql = "SELECT password, salt FROM users WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        ResultSet res = preparedStatement.executeQuery();
        if (res.next()) {
            return new Password(res.getString("password"), res.getString("salt"));
        }
        return null;
    }

    public Entity readByEmail(String email) throws Exception{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            User entity = new User();
            entity.setId(resultSet.getString("id"));
            entity.setEmail(resultSet.getString("email"));
            entity.setLogin(resultSet.getString("login"));
            entity.setRole(resultSet.getString("role"));
            return entity;
        }
        return null;
    }

    public void update(User entity) throws Exception{
        PreparedStatement statement = connection.prepareStatement("UPDATE " + table
                + " SET login = ?, email = ? WHERE id = ?");
        statement.setString(1, entity.getLogin());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getId());
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
            User entity = new User();
            entity.setId(resultSet.getString("id"));
            entity.setLogin(resultSet.getString("login"));
            entity.setEmail(resultSet.getString("email"));
            entity.setRole(resultSet.getString("role"));
            entities.add(entity);
        }
        return entities;
    }
}