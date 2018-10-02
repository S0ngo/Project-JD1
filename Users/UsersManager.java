package Users;

import DAO.IoDaoSupport;
import DAO.JdbcDaoSupport;
import Factory.IoDaoFactory;
import Factory.JdbcDaoFactory;

public class UsersManager {
    
    private User User;
    private final JdbcDaoSupport JDBCDAO = JdbcDaoFactory.getJdbcDao();
    private final IoDaoSupport IO = IoDaoFactory.getIoDao();
    
    private String modifier;
    
    public void addUser () {
        
        IO.Print("Введите желаемое имя: ");
        String username = IO.Input();
        
        if (JDBCDAO.getUserName(username) == null) {
            IO.Print("Введите логин: ");        
            String login = IO.Input();
            IO.Print("Введите пароль: ");        
            String passhash = IO.Input();
            IO.Print("Права доступа (admin или user): ");        
            String role = IO.Input();
            JDBCDAO.addUser (username, login, role, passhash);
            IO.Print("Пользователь " + username + " создан!");
        }     
        else           
            IO.Print("Такой пользователь уже существует, придумайте другого", modifier);
    }
    
    public void deleteUser () {
        IO.Print("Введите имя пользователя которого хотите удалить: ");
        String username = IO.Input();  
        JDBCDAO.deleteUser(username);
        IO.Print("Пользователь " + username + " удален!", modifier); 
    }  
    
    public User logon () {
        IO.Print("Введите имя: ");
        String username = IO.Input();
        IO.Print("Введите пароль: ");
        String passhash = IO.Input();    
        
        String[] str = JDBCDAO.getUser(username, passhash);
        if (str != null){
            String role = str [0]; 
            String login = str [1];   
            IO.Print("Добро пожаловать, " + login, modifier);
            User = new User (login, role);
        }
        else {  
            IO.Print("логин или пароль не верны или не существуют, попробуйте еще раз", modifier);
            logon ();
        }
        return User;
    }       
 
}
