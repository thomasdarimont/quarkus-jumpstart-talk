package demo;

import demo.jokes.JokeCommand;
import demo.jug.JugCommand;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@TopCommand
@CommandLine.Command(
        mixinStandardHelpOptions = true,
        subcommands = {
                JokeCommand.class,
                JugCommand.class
        }
)
public class EntryCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("Quarkus CLI Demo");
        CommandLine.usage(this, System.out);
        return 0;
    }
}