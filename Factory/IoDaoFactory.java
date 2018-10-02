package Factory;

import DAO.IoDaoSupport;
import DAO.IoDaoSupportImpl;

public class IoDaoFactory {
    
    private static final IoDaoSupport IoDao = new IoDaoSupportImpl();

    public static IoDaoSupport getIoDao() {
        return IoDao;
    }

}

