package com.adaptionsoft.games.uglytrivia;

public class Player {
    private final String name;
    private int coins = 0;

    public Player(String name) {
        this.name = name;
    }

    public void winsACoin() {
        coins++;
    }

    public int coins() {
        return coins;
    }

    public String name() {
        return name;
    }
}
