package com.aircompany.servlets;

import com.aircompany.db.dao.DaoManager;
import com.aircompany.db.dao.RaceDao;
import com.aircompany.db.entity.Entity;
import com.aircompany.db.entity.Race;
import com.aircompany.parsers.JsonParser;
import com.aircompany.servlets.utils.RequestPack;
import com.aircompany.servlets.utils.RoleUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/race")
public class RaceServlet extends HttpServlet {
    public Entity getEntity(String toParse){
        try {
            return JsonParser.parseRace(toParse);
        } catch (Exception e) {
            return null;
        }
    }
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        BufferedReader reader = req.getReader();
        String requestBodyString = RequestPack.processRequest(reader);
        if(!RoleUtil.validateAccess(RoleUtil.getRole(req), RoleUtil.getAllowedRoles(new String[]{RoleUtil.ADMIN, RoleUtil.DISPATCH}))){
            resp.getWriter().println("[]");
            return;
        }
        Entity entity = getEntity(requestBodyString);
        DaoManager DBM = new DaoManager();
        Connection conn = DBM.getConnection();
        RaceDao dao = new RaceDao(conn);
        if(entity != null) {
            try {
                dao.update((Race) entity);
                resp.getWriter().println(JsonParser.toJsonObject(dao.readAll()));
            } catch (Exception e) {
                resp.getWriter().println("[]");
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        BufferedReader reader = req.getReader();
        String requestBodyString = RequestPack.processRequest(reader);
        if(!RoleUtil.validateAccess(RoleUtil.getRole(req), RoleUtil.getAllowedRoles(new String[]{RoleUtil.ADMIN, RoleUtil.DISPATCH}))){
            resp.getWriter().println("[]");
            return;
        }
        Entity entity = getEntity(requestBodyString);
        if(entity == null){
            resp.getWriter().println("[]");
            return;
        }
        entity.setId(UUID.randomUUID().toString());
        DaoManager DBM = new DaoManager();
        Connection conn = DBM.getConnection();
        RaceDao dao = new RaceDao(conn);
        try {
            dao.create((Race) entity);
        } catch (Exception e) {
            resp.getWriter().println("[]");
            return;
        }
        try {
            resp.getWriter().println(JsonParser.toJsonObject(dao.readAll()));
        } catch (Exception e) {
            resp.getWriter().println("[]");
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String field = req.getParameter("field");
        String value = req.getParameter("value");
        if(field == null || value == null){
            resp.getWriter().println("[]");
            return;
        }
        String role = RoleUtil.getRole(req);
        DaoManager mgr = new DaoManager();
        Connection conn = mgr.getConnection();
        if(conn == null){
            resp.getWriter().println("[]");
            return;
        }
        RaceDao dao = new RaceDao(conn);
        List<Entity> entityList = new ArrayList<>();
        try {
            if(RoleUtil.validateAccess(role, RoleUtil.getAllowedRoles(new String[]{RoleUtil.USER, RoleUtil.DISPATCH, RoleUtil.ADMIN}))) {
                switch (field) {
                    case "departure":
                        entityList = dao.readByDeparture(value);
                        break;
                    case "arrival":
                        entityList = dao.readByArrival(value);
                        break;
                    case "id":
                        entityList.add(dao.read(value));
                        break;
                    case "all":
                        entityList = dao.readAll();
                        break;
                    default:
                        if (RoleUtil.validateAccess(role, RoleUtil.getAllowedRoles(new String[]{RoleUtil.USER, RoleUtil.DISPATCH, RoleUtil.ADMIN}))) {
                            switch (field) {
                                case "delete":
                                    dao.delete(value);
                                    entityList = dao.readAll();
                                    break;
                                default:
                                    resp.getWriter().println("[]");
                                    return;
                            }

                        } else {
                            resp.getWriter().println("[]");
                            return;
                        }
                }
            }
        }catch (Exception e){
            resp.getWriter().println("[]");
            return;
        }
        try {
            String res = JsonParser.toJsonObject(entityList);
            resp.getWriter().println(res);
        } catch (Exception e) {
            resp.getWriter().println("[]");
            return;
        }
    }
}