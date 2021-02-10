package priv.matteo.rajkov.database;

import priv.matteo.rajkov.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseManagerImpl implements DatabaseManager {

    private Connection connection = null;

    @Override
    public void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/videoteka", "root", "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseResources() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT movie.id, movie.name, movie.duration FROM movie";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String duration = resultSet.getString("duration");
                resultList.add(new Movie(id, name, duration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Movie> getAvailableMovies() {
        List<Movie> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT movie.id, movie.name, movie.duration " +
                    "FROM movie " +
                    "LEFT JOIN customer_to_movie " +
                    "ON movie.id=customer_to_movie.movie_id " +
                    "WHERE customer_to_movie.movie_id IS NULL";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String duration = resultSet.getString("duration");
                resultList.add(new Movie(id, name, duration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Movie> getUnavailableMovies() {
        List<Movie> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT movie.id, movie.name, movie.duration " +
                    "FROM movie, customer_to_movie " +
                    "WHERE movie.id=customer_to_movie.movie_id";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String duration = resultSet.getString("duration");
                resultList.add(new Movie(id, name, duration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Director> getAllDirectors() {
        List<Director> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM director";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                resultList.add(new Director(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Actor> getAllActors() {
        List<Actor> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM actor";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                resultList.add(new Actor(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM customer";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phone_num");
                resultList.add(new Customer(id, name, address, phoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public boolean rentMovie(int customerId, int movieId) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO customer_to_movie (customer_id, movie_id) VALUES ('" + customerId + "','" + movieId + "')";
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Movie getMovieDetails(int movieId) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT DISTINCT m.id, m.name, m.duration, d.name as director_name, g.name as genre_name, (SELECT GROUP_CONCAT(a2.name SEPARATOR ',') " +
                    "FROM Movie m2, Actor_to_movie am2, Actor a2 " +
                    "WHERE m2.id = am2.movie_id " +
                    "AND am2.actor_id = a2.id " +
                    "AND m2.id = m.id " +
                    "GROUP BY m2.id) actors " +
                    "FROM Movie m, Director d, Genre g, Actor_to_movie am, Actor a " +
                    "WHERE m.director_id = d.id " +
                    "AND m.genre_id = g.id " +
                    "AND m.id = " + movieId + " " +
                    "AND am.actor_id = a.id";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String duration = rs.getString("duration");
                String directorName = rs.getString("director_name");
                String genreName = rs.getString("genre_name");
                String[] actorNames = rs.getString("actors").split(",");

                List<Actor> actors = new ArrayList<>(Collections.emptyList());
                for (String s : actorNames) {
                    actors.add(new Actor(s));
                }
                return new Movie(id, name, duration, new Director(directorName), new Genre(genreName), actors);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
