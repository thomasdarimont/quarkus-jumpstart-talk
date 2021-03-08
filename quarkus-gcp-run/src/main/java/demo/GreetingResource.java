package demo;

import lombok.Data;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;

@Path("/")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting greet() {
        return new Greeting("Hello ", Instant.now());
    }

    @Data
    static class Greeting {

        private final String message;

        private final Instant timestamp;

    }
}