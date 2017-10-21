package com.pajonc.twitter.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class User {

    private String name;
    private List<String> messages = new LinkedList<>();

    public User(String name) {
        this.name = name;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
