package DAO;

import java.util.Scanner;

public class IoDaoSupportImpl implements IoDaoSupport  {
    
    private final Scanner in = new Scanner(System.in);
    
    @Override
    public void Print (String str) {
        System.out.print(str);
        
    }
    
    @Override
    public void Print (String str, String modifier) {
        System.out.println(str);
        
    }    
    
    @Override
    public String Input () {
        String Input = in.nextLine(); 
        return Input;
        
    }    
    
}
