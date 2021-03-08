package com.github.thomasdarimont.training.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * http://localhost:8080/q/health/live
 */
@Liveness
@ApplicationScoped
public class DownstreamServiceHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Downstream Service Rechable");

        // call external service here
        try (InputStream dummy = new URL("http://localhost:7080").openStream()) {
            // ignore
            dummy.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return responseBuilder.down().withData("error", e.getMessage()).build();
        }

        return responseBuilder.up().build();
    }
}
