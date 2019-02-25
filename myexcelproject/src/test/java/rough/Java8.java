package rough;

import java.awt.print.Printable;

@FunctionalInterface
public interface Java8 {
	
	void print();
	
	static void staticMethod() {
		System.out.println("printing from static method in interface Java8");
		
	}
	
	default void defaultMethod() {
		System.out.println("printing from default method in interface Java8");
	}
	

}
