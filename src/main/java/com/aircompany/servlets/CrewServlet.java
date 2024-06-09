package com.aircompany.servlets;

import com.aircompany.db.dao.BrigadeDao;
import com.aircompany.db.dao.CrewDao;
import com.aircompany.db.dao.DaoManager;
import com.aircompany.db.entity.Crewmate;
import com.aircompany.db.entity.Entity;
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

@WebServlet("/crew")
public class CrewServlet  extends HttpServlet {


    private Entity getEntity(String toParse){
        try {
            return JsonParser.parseCrewmate(toParse);
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
        CrewDao dao = new CrewDao(conn);
        if(entity != null) {

            try {
                dao.update((Crewmate) entity);
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
        CrewDao dao = new CrewDao(conn);
        try {
            dao.create((Crewmate) entity);
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
        CrewDao crewDao = new CrewDao(conn);
        List<Entity> entityList = new ArrayList<>();
        try {
            if(RoleUtil.validateAccess(role, RoleUtil.getAllowedRoles(new String[]{RoleUtil.USER, RoleUtil.DISPATCH, RoleUtil.ADMIN}))){
                switch (field) {
                    case "name":
                        entityList = crewDao.readByName(value);
                        break;
                    case "qualification":
                        entityList = crewDao.readByQualification(value);
                        break;
                    case "id":
                        entityList.add(crewDao.read(value));
                        break;
                    case "all":
                        entityList = crewDao.readAll();
                        break;
                    default:
                        if(RoleUtil.validateAccess(role, RoleUtil.getAllowedRoles(new String[]{RoleUtil.DISPATCH, RoleUtil.ADMIN}))) {
                            switch (field) {
                                case "delete":
                                    crewDao.delete(value);
                                    entityList = crewDao.readAll();
                                    break;
                                case "brigades":
                                    BrigadeDao dao = new BrigadeDao(conn);
                                    resp.getWriter().println(JsonParser.toJsonIds(dao.readIds()));
                                    return;
                                default:
                                    resp.getWriter().println("[]");
                                    return;
                            }
                        }
                        else {
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