package app.services;

import java.util.UUID;

public class IDGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
