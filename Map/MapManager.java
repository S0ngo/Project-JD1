package Map;

import DAO.IoDaoSupport;
import DAO.JdbcDaoSupport;
import Dijkstra.Dijkstra;

import Factory.IoDaoFactory;
import Factory.JdbcDaoFactory;

import static java.lang.Integer.parseInt;
import java.util.stream.Collectors;

public class MapManager {

    private Dijkstra Map;
    private final JdbcDaoSupport JDBCDAO = JdbcDaoFactory.getJdbcDao();
    private final IoDaoSupport IO = IoDaoFactory.getIoDao();
    
    private String modifier;
    
    public Dijkstra CreateMap () {
        
        int[][] arr = JDBCDAO.getGraph();
        Graph Edge = new Graph(arr.length); //количество графов
        for (int i = 0; i < arr.length; i++) {
            Edge.addArc(arr [i][0], arr [i][1],(double) arr [i][2]);
            i++;
        }
        Map = new Dijkstra(Edge);
        return Map;
    }

    public void Route () {
        
        int sourceID;
        int destID;
        String source;
        String dest;
        
        //Ввод 1 района
        IO.Print("Введите район в котором находитесь: ");
        source = IO.Input();   
        sourceID = JDBCDAO.getDistrictId(source);
        if (sourceID == 0) {
            IO.Print("такого района не существует, попробуйте еще раз", modifier);
            return;
        } 
        //Ввод 2 района
        IO.Print("Введите район назначения: ");
        dest = IO.Input();
        destID = JDBCDAO.getDistrictId(dest);     
        if (destID == 0) {
            IO.Print("такого района не существует, попробуйте еще раз", modifier);
        }     
        //Построение пути
        String patch = Map.getPath(sourceID, destID).stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",")); //парсим массив с айди районов в строку
        String[] str = JDBCDAO.getPathDistricts(patch);
        for (int i = 0; i<str.length ; i++) {
            if (str [i]!= null)
                System.out.print(str [i] + " -->");
        }        
        IO.Print("Вы на месте!", modifier);
    }
    
    public void addTraficLoadInfo () {  
        
        int traficLoad;
        int edgeExist;
        int sourceID;
        int destID;
        String source;
        String dest;
        
        //Ввод 1 района
        IO.Print("Введите район в котором находитесь: ");
        source = IO.Input();  
        sourceID = JDBCDAO.getDistrictId(source);
        if (sourceID == 0) {
            IO.Print("такого района не существует, попробуйте еще раз", modifier);
            return;
        } 
        //Ввод 2 района
        IO.Print("Введите район назначения: ");
        dest = IO.Input(); 
        destID = JDBCDAO.getDistrictId(dest);     
        if (destID == 0) {
            IO.Print("такого района не существует, попробуйте еще раз", modifier);
        }     
        //Проверка существует ли такая дуга
        edgeExist = JDBCDAO.getEdgeExist(sourceID, destID);
        if (edgeExist == 0) {
            IO.Print("такого пути не существует, попробуйте еще раз", modifier); 
            return;
        }
        //Ввод информации о пробке
        IO.Print("Введите информацию о пробке между районами " + source + " и " + dest + " числом от 0 до 10, где 0 - отсутствие пробки: ");
        try {
            traficLoad = parseInt(IO.Input());
        }
        catch (NumberFormatException ex) {
            IO.Print("Ошибка ввода!!!" + ex, modifier);
            return;
        }    
        //Обновление информации о пробке
        if (traficLoad >= 0 && traficLoad <= 10) {
           JDBCDAO.setTrafikLoad(traficLoad, edgeExist);
        }
        else {
            IO.Print("не верное число, попробуйте еще раз", modifier);
            return; 
        }
        IO.Print("Информация о пробках обновлена", modifier);
    }
    
    public void addEdge () {  
        
        int trafikLoad;
        int sourceID;
        int destID;
        String source;
        String dest;
        
        //Ввод 1 района
        IO.Print("Введите первый район: ");
        source = IO.Input();  
        sourceID = JDBCDAO.getDistrictId(source);
        if (sourceID == 0) {
            IO.Print("такого района не существует, попробуйте еще раз", modifier);
            return;
        } 
        //Ввод 2 района
        IO.Print("Введите район с которым он связан: ");
        dest = IO.Input();
        destID = JDBCDAO.getDistrictId(dest);     
        if (destID == 0) {
            IO.Print("такого района не существует, попробуйте еще раз", modifier);
        }   
        //Ввод информации о пробке
        IO.Print("Введите информацию о пробке между районами " + source + " и " + dest + " числом от 0 до 10, где 0 - отсутствие пробки, если информация о пробках отсутствует введите -1 - будет выставленно среднее по городу: ");
        try {
            trafikLoad = parseInt(IO.Input());
        }
        catch (NumberFormatException ex) {
            IO.Print("Ошибка ввода, попробуйте еще раз" + ex, modifier);
            return;
        }  
        //Берем среднюю загруженность по городу
        if (trafikLoad == -1) {
            trafikLoad = JDBCDAO.getAverageTrafikLoad();
        }    
        //Добавление дуги
        if (trafikLoad >= 0 && trafikLoad <= 10) {
            JDBCDAO.setEdge(sourceID, destID, trafikLoad);
        }
        else {
            IO.Print("не верное число, попробуйте еще раз", modifier);
            return; 
        }
        IO.Print("Новый путь добавлен", modifier);
    }    
    
}