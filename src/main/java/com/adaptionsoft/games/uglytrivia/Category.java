package com.adaptionsoft.games.uglytrivia;

public enum  Category {
    POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock");
    public final String label;

    Category(String label) {
        this.label = label;
    }

    public static Category fromPosition(int position) {

        if (position == 0) return POP;
        if (position == 4) return POP;
        if (position == 8) return POP;
        if (position == 1) return SCIENCE;
        if (position == 5) return SCIENCE;
        if (position == 9) return SCIENCE;
        if (position == 2) return SPORTS;
        if (position == 6) return SPORTS;
        if (position == 10) return SPORTS;

        return ROCK;
    }

    @Override
    public String toString() {
        return label;
    }
}
