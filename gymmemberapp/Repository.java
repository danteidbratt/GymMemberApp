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

    public List<Member> getMembers(String nameID){
        List<Member> members = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(allOrOne("select * from member", nameID))){
            if(nameID.length() > 0)
                stmt.setString(1, nameID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Member temp = new Member();
                temp.setName(rs.getString("name"));
                temp.setID(rs.getInt("ID"));
//                temp.setGroupSessions(getGroupSessionsInMember(String.valueOf(temp.getID())));
                members.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    
    public List<ExerciseType> getExerciseTypes(String nameID){
        List<ExerciseType> exerciseTypes = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(allOrOne("select * from exerciseType", nameID))){
            if(nameID.length() > 0)
                stmt.setString(1, nameID);
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
    
    public List<GroupSession> getGroupSessions(String groupSessionID){
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
                       "inner join exerciseType on groupSession.exerciseTypeID = exerciseType.ID";
        if (groupSessionID.length() > 0)
            query = query + " where groupSession.ID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query)){
            if (groupSessionID.length() > 0)
                stmt.setString(1, groupSessionID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                GroupSession temp = new GroupSession();
                temp.setCapacity(rs.getInt("capacity"));
                temp.setTimeSpan(LocalDateTime.parse(rs.getString("scheduled").substring(0, 19), formatter), rs.getInt("minutes"));
//                temp.getDuration().setMinutes(rs.getInt("minutes"));
                temp.getExerciseType().setName(rs.getString("type"));
                temp.setGroupSessionID(rs.getInt("groupSessionID"));
                temp.setSessionID(rs.getInt("sessionID"));
                temp.getHall().setName(rs.getString("hall"));
                temp.getTrainer().setName(rs.getString("trainer"));
//                temp.setTimeScheduled(LocalDateTime.parse(rs.getString("scheduled").substring(0, 19), formatter));
                temp.setParticipants(getMembersInGroup(String.valueOf(rs.getInt("groupSessionID"))));
                groupSessions.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupSessions;
    }
    
    private List<Member> getMembersInGroup(String groupSessionID){
        List<Member> membersInGroup = new ArrayList<>();
        String query = "select * from booking where groupSessionID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1, groupSessionID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                membersInGroup.add(getMembers(String.valueOf(rs.getInt("memberID"))).get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersInGroup;
    }
    
    public List<GroupSession> getGroupSessionsInMember(String memberID){
        List<GroupSession> groupSessionsInMember = new ArrayList<>();
        String query = "select groupSessionID as 'groupSessionID' from booking where memberID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1, memberID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                groupSessionsInMember.add(getGroupSessions(String.valueOf(rs.getInt("groupSessionID"))).get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupSessionsInMember;
    }
    
    public int makeGroupSessionReservation(String memberID, String groupSessionID){
        String query = "insert into booking (memberID, groupSessionID) values (?,?)";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1, memberID);
            stmt.setString(2, groupSessionID);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int removeGroupSession(String memberID, String groupSessionID){
        String query = "delete from booking where memberID = ? and groupSessionID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1, memberID);
            stmt.setString(2, groupSessionID);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public String allOrOne(String query, String nameID){
        if(nameID.length() > 0)
            return isInteger(nameID) ? query + " where ID = ?" : query + " where name = ?";
        return query;
    }
    
    public boolean isInteger(String s){
        try{
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}