package com.kodilla;

public class Player {
    private String name;
    private double playersMoney;

    public Player(String name, double playersMoney) {
        this.name = name;
        this.playersMoney = playersMoney;
    }

    public String getName() {
        return name;
    }

    public double getPlayersMoney() {
        return playersMoney;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayersMoney(double playersMoney) {
        this.playersMoney = playersMoney;
    }
}
