package priv.matteo.rajkov.ui;

import priv.matteo.rajkov.database.DatabaseManager;
import priv.matteo.rajkov.database.DatabaseManagerImpl;
import priv.matteo.rajkov.models.Actor;
import priv.matteo.rajkov.models.Customer;
import priv.matteo.rajkov.models.Director;
import priv.matteo.rajkov.models.Movie;

import java.util.List;

public final class InputHandler {

    private static final DatabaseManager databaseManager = new DatabaseManagerImpl();

    public static void handleUserMainInput(int commandNumber) {
        if (commandNumber != 9) databaseManager.connect();
        else databaseManager.releaseResources();

        switch (commandNumber) {
            case 1 -> {
                List<Movie> moviesList = databaseManager.getAvailableMovies();
                Printer.printMoviesList("Currently available movies are: ", moviesList);
            }
            case 2 -> {
                List<Director> directorsList = databaseManager.getAllDirectors();
                Printer.printDirectorsList("Following is the list of all directors which have directed movies in our database", directorsList);
            }
            case 3 -> {
                List<Actor> actorsList = databaseManager.getAllActors();
                Printer.printActorsList("Following is the list of all actors which have starred in movies in our database", actorsList);
            }
            case 4 -> handleMovieRenting();
            case 5 -> {
                List<Movie> moviesList = databaseManager.getUnavailableMovies();
                Printer.printMoviesList("Currently unavailable movies are:", moviesList);
            }
            case 6 -> {
                List<Movie> allMovies = databaseManager.getAllMovies();
                Printer.printMoviesList("Movies stored in the database are: ", allMovies);
                int movieId = Printer.askForMovieIdInputUntilValid(allMovies);
                Movie movie = databaseManager.getMovieDetails(movieId);
                Printer.printMovieDetails("Printing out movie details", movie);
            }
            case 9 -> {
                Printer.printExitingText();
                System.exit(0);
            }
        }
    }

    private static void handleMovieRenting() {
        List<Customer> customers = databaseManager.getAllCustomers();
        Printer.printCustomers("Which customer are you", customers);
        int customerId;
        do {
            try {
                String userInput = Printer.askForUserInput();
                customerId = Integer.parseInt(userInput);
                if (customerId < 1 || customerId > 3) System.out.println("That id doesn't belong to a user, try again");
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid id, please try again");
                customerId = -1;
            }
        } while (customerId < 1 || customerId > 3);

        List<Movie> moviesList = databaseManager.getAvailableMovies();
        Printer.printMoviesList("Currently available movies are: ", moviesList);
        System.out.println("Please select movie id you wish to rent out");
        int movieId;
        boolean correctMovieId = false;
        do {
            try {
                String userInput = Printer.askForUserInput();
                movieId = Integer.parseInt(userInput);
                for (Movie movie : moviesList) {
                    if (movie.getId() == movieId) {
                        correctMovieId = true;
                        break;
                    }
                }
                if (!correctMovieId) System.out.println("That id doesn't belong to a movie, try again");
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid id, please try again");
                correctMovieId = false;
                movieId = -1;
            }
        } while (!correctMovieId);

        if (databaseManager.rentMovie(customerId, movieId)) {
            System.out.println("Movie has been successfully rented");
        } else {
            System.out.println("An error occurred whilst renting a movie");
        }
    }
}
