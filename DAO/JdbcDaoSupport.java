
package DAO;

public interface JdbcDaoSupport {
    public int getDistrictId (String District); //Принимает имя района, возвращает ID района
    public String[] getPathDistricts (String path); //Принимает строку с ID районов пути, возвращает массив с именами районов пути по порядку
    public int[][] getGraph(); //Возвращает массив дуг, где первая колонка номер дуги, вторая вершины дуг и нагрузка по порядку.
    public int getEdgeExist(int sourceID, int destID);//принимает ID районов, возвращает номер дуги их связывающий
    public int getAverageTrafikLoad ();//возвращает среднюю пробку по городу
    public String getUserName (String username);//Возвращает имя пользователя
    public String[] getUser (String username, String passhash);//Возвращает массив строк из прав доступа и логина переданного пользователя
    public void addUser (String username, String login, String role, String passhash);
    public void deleteUser (String username);
    public void setTrafikLoad (int traficLoad, int edgeExist);
    public void setEdge (int sourceID, int destID, int trafikLoad);
    
}
