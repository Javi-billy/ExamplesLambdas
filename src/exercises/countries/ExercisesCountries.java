package exercises.countries;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExercisesCountries {

	// https://binkurt.blogspot.com/2017/10/exercises-to-study-java-stream-api.html

	public static List<Country> countries = new ArrayList<>();
	public static List<City> cities = new ArrayList<>();
	public static Map<String, Country> listcountries = new HashMap<>();

	public static void main(String[] args) {
		Country es = new Country("ES", "España", "Europa", 1421, 0, 0, 1);
		Country it = new Country("IT", "Italia", "Europa", 2478, 0, 0, 4);
		Country us = new Country("US", "USA", "America", 1235, 0, 0, 8);
		Country an = new Country("AN", "Andorra", "Europa", 0, 0, 0, 0);

		City c1 = new City(1, "Madrid", "ES", 123);
		City c2 = new City(2, "Santander", "ES", 1243);
		City c3 = new City(3, "Caceres", "ES", 55);
		City c4 = new City(4, "Roma", "IT", 3455);
		City c5 = new City(5, "Florencia", "IT", 23);
		City c6 = new City(6, "Nueva York", "US", 1);
		City c7 = new City(7, "Santa Cruz", "US", 1123);
		City c8 = new City(8, "Wassinton", "US", 99);
		City c9 = new City(9, "Chicago", "US", 12);

		List<City> le = new ArrayList<>();
		le.add(c1);
		le.add(c2);
		le.add(c3);
		es.setCities(le);

		countries.add(es);

		List<City> li = new ArrayList<>();
		li.add(c4);
		li.add(c5);
		it.setCities(li);

		countries.add(it);

		List<City> lu = new ArrayList<>();
		lu.add(c6);
		lu.add(c7);
		lu.add(c8);
		lu.add(c9);
		us.setCities(lu);

		countries.add(us);

		countries.add(an);

		cities.add(c1);
		cities.add(c2);
		cities.add(c3);
		cities.add(c4);
		cities.add(c5);
		cities.add(c6);
		cities.add(c7);
		cities.add(c8);
		cities.add(c9);

		listcountries = countries.stream().collect(Collectors.toMap(Country::getCode, s -> s));

		System.out.println("--- exercise1 ---");
		exercise1();
		System.out.println("--- exercise2 ---");
		exercise2();
		System.out.println("--- exercise5 ---");
		exercise5();
		System.out.println("--- exercise6 ---");
		exercise6();
		System.out.println("--- exercise7 ---");
		exercise7();
		System.out.println("--- exercise0 ---");
		exercise0();
	}

	private static City findCityById(int idCity) {
		return cities.stream().filter(p -> p.getId() == idCity).findFirst().orElse(null);
	}

	/*
	 * Encuentra la ciudad más poblada de cada país
	 */
	public static void exercise1() {

		List<City> highPopulatedCitiesOfCountries = countries.stream()
				.map(c -> c.getCities().stream().max(Comparator.comparing(City::getPopulation)))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

		highPopulatedCitiesOfCountries.forEach(System.out::println);

	}

	// Encuentra la ciudad más poblada de cada continente:
	public static void exercise2() {

		final BiConsumer<String, Optional<City>> printEntry = (k, v) -> {
			City city = v.get();
			System.out.println(k + ": City [ name= " + city.getName() + ", population= " + city.getPopulation() + " ]");
		};

		Map<String, Optional<City>> highPopulatedCitiesByContinent = countries.stream()
				.flatMap(country -> country.getCities().stream())
				.collect(Collectors.groupingBy(city -> listcountries.get(city.getCountryCode()).getContinent(),
						Collectors.maxBy(Comparator.comparing(City::getPopulation))));

		highPopulatedCitiesByContinent.forEach(printEntry);

	}

	/*
	 * Encuentra la ciudad capital más poblada
	 */
	public static void exercise5() {		

		Optional<City> l = countries.stream().map(Country::getCapital).map(ExercisesCountries::findCityById)
				.filter(Objects::nonNull).collect(Collectors.maxBy(Comparator.comparing(City::getPopulation)));
		System.out.println(l);
	}

	/*
	 * Encuentra la ciudad capital más poblada de cada continente
	 */
	public static void exercise6() {		

		Map<String, Optional<ContinentPopulatedCity>> continentsCapitals = countries.stream()
				.filter(country -> country.getCapital() > 0)
				.map(country -> new ContinentPopulatedCity(country.getContinent(), findCityById(country.getCapital())))
				.collect(groupingBy(ContinentPopulatedCity::getKey,
						Collectors.maxBy(Comparator.comparing(cpc -> cpc.getValue().getPopulation()))));

		BiConsumer<String, Optional<ContinentPopulatedCity>> print = (k, v) -> {
			Consumer<ContinentPopulatedCity> continentPopulatedCityConsumer = cpc -> System.out
					.println(cpc.getKey() + ": " + v.get().getValue());
			v.ifPresent(continentPopulatedCityConsumer);
		};

		continentsCapitals.forEach(print);
	}

	/*
	 * Ordene los países por número de sus ciudades en orden descendente
	 */
	public static void exercise7() {		

		//Comparator<Country> compara = (c1, c2) -> Integer.compare(c1.getCities().size(), c2.getCities().size());

		Comparator<Country> compara2 = Comparator.comparing(c3 -> c3.getCities().size());

		Predicate<Country> isEmpty = c -> c.getCities().isEmpty();

		List<Country> order = countries.stream().filter(isEmpty.negate()).sorted(compara2.reversed())
				.collect(Collectors.toList());

		order.forEach(System.out::println);

	}

	/*
	 * Ordene los países por sus densidades de población en orden descendente
	 * ignorando los países con población cero
	 */
	public static void exercise0() {
		
		Predicate<Country> sinPoblacion = p -> p.getPopulation() == 0;

		Comparator<Country> population = Comparator.comparing(Country::getPopulation);

		countries.stream().filter(sinPoblacion.negate()).sorted(population.reversed()).forEach(System.out::println);
	}

}
