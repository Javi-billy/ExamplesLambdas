import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Primero {

	Comparator<String> resultado = (o1,o2) -> o1.length() - o2.length();
	
	public static void main(String[] args) {
		
		 	String string = "Item1 10 Item2 25 Item3 30 Item4 45";

	        Integer sum = Arrays.stream(string.split(" "))
	            .filter((w) -> w.matches("\\d+"))
	            .mapToInt(Integer::valueOf)
	            .sum();
	        
	        List<User> users = Arrays.asList(new User("John", 30), new User("Julie", 35));
	        //int computedAges = users.stream().reduce(0, (p, u) -> p + u.getAge());
	        
	        int result = users.stream().reduce(0, (p, u) -> p + u.getAge(), Integer::sum);
	        
	        
	        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
	        int result2 = numbers.stream().reduce(0, (subtotal, element) -> subtotal + element);
	        System.out.println(result);
	        
	        int iiii = users.stream().mapToInt(User::getAge).reduce(0, (partialAgeResult, u) -> partialAgeResult + u);	        	        
	        
	        int ii = users.stream().map(User::getAge).reduce(0, Integer::sum);
	        
	        System.out.println("=============");
	        experssion();
	}
	
	public static void ejercicio1 () {
		List<String> list = new ArrayList<>();
		list.add("asder");
		list.add("fdgsg");
		list.add("ads");
		list.add("sdf");
		list.add("asx");
		
		 Predicate<String> predicate1 =  str -> str.startsWith("a");
	     Predicate<String> predicate2 =  str -> str.length() == 3;
		
		List<String> v = list.stream().filter(str -> str.startsWith("a"))
				.filter( str -> str.length() == 3).collect(Collectors.toList());
		
	}
	/*
	 * Escriba un método que devuelva una cadena separada por comas basada en una lista dada de enteros. 
	 * Cada elemento debe estar precedido por la letra 'e' si el número es par, y precedido por la letra 'o' si el número es impar.
	 * Por ejemplo, si la lista de entrada es (3,44), la salida debería ser 'o3,e44'.
	 */
	public static void ejercicio2 () {
		List<Integer> list = new ArrayList<>();
		list.add(23);list.add(77);list.add(44);list.add(3);
		
		String h = list.stream()
		.map(p -> p%2 == 0 ? "e"+p : "o"+p).collect(Collectors.joining(","));
		
	}
	
	/*
	 * Escriba los siguientes métodos que devuelvan una expresión lambda que realice una acción específica:
	 * PerformOperation isOdd(): la expresión lambda debe devolver verdadero si un número es impar o falso si es par.
	 * PerformOperation isPrime(): la expresión lambda debe devolver verdadero si un número es primo o falso si es compuesto.
	 * PerformOperation isPalindrome(): la expresión lambda debe devolver verdadero si un número es un palíndromo o falso si no lo es.
	 */
	
	public static void experssion() {
				        
        int condition = 3;
        int number = 1321;
        
        
            

            switch (condition) {
                case 1:
                	System.out.println(isOdd().check(number) ? "ODD" : "EVEN");
                	break;
                case 2:
                	System.out.println(isPrime().check(number) ? "PRIME" : "COMPOSITE");
                	break;
                case 3:
                	System.out.println(isPalindrome().check(number) ? "PALINDROME" : "NOT PALINDROME");
                	break;
                	
            }

        
       System.out.println();
	}

    static PerformOperation isOdd() {
        return number -> number % 2 != 0;
    }

    static PerformOperation isPrime() {
        return number -> BigInteger.valueOf(number).isProbablePrime(1);
    }

    static PerformOperation isPalindrome() {
        return number -> String.valueOf(number).equals(new StringBuilder(String.valueOf(number)).reverse().toString());
    }
	
	//public static Predicate<Integer> isOdd = p -> p%2 == 0 ? true : false;
	//public static Predicate<Integer> isPrime = p -> BigInteger.valueOf(p).isProbablePrime(1);
	//public static Predicate<Integer> isPalindrome = p -> p.toString().equals(new StringBuilder(p).reverse().toString()) ? true : false;
}
