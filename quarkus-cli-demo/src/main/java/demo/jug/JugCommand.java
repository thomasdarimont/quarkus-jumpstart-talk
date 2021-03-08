package demo.jug;

import picocli.CommandLine;

/**
 * mvn compile quarkus:dev -Dquarkus.args='joke'
 */
@CommandLine.Command(name = "jug", description = "Prints information about a Java User Group")
public class JugCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello from JUG Command");
    }
}
