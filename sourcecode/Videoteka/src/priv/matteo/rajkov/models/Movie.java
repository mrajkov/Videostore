package priv.matteo.rajkov.models;

import java.util.List;

public class Movie {
    private int id;
    private String name;
    private String duration;
    private Director director;
    private Genre genre;
    private List<Actor> actors;

    public Movie(int id) {
        this(id, null, null, null, null);
    }

    public Movie(int id, String name, String duration) {
        this(id, name, duration, null, null);
    }

    public Movie(int id, String name, String duration, Director director, Genre genre) {
        this(id, name, duration, director, genre, null);
    }

    public Movie(int id, String name, String duration, Director director, Genre genre, List<Actor> actors) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.director = director;
        this.genre = genre;
        this.actors = actors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Database ensures uniqueness of its id's, so having equals and hashcode checking for only id is acceptable.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
