package rough;
import java.util.function.Predicate;

public class Predicates {
	
	    public static void main(String[] args) 
	    { 
	        // Creating predicate 
	        Predicate<String> lesserthan = i -> (i.equals("abc"));  
	  
	        // Calling Predicate method 
	        System.out.println(lesserthan.test("abc"));  
	    } 
}
