package demo.jokes;

import picocli.CommandLine;

import javax.inject.Inject;

/**
 * mvn compile quarkus:dev -Dquarkus.args='joke'
 */
@CommandLine.Command(name = "joke", description = "Prints a random Joke")
public class JokeCommand implements Runnable {

    private final JokesClient jokesClient;

    @Inject
    public JokeCommand(JokesClient jokesClient) {
        this.jokesClient = jokesClient;
    }

    @Override
    public void run() {
        System.out.println(jokesClient.getRandomJoke());
    }
}
