package rough;

public class AnonymousClass {
	public static void main(String[] args) {
		
	Java8 j = new Java8() {

		@Override
		public void print() {
			System.out.println("Printing through anonymous class for an interface");
			
		}
		
	};
	j.print();
	Java8 i = ()->{
		System.out.println("Printing through Lambda Expression");
	};
	i.print();
	
	Java8Features k = new Java8Features() {
		@Override
		public void print() {
			System.out.println("Printing through anonymous class for a concrete class");
		}
	};
	k.print();
	
	Java8Features m = new Java8Features();
	m.print();
	
	
	}
	


}
