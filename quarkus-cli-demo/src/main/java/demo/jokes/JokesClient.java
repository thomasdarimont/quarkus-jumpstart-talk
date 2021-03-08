package demo.jokes;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;

@Dependent
public class JokesClient {

    @Inject
    @ConfigProperty(name = "JOKE_API_ENDPOINT", defaultValue = "https://api.chucknorris.io/jokes/random")
    String jokesApiEndpoint;

    private Client client;

    private WebTarget jokesApi;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newBuilder().build();
        jokesApi = client.target(jokesApiEndpoint);
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    @Fallback(JokeFallbackHandler.class)
    public String getRandomJoke() {
        return getRandomJokeInternal();
    }

    public String getRandomJokeInternal() {

        Response response = jokesApi.request()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (Response.Status.OK.getStatusCode() != response.getStatus()) {
            return "Error during Joke retrieval. Status=" + response.getStatus();
        }

        Joke joke = response.readEntity(Joke.class);
        return joke.getValue();
    }

    static class JokeFallbackHandler implements FallbackHandler<String> {

        @Override
        public String handle(ExecutionContext executionContext) {
            return "Fallback JOKE based on time " + Instant.now();
        }
    }

}
