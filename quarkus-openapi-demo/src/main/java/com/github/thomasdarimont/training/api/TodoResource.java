package com.github.thomasdarimont.training.api;

import com.github.thomasdarimont.training.TodoService;
import com.github.thomasdarimont.training.model.Todo;
import com.github.thomasdarimont.training.model.TodoNew;
import com.github.thomasdarimont.training.model.TodoUpdate;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Open API Definition
 * http://localhost:8080/q/openapi
 * <p>
 * Open API UI
 * http://localhost:8080/q/swagger-ui
 */
@Tag(name = "public")
@Path(TodoResource.ENDPOINT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    static final String ENDPOINT = "/api/todos";
    static final String SLASH_ID = "/{id}";
    static final String ENDPOINT_ID = ENDPOINT + SLASH_ID;

    private final TodoService todos;

    @Inject
    public TodoResource(TodoService todos) {
        this.todos = todos;
    }

    @GET
    @Operation(summary = "Returns all ToDo's", description = "Description find all")
    @APIResponse(
            responseCode = "200", description = "Todo list",
            content = @Content(schema = @Schema(implementation = Todo.class, type = SchemaType.ARRAY)))
    public Response findAll() {
        return Response.ok(todos.findAll()).build();
    }

    @GET
    @Path(TodoResource.SLASH_ID)
    @Operation(summary = "Returns the ToDo with the given id", description = "Description find by id")
    @APIResponse(
            responseCode = "200", description = "Todo found",
            content = @Content(schema = @Schema(implementation = Todo.class)))
    @APIResponse(responseCode = "404", description = "Todo not found")
    public Response getById(@PathParam("id") Long id) {
        return todos.findById(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    @Operation(summary = "Creates a new ToDo", description = "Description create new ToDo")
    @APIResponse(responseCode = "201", description = "Todo created",
            content = @Content(schema = @Schema(implementation = TodoNew.class)))
    @APIResponse(responseCode = "400", description = "Invalid request")
    public Response create(@Valid @RequestBody TodoNew todoNew, @Context UriInfo uriInfo) {
        long id = todos.create(todoNew.getText());
        URI location = uriInfo.getBaseUriBuilder().path(ENDPOINT_ID).resolveTemplate("id", id).build();
        return Response.created(location).build();
    }

    @DELETE
    @Path(TodoResource.SLASH_ID)
    @Operation(summary = "Deletes the ToDo with the given id", description = "Description deletes a ToDo")
    @APIResponse(responseCode = "204", description = "Todo deleted")
    @APIResponse(responseCode = "404", description = "Todo not found")
    public Response delete(@PathParam("id") Long id) {
        return (todos.delete(id)
                ? Response.noContent()
                : Response.status(Response.Status.NOT_FOUND))
                .build();
    }


    @PUT
    @Path(TodoResource.SLASH_ID)
    @Operation(summary = "Updates the ToDo with the given id", description = "Description updates a ToDo")
    @APIResponse(responseCode = "202", description = "Todo updated")
    @APIResponse(responseCode = "400", description = "Invalid request")
    @APIResponse(responseCode = "404", description = "Todo not found")
    public Response update(@PathParam("id") Long id, @Valid @RequestBody TodoUpdate update) {
        return todos.update(id, update)
                .map(Response::accepted)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}