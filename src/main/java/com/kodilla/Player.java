package com.kodilla;

public class Player {
    private String name;
    private double playerMoney;

    public Player(String name, double playerMoney) {
        this.name = name;
        this.playerMoney = playerMoney;
    }

    public String getName() {
        return name;
    }

    public double getPlayerMoney() {
        return playerMoney;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayersMoney(double playerMoney) {
        this.playerMoney = playerMoney;
    }
}
