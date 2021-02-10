package priv.matteo.rajkov.ui;

import priv.matteo.rajkov.models.Actor;
import priv.matteo.rajkov.models.Customer;
import priv.matteo.rajkov.models.Director;
import priv.matteo.rajkov.models.Movie;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public final class Printer {

    private static final String MOVIE_FORMAT_SHORT = "| %-2s | %-45s | %-10s |\n";
    private static final String MOVIE_FORMAT_LONG = "| %-2s | %-45s | %-10s | %-20s | %-10s | %-40s |\n";
    private static final String TWO_DATA_TYPE_FORMAT = "| %-2s | %-25s |\n";
    private static final String FOUR_DATA_TYPE_FORMAT = "| %-2s | %-15s | %-20s | %-15s |\n";

    private static final Scanner scanner = new Scanner(System.in);

    public static String askForUserInput(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    public static String askForUserInput() {
        return scanner.nextLine();
    }

    public static int askForIntegerInputUntilValid(List<Integer> validInputs) {
        int parsedCommand;
        do {

            String scannedLine = scanner.nextLine();
            try {
                parsedCommand = Integer.parseInt(scannedLine);
                for (int validInput : validInputs) if (parsedCommand == validInput) return parsedCommand;
                System.out.println("That command was unrecognized, try again");
            } catch (NumberFormatException e) {
                System.out.println("That is not an integer number, try again");
            }
        } while (true);
    }

    public static int askForMovieIdInputUntilValid(List<Movie> validMovies) {
        int movieId;
        do {
            String scannedLine = scanner.nextLine();
            try {
                movieId = Integer.parseInt(scannedLine);
                Movie wantedMovie = new Movie(movieId);
                for (Movie validMovie : validMovies) {
                    if (wantedMovie.equals(validMovie)) return movieId;
                }
                System.out.println("That command was unrecognized, try again");
            } catch (NumberFormatException e) {
                System.out.println("That is not an integer number, try again");
            }
        } while (true);
    }

    public static void printHelloText() {
        System.out.println("Welcome to the digital videostore!");
    }

    public static void printErrorText() {
        System.out.println("You have entered an invalid/unknown command, exiting the Digital Videostore.");
    }

    public static void printExitingText() {
        System.out.println("Exiting Digital Videostore, thank you for visiting our store.");
    }

    public static void printMainMenu() {
        System.out.println("\nSelect the number of the action you'd like to perform:\n");
        System.out.println("[1] Display the list of available movies");
        System.out.println("[2] Display the list of directors");
        System.out.println("[3] Display the list of actors");
        System.out.println("[4] Rent a movie");
        System.out.println("[5] Display the list of already rented movies");
        System.out.println("[6] Display movie details");
        System.out.println("[9] Exit program");
    }

    public static void printMoviesList(String preText, List<Movie> moviesList) {
        System.out.println("\n\n" + preText);
        System.out.println("-------------------------------------------------------------------");
        System.out.format(MOVIE_FORMAT_SHORT, "id", "Movie name", "Duration");
        System.out.println("-------------------------------------------------------------------");
        for (Movie movie : moviesList) {
            int id = movie.getId();
            String name = movie.getName();
            String duration = movie.getDuration();
            System.out.format(MOVIE_FORMAT_SHORT, id, name, duration);
        }
        System.out.println("-------------------------------------------------------------------");
    }

    public static void printDirectorsList(String preText, List<Director> directorList) {
        System.out.println("\n\n" + preText);
        System.out.println("----------------------------------");
        System.out.format(TWO_DATA_TYPE_FORMAT, "id", "Director name");
        System.out.println("----------------------------------");
        for (Director director : directorList) {
            int id = director.getId();
            String name = director.getName();
            System.out.format(TWO_DATA_TYPE_FORMAT, id, name);
        }
        System.out.println("----------------------------------");
    }

    public static void printActorsList(String preText, List<Actor> actorList) {
        System.out.println("\n\n" + preText);
        System.out.println("----------------------------------");
        System.out.format(TWO_DATA_TYPE_FORMAT, "id", "Actor name");
        System.out.println("----------------------------------");
        for (Actor actor : actorList) {
            int id = actor.getId();
            String name = actor.getName();
            System.out.format(TWO_DATA_TYPE_FORMAT, id, name);
        }
        System.out.println("----------------------------------");
    }

    public static void printCustomers(String preText, List<Customer> customers) {
        System.out.println("\n\n" + preText);
        System.out.println("-----------------------------------------------------------------");
        System.out.format(FOUR_DATA_TYPE_FORMAT, "id", "User name", "Address", "Phone number");
        System.out.println("-----------------------------------------------------------------");
        for (Customer customer : customers) {
            int id = customer.getId();
            String name = customer.getName();
            String address = customer.getAddress();
            String phoneNumber = customer.getPhoneNumber();
            System.out.format(FOUR_DATA_TYPE_FORMAT, id, name, address, phoneNumber);
        }
        System.out.println("-----------------------------------------------------------------");
    }

    public static void printMovieDetails(String preText, Movie movie) {
        System.out.println("\n\n" + preText);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.format(MOVIE_FORMAT_LONG, "id", "Movie name", "Duration", "Director name", "Genre", "Actors");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

        StringBuilder actorNames = new StringBuilder();
        for (Actor a : movie.getActors()) {
            actorNames.append(a.getName()).append(",");
        }
        String actorNamesOutput = actorNames.toString();
        actorNamesOutput = actorNamesOutput.substring(0, actorNamesOutput.length() - 1);
        System.out.format(MOVIE_FORMAT_LONG,
                movie.getId(),
                movie.getName(),
                movie.getDuration(),
                movie.getDirector().getName(),
                movie.getGenre().getName(),
                actorNamesOutput
        );
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
