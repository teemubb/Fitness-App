package com.FitnessApp;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Item {
    private String name;
    private Timestamp timestamp;

    public Item() {
        name = "default name";
        timestamp = createTimestamp();
    }
    public Item(String name) {
        this.name = name;
        timestamp = createTimestamp();
    }

    public Timestamp createTimestamp(){
        Instant instant = Instant.now();
        return Timestamp.from(instant);
    }
    public String getName() {
        return name;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTimestamp() {
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }
}
