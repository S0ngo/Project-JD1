package DAO;

import Connection.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDaoSupportImpl implements JdbcDaoSupport {
    
    private Statement STM;
    private ResultSet rs;
    
    public JdbcDaoSupportImpl() {
        try {
            STM = ConnectionManager.getInstance().getConnection().createStatement();
        }
        catch (SQLException e) {
            System.out.println("Невозможно создать PreparedStatement" + e);
        }
    }   

    @Override
    public int getDistrictId (String District) {
        try {
            rs = STM.executeQuery("select id from districts where DistrictName= \"" + District + "\"");
            while (rs.next()) {
                int sourceID = rs.getInt("id");
                return sourceID;
            }
        }
        catch (SQLException e) {
            System.out.println("Невозможно получить ID района" + e);
        }
        return 0;
    }
    
    @Override
    public String[] getPathDistricts (String path) {
        int i = 0;
        String[] str = new String[10];
        try {
            rs = STM.executeQuery("select DistrictName from districts where id IN (" + path + ") ORDER BY FIELD(id, " + path + ")");
            while (rs.next()) {
                str [i] = rs.getString("DistrictName");
                i++;
            }
        return str;   
        }
        catch (SQLException e) {
            System.out.println("Невозможно получить путь" + e);
        }
        return null;
    }

    @Override
    public int[][] getGraph() {
        
        int[][] arr = new int[100][3];
        int i = 0;

        try {
            rs = STM.executeQuery("select districtid1,districtid2,trafikload from districtsconnections");
            while (rs.next()) {
                arr[i][0] = rs.getInt("districtid1");   
                arr[i][1] = rs.getInt("districtid2");
                arr[i][2] = rs.getInt("trafikload");
                i++;
            }
          return arr;
        }
        catch (SQLException e) {
            System.out.println("Невозможно получить путь" + e);
        }
        return null;
    }

    @Override
    public int getEdgeExist(int sourceID, int destID) {
        try {
            rs = STM.executeQuery("select id from districtsconnections where (districtid1 = " + sourceID + " AND districtid2 = " + destID + ") OR (districtid1 = " + destID + " AND districtid2 = " + sourceID + ")");
         while (rs.next()) {
            int edgeExist = rs.getInt("id");
                return edgeExist;
            }
        }
        catch (SQLException e) {
            System.out.println("Невозможно получить дугу" + e);
        }
        return 0;
    }   
    
    @Override
    public int getAverageTrafikLoad () {
        try {
            rs = STM.executeQuery("select  avg(trafikload) AS trafikload from districtsconnections where trafikload >= 0 ");
            while (rs.next()) {
                int trafikLoad = rs.getInt("trafikload"); 
                return trafikLoad;
            }
        }
        catch (SQLException e) {
            System.out.println("Невозможно получить информацию о пробке" + e);
        }
        return 0;
    }    
    
    @Override
    public String getUserName (String username) {
        try {
            rs = STM.executeQuery("select username from users where username= \"" + username + "\"");
            while (rs.next()) {
                String User = rs.getString("username");
                return User;
            }
        }
        catch (SQLException e) {
            System.out.println("Невозможно получить имя пользователя" + e);
        }
        return null;
    } 
    
    @Override
    public String[] getUser (String username, String passhash) {
        try {
            rs = STM.executeQuery("select login,role from users where username= \"" + username + "\" AND passhash =  \"" + passhash + "\"");
        while (rs.next()) {
                String role = rs.getString("role");     
                String login = rs.getString("login"); 
                String[] str = {role, login};
                return str;
            }
        }
        catch (SQLException e) {
            System.out.println("Невозможно получить пользователя" + e);
        }
        return null;
    }     

    public void addUser (String username, String login, String role, String passhash) {
        try {
            STM.executeUpdate("INSERT INTO users(username,login,role,passhash) VALUES (\"" + username + "\", \"" + login + "\", \"" + role + "\", \"" + passhash + "\")");
        }
        catch (SQLException e) {
            System.out.println("Невозможно внести информацию в БД" + e);
        }
    }        
    
    public void deleteUser (String username) {
        try {
            STM.executeUpdate("Delete from users where username= \"" + username + "\"");
        }
        catch (SQLException e) {
            System.out.println("Невозможно внести информацию в БД" + e);
        }
    }     
    
    public void setTrafikLoad (int traficLoad, int edgeExist) {
        try {
            STM.executeUpdate("UPDATE districtsconnections SET trafikload = " + traficLoad + " WHERE (id = " + edgeExist + ")");
        }
        catch (SQLException e) {
            System.out.println("Невозможно внести информацию в БД" + e);
        }
    }
    
    public void setEdge (int sourceID, int destID, int trafikLoad) {
        try {
            STM.executeUpdate("INSERT INTO districtsconnections(districtid1,districtid2,trafikload) VALUES (\"" + sourceID + "\", \"" + destID + "\", \"" + trafikLoad + "\")");
        }
        catch (SQLException e) {
            System.out.println("Невозможно внести информацию в БД" + e);
        }
    }       
    
}
