package demo.jokes;

import java.beans.ConstructorProperties;

public class Joke {

    private String id;

    private String value;

    @ConstructorProperties({"id", "value"})
    public Joke(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
