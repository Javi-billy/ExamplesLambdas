package exercises.movies;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExercisesMovies {

	// https://binkurt.blogspot.com/2017/10/exercises-to-study-java-stream-api.html

	public static List<Genre> genres = new ArrayList<>();
	public static List<Movie> movies = new ArrayList<>();
	public static List<Director> directors = new ArrayList<>();

	public static void main(String[] args) {
		
		Genre gen1 = new Genre(1,"Comedia");
		Genre gen2 = new Genre(2,"Terror");
		Genre gen3 = new Genre(3,"Drama");
		genres.add(gen1);genres.add(gen2);genres.add(gen3);
		List<Genre> lm1 = new ArrayList<>(); lm1.add(gen1);
		List<Genre> lm2 = new ArrayList<>(); lm2.add(gen2);
		List<Genre> lm3 = new ArrayList<>(); lm3.add(gen3);
		
		List<Genre> lm12 = new ArrayList<>(); lm12.add(gen1);lm12.add(gen2);
		List<Genre> lm13 = new ArrayList<>(); lm13.add(gen1);lm13.add(gen3);
		List<Genre> lm23 = new ArrayList<>(); lm23.add(gen2);lm23.add(gen3);
		List<Genre> lm123 = new ArrayList<>(); lm123.add(gen1);lm123.add(gen2);lm123.add(gen3);
		
		Movie m1 = new Movie(1,"Pelicula-1",1974,"nose");		
		
		Movie m2 = new Movie(2,"Pelicula-2",1998,"nose");
		m2.setGenres(lm1);
		Movie m3 = new Movie(3,"Pelicula-3",2012,"nose");
		m3.setGenres(lm2);
		Movie m4 = new Movie(4,"Pelicula-4",2007,"nose");
		m4.setGenres(lm12);
		Movie m5 = new Movie(5,"Pelicula-5",1998,"nose");
		m5.setGenres(lm12);
		Movie m6 = new Movie(6,"Pelicula-6",1974,"nose");
		m6.setGenres(lm23);
		Movie m7 = new Movie(7,"Pelicula-7",1974,"nose");
		m7.setGenres(lm13);
		Movie m8 = new Movie(8,"Pelicula-8",2012,"nose");
		m8.setGenres(lm123);
		Movie m9 = new Movie(9,"Pelicula-9",2012,"nose");
		m9.setGenres(lm12);
		Movie m10 = new Movie(10,"Pelicula-10",2007,"nose");
		m10.setGenres(lm3);
		Movie m11 = new Movie(11,"Pelicula-11",2007,"nose");
		m11.setGenres(lm3);
		Movie m12 = new Movie(12,"Pelicula-12",2007,"nose");
		m12.setGenres(lm1);
		movies.add(m1);movies.add(m2);movies.add(m3);movies.add(m4);movies.add(m5);movies.add(m6);
		movies.add(m7);movies.add(m8);movies.add(m9);movies.add(m10);movies.add(m11);movies.add(m12);
				
		Director d1 = new Director(1,"Berlanga","nose");
		Director d2 = new Director(2,"Brian De Palma","nose");
		Director d3 = new Director(3,"Jose Luis Garci","nose");
		Director d4 = new Director(4,"Peter Jackson","nose");
		
		List<Movie> md1 = new ArrayList<>();
		md1.add(m1);md1.add(m2);md1.add(m3);
		d1.setMovies(md1);
		
		List<Movie> md2 = new ArrayList<>();
		md2.add(m5);md2.add(m4);
		d2.setMovies(md2);
		
		List<Movie> md3 = new ArrayList<>();
		md3.add(m6);md3.add(m7);md3.add(m8);md3.add(m9);
		d3.setMovies(md3);
		
		List<Movie> md4 = new ArrayList<>();
		md4.add(m10);md4.add(m11);md4.add(m12);
		d4.setMovies(md4);
		
		directors.add(d1);directors.add(d2);directors.add(d3);directors.add(d4);
						
		//////////////////////////////////			
		
		System.out.println("--- exercise3 ---");
		exercise3();
		System.out.println("--- exercise4 ---");
		exercise4();
		System.out.println("--- exercise8 ---");
		exercise8();
		System.out.println("--- exercise9 ---");
		exercise9();
	}

	/*private static City findCityById(int idCity) {
		return cities.stream().filter(p -> p.getId() == idCity).findFirst().orElse(null);
	}*/

	

	/*
	 * Encuentre la cantidad de películas de cada director. intente resolver este
	 * problema suponiendo que la clase Director no tiene películas miembro.
	 */
	public static void exercise3() {		
        Map<String, Long> directorMovieCounts =
                movies.stream()
                        .map(Movie::getDirectors)
                        .flatMap(List::stream)
                        .collect(Collectors.groupingBy(Director::getName, Collectors.counting()));
        directorMovieCounts.entrySet().forEach(System.out::println);
	}

	/*
	 * Encuentra el número de géneros de las películas de cada director
	 */
	public static void exercise4() {		
		Stream<DirectorGenre> d = 
				directors.stream()
				.flatMap(director -> director.getMovies()
						.stream()
						.map(Movie::getGenres)
						.flatMap(Collection::stream)
						.map(genre -> new DirectorGenre(director, genre))
						.collect(Collectors.toList()).stream());
		d.forEach(p -> System.out.println(" "+p.getKey().getName()+": "+p.getValue().getName()));
		
	}
	
	
	
	/*
	 * Encuentre la lista de películas que tienen los géneros "Drama" y "Comedia" solamente	 
	 */
	public static void exercise8() {
		Predicate<Movie> drama = p -> p.getGenres().stream().anyMatch(m -> m.getName().equals("Drama"));		
		Predicate<Movie> comedia = p -> p.getGenres().stream().anyMatch(m -> m.getName().equals("Comedia"));		
		Predicate<Movie> onlyOne = p -> p.getGenres().size()==2;
		
		List<Movie> df = movies.stream()
				.filter(onlyOne.and(drama).and(comedia))
				.collect(Collectors.toList());
		
		df.forEach(p -> System.out.println(
				"Pelicula: "+p.getTitle()+
				" generos: "+p.getGenres().stream()
				.map(Genre::getName)
				.collect(Collectors.joining(","))));
	}

	/*
	 * Agrupa las películas por año y enuméralas
	 */
	public static void exercise9() {		
		Map<Integer, String> d = movies.stream()
				.collect(Collectors.groupingBy(Movie::getYear, Collectors.mapping(Movie::getTitle, Collectors.joining(","))));
		
		d.entrySet()
			.stream()
			.sorted(Comparator.comparing(Entry::getKey))
			.forEach(p -> System.out.println("Ano: "+p.getKey()+ " Peliculas: "+p.getValue()));
	}	
}
