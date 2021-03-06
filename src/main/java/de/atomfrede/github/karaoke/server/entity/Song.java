package de.atomfrede.github.karaoke.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoId;

public class Song {

    @MongoId
    @JsonProperty
    private String _id;

    @JsonProperty
    private String title;
    @JsonProperty
    private String interpreter;

    public Song() {

    }

    public Song(String id) {
        this._id = id;
    }

    public String id() {
        return _id;
    }

    public String title() {
        return title;
    }

    public Song setTitle(String title) {
        this.title = title;
        return this;
    }

    public String interpreter() {
        return interpreter;
    }

    public Song setInterpreter(String interpreter) {
        this.interpreter = interpreter;
        return this;
    }
}
