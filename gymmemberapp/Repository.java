package gymmemberapp;

import Models.*;
import java.sql.*;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            PreparedStatement stmt = con.prepareStatement(allOrOneName("select * from member", name))){
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
            PreparedStatement stmt = con.prepareStatement(allOrOneName("select * from exerciseType", name))){
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
    
    public List<GroupSession> getGroupSessions(){
        List<GroupSession> groupSessions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String query = "select groupSession.ID as 'groupSessionID', " +
                       "exerciseType.name as 'type', " +
                       "groupSession.capacity as 'capacity', " +
                       "hall.name as 'hall', " +
                       "duration.minutes as 'minutes', " +
                       "trainer.name as 'trainer', " +
                       "session.scheduled as 'scheduled', " +
                       "session.ID as 'sessionID' " +
                       "from groupSession " +
                       "inner join session on groupSession.ID = session.ID " +
                       "inner join hall on session.hallID = hall.ID " +
                       "inner join duration on session.durationID = duration.ID " +
                       "inner join trainer on session.trainerID = trainer.ID " +
                       "inner join exerciseType on groupSession.exerciseTypeID = exerciseType.ID;";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                GroupSession temp = new GroupSession();
                temp.setCapacity(rs.getInt("capacity"));
                temp.getDuration().setMinutes(rs.getInt("minutes"));
                temp.getExerciseType().setName(rs.getString("type"));
                temp.setGroupSessionID(rs.getInt("groupSessionID"));
                temp.setSessionID(rs.getInt("sessionID"));
                temp.getHall().setName(rs.getString("hall"));
                temp.getTrainer().setName(rs.getString("trainer"));
                temp.setTimeScheduled(LocalDateTime.parse(rs.getString("scheduled").substring(0, 19), formatter));
                groupSessions.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupSessions;
    }
    
    public String allOrOneName(String query, String name){
        return name.length() > 0 ? query + " where name = ?" : query;
    }
    
    public String allOrOneID(String query, String ID){
        return ID.length() > 0 ? query + " where name = ?" : query;
    }
    
}