package rough;

public class Catch {
	public static void main(String[] args) {
		
	//public void loop(String a) {
		int arr[] = {10,20,30,40,50};
		//int i = Integer.parseInt(a);
		int i = 0;
		while(i<arr.length) {
			try {
			if(arr[i]==30) {
				throw new RuntimeException();
			}
			else {
				System.out.println(arr[i]);
			}
			}
			catch(Exception e){
				i++;
				continue;
			}
			i++;
		}
		Lambda l = () -> {
			System.out.println("Printing using Lambda Expression.");
		};
		l.sample();
	}

}
