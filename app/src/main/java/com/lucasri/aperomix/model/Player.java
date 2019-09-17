package com.lucasri.aperomix.model;

public class Player {

    private String playerName;
    private int picNbDrink;
    private int coeurNbDrink;
    private int trefleNbDrink;
    private int caroNbDrink;
    private int giveDrinkNb;
    private int takeDrinkNb;

    public String getPlayerName(){
        return playerName;
    }

    public int getPicNbDrink() {
        return picNbDrink;
    }
    public int getCoeurNbDrink() {
        return coeurNbDrink;
    }
    public int getTrefleNbDrink() {
        return trefleNbDrink;
    }
    public int getCaroNbDrink() {
        return caroNbDrink;
    }

    public int getGiveDrinkNb() {
        return giveDrinkNb;
    }

    public void setGiveDrinkNb(int giveDrinkNb) {
        this.giveDrinkNb = giveDrinkNb;
    }

    public int getTakeDrinkNb() {
        return takeDrinkNb;
    }

    public void setTakeDrinkNb(int takeDrinkNb) {
        this.takeDrinkNb = takeDrinkNb;
    }

    public void setPlayerName(String name){
        this.playerName = name;
    }

    public void setPicNbDrink(int nb) {
        this.picNbDrink = nb;
    }

    public void setCoeurNbDrink(int nb) {
        this.coeurNbDrink = nb;
    }
    public void setTrefleNbDrink(int nb) {
        this.trefleNbDrink = nb;
    }
    public void setCaroNbDrink(int nb) {
        this.caroNbDrink = nb;
    }
}