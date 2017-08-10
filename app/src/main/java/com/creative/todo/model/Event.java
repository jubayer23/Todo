package com.creative.todo.model;

/**
 * Created by jubayer on 8/2/2017.
 */

public class Event {

    int id;
    String note;
    int status;
    int priority;
    String created_at;
    String event_date;

    // constructors
    public Event() {
    }

    public Event(String note, int status, int priority, String created_at, String event_date) {
        this.note = note;
        this.status = status;
        this.priority = priority;
        this.created_at = created_at;
        this.event_date = event_date;
    }

    public Event(String note, int status) {
        this.note = note;
        this.status = status;
    }

    public Event(int id, String note, int status) {
        this.id = id;
        this.note = note;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    // getters
    public int getId() {
        return this.id;
    }

    public String getNote() {
        return this.note;
    }

    public int getStatus() {
        return this.status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }
}