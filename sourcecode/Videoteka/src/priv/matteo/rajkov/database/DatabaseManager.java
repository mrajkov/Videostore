package priv.matteo.rajkov.database;

import priv.matteo.rajkov.models.Actor;
import priv.matteo.rajkov.models.Customer;
import priv.matteo.rajkov.models.Director;
import priv.matteo.rajkov.models.Movie;

import java.util.List;

public interface DatabaseManager {
    void connect();
    void releaseResources();

    List<Movie> getAllMovies();
    List<Movie> getAvailableMovies();
    List<Movie> getUnavailableMovies();

    List<Director> getAllDirectors();
    List<Actor> getAllActors();
    List<Customer> getAllCustomers();

    boolean rentMovie(int customerId, int movieId);
    Movie getMovieDetails(int id);

}
