package gymmemberapp;

import Models.*;
import java.sql.*;
import java.sql.DriverManager;
import java.util.*;

public class Repository {
    
    LogInfo logInfo;

    public Repository() {
        logInfo = new LogInfo();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<Member> getMembers(String name){
        List<Member> members = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(allOrOne("select * from member", name))){
            if(name.length() > 0)
                stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Member temp = new Member();
                temp.setName(rs.getString("name"));
                temp.setID(rs.getInt("ID"));
                members.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    
    public List<ExerciseType> getExerciseTypes(String name){
        List<ExerciseType> exerciseTypes = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(allOrOne("select * from exerciseType", name))){
            if(name.length() > 0)
                stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ExerciseType temp = new ExerciseType();
                temp.setName(rs.getString("name"));
                exerciseTypes.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exerciseTypes;
    }
    
    public String allOrOne(String query, String name){
        return name.length() > 0 ? query + " where name = ?" : query;
    }
}