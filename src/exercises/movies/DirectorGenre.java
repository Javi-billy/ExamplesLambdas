package exercises.movies;

import java.util.Map;

public class DirectorGenre implements Map.Entry<Director, Genre> {
	private Director director;
	private Genre genre;

	public DirectorGenre(Director director, Genre genre) {
		this.director = director;
		this.genre = genre;
	}

	@Override
	public Director getKey() {
		return director;
	}

	@Override
	public Genre getValue() {
		return genre;
	}

	@Override
	public Genre setValue(Genre genre) {
		this.genre = genre;
		return genre;
	}

}
