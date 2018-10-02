package LogisticSystem;

import DAO.IoDaoSupport;
import Factory.IoDaoFactory;
import Users.User;

/*
Project JD1 Мацко
*/

public class LogisticSystem {

    private User currentUser;
    
    private final Users.UsersManager user = new Users.UsersManager();
    private final Map.MapManager map = new Map.MapManager();
    private final IoDaoSupport IO = IoDaoFactory.getIoDao();
    
    private String modifier;

    public static void main(String args[]) { 
        LogisticSystem LS = new LogisticSystem();
        LS.login();
        LS.control();

    }    
    
    private void login () {
        
        this.currentUser = user.logon();
    }         
    
    public void control () {
        
        String admin = "Для этого действия надо обладать правами администратора!";
        IO.Print("Введите 1, что бы проложить маршрт: ", modifier);
        IO.Print("Введите 2, что бы сообщить о пробке: ", modifier);
        IO.Print("Введите 3, что бы добавить пользователя: ", modifier);
        IO.Print("Введите 4, что бы удалить пользователя: ", modifier);
        IO.Print("Введите 5, что бы добавить новый путь: ", modifier);
        IO.Print("Введите 0, что бы выйти из программы: ", modifier);       
        switch(IO.Input()) {
            case "1" :
                map.CreateMap();
                map.Route();
                control ();
                return;
            case "2" :
                map.addTraficLoadInfo();
                control ();
                return;
            case "3" :
		if (currentUser.getRole().equals("admin")){
                    user.addUser();                   
                }
                else {
                    IO.Print(admin, modifier);    
                }
                control ();
                return;
            case "4" :
		if (currentUser.getRole().equals("admin")){
                    user.deleteUser();
                }
                else {
                    IO.Print(admin, modifier);    
                }
                control ();
                return;
            case "5" :
		if (currentUser.getRole().equals("admin")){
                    map.addEdge(); 
                }
                else {
                    IO.Print(admin, modifier);    
                }
                control ();
                return;
            case "0" :
                IO.Print("Досвидания, " + currentUser.getLogin(), modifier);
                break;
            default:
                IO.Print("Ошибка ввода!!!", modifier);  
	}         
    }
    
}    

    


