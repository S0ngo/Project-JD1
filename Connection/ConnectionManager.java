package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    
    private static ConnectionManager singleInstance;
    private Connection JDBCconnection;
    
    private String url = "jdbc:mysql://localhost:3306/project jd1?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String user = "root";
    private String pwd = "12345";
    
    private ConnectionManager() {
        
        try {
            JDBCconnection = DriverManager.getConnection(url, user, pwd);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static ConnectionManager getInstance(){
        if(singleInstance == null){
            singleInstance = new ConnectionManager();
        }
        return singleInstance;
    }

    public Connection getConnection() {
        return JDBCconnection;
    }

    public void closeConnection() {
        if (JDBCconnection == null) {
            System.out.println("Не найдено соединения");
        }
        else {
            try {
                JDBCconnection.close();
                JDBCconnection = null;
            }
            catch (SQLException e) {
                System.out.println("Невозможно закрыть соединение" + e);
            }
        }
    }    
    
}  
