package exercises.countries;

import java.util.Map;

public class ContinentPopulatedCity implements Map.Entry<String, City> {
	
	private String continent;
	private City city;

	public ContinentPopulatedCity(String continent, City city) {
		this.continent = continent;
		this.city = city;
	}

	@Override
	public String getKey() {
		return continent;
	}

	@Override
	public City getValue() {
		return city;
	}

	@Override
	public City setValue(City city) {
		this.city = city;
		return city;
	}

}
