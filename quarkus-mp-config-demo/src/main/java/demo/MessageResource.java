package demo;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.Instant;

@Path("/message")
public class MessageResource {

    @Inject
    @ConfigProperty(name = "demo.message", defaultValue = "Hello World")
    String message;

    @Inject
    Logger log;

    @GET
    public String getMessage() {

        log.debug("Access message...");

        return message + " " + Instant.now();
    }
}
