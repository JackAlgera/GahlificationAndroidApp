package com.jack.algera.gahlificapption.reminders.models;

import java.time.Instant;
import java.util.UUID;

public class Reminder {

    private UUID reminderId;
    private String name;
    private String description;
    private Instant dueDate;
    private Instant startNotifyingDate;
    private Long pingFrequencyInterval; // In seconds
    private Instant createdOn;
    private Instant lastModified;

    public Reminder() {
    }

    public Reminder(UUID reminderId, String name, String description, Instant dueDate, Instant startNotifyingDate, Long pingFrequencyInterval, Instant createdOn, Instant lastModified) {
        this.reminderId = reminderId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.startNotifyingDate = startNotifyingDate;
        this.pingFrequencyInterval = pingFrequencyInterval;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
    }

    public UUID getReminderId() {
        return reminderId;
    }

    public void setReminderId(UUID reminderId) {
        this.reminderId = reminderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getStartNotifyingDate() {
        return startNotifyingDate;
    }

    public void setStartNotifyingDate(Instant startNotifyingDate) {
        this.startNotifyingDate = startNotifyingDate;
    }

    public Long getPingFrequencyInterval() {
        return pingFrequencyInterval;
    }

    public void setPingFrequencyInterval(Long pingFrequencyInterval) {
        this.pingFrequencyInterval = pingFrequencyInterval;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }
}
