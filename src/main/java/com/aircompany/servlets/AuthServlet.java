package com.aircompany.servlets;

import com.aircompany.db.dao.DaoManager;
import com.aircompany.db.dao.UserDao;
import com.aircompany.db.entity.Password;
import com.aircompany.db.entity.User;
import com.aircompany.parsers.JsonParser;
import com.aircompany.servlets.token.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.Connection;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        BufferedReader reader = req.getReader();
//        StringBuilder requestBody = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            requestBody.append(line);
//        }
//        String requestBodyString = requestBody.toString();
//        System.out.println(requestBodyString);
//        User user = JsonParser.parseUser(requestBodyString);
//        String responseData = "NULL";
//        if(user != null) {
//            responseData = "Hello " + user.getLogin() + " (" + user.getPassword() + ") from Servlet!";
//        }
////        resp.setContentType("text/plain");
////        resp.setCharacterEncoding("UTF-8");
//        resp.getWriter().write(responseData);
//    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
//        DaoManager mgr = new DaoManager();
//        Connection conn = mgr.getConnection();
//        UserDao dao = new UserDao(conn);
//        try {
//            dao.create(new User(UUID.randomUUID().toString(), "terro_wrist", "allah@gmail.com", "dispatch"), "inshallah");
//            dao.create(new User(UUID.randomUUID().toString(), "gapeHorn", "thug@gmail.com", "admin"), "shaker");
//            dao.create(new User(UUID.randomUUID().toString(), "adolf", "heil@gmail.com", "admin"), "1488");
//            dao.create(new User(UUID.randomUUID().toString(), "moe_lester", "moe_lester@gmail.com", "dispatch"), "2222");
//            dao.create(new User(UUID.randomUUID().toString(), "HueJS", "hue@gmail.com", "user"), "1111");
//            dao.create(new User(UUID.randomUUID().toString(), "nick_ger", "ngr@gmail.com", "user"), "kkk");
//        } catch (Exception e) {
//            throw new RemoteException(e.getMessage());
//        }
        String login = req.getParameter("login");
        String pwd = req.getParameter("password");
        String email = req.getParameter("email");
        if(login == null || pwd == null || email == null){
            resp.getWriter().println("[]");
            return;
        }
        DaoManager mgr = new DaoManager();
        Connection conn = mgr.getConnection();
        UserDao dao = new UserDao(conn);
        User user = null;
        Password pass = null;
        try {
            user = (User) dao.readByEmail(email);
            if(user == null){
                throw new Exception();
            }
            pass = dao.getPassword(user.getId());
            String hashedPass = BCrypt.hashpw(pwd, pass.getSalt());
            if(!hashedPass.equals(pass.getPassword())){
                throw new Exception();
            }
        } catch (Exception e) {
            resp.getWriter().println("[]");
            return;
        }

        Algorithm algorithm = Algorithm.HMAC256("baeldung");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Baeldung")
                .build();
        String jwt = JWT.create()
                .withIssuer("Baeldung")
                .withClaim("id", user.getId())
                .withClaim("login", user.getLogin())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole())
                .sign(algorithm);

        Token token = new Token(jwt);
        try {
            String userJson = JsonParser.toJsonObject(token);
            resp.getWriter().println(userJson);
        } catch (Exception e) {
            resp.getWriter().println("[]");
        }
    }
}