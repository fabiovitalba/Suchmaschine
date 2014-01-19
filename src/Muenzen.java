
class List<T> {
	T head;
	List<T> tail;
	
	List(T head, List<T> tail) {
		this.head = head;
		this.tail = tail;
	}
	
	/** Generic helper function for list creation. You DO NOT NEED to call this function. */
	static <U> List<U> node(U head, List<U> tail) {
		return new List<U>(head, tail);
	}
}

public class Muenzen {

	static int numPossibilities(int sum, int max, List<Integer> remainingCoins) {
//		TODO
		return 0;
	}
	
	public static void main(String[] args) {
		// Given a list of coins...
		List<Integer> coins = List.node(1, List.node(2, List.node(5,  List.node(10,
				List.node(20, List.node(50, List.node(100,  List.node(200, null))))))));

		// For example, the number of possibilities to get a total value of 175 is 41454.
		int result = 0;
		
		System.out.println("There are " + result + " number of ways.");
	}

}
