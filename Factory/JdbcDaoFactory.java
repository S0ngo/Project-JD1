package Factory;

import DAO.JdbcDaoSupport;
import DAO.JdbcDaoSupportImpl;

public class JdbcDaoFactory  {
    
    private static final JdbcDaoSupport JdbcDao = new JdbcDaoSupportImpl();

    public static JdbcDaoSupport getJdbcDao() {
        return JdbcDao;
    }

}

