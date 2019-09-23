package com.lucasri.aperomix.model;

public class Player {

    private String playerName;

    //PmuGame
    private int picNbDrink;
    private int coeurNbDrink;
    private int trefleNbDrink;
    private int caroNbDrink;
    private int giveDrinkNb;
    private int takeDrinkNb;

    //BeLuckyGame
    private int color;
    private int beLuckyCase;
    private int beLuckyPreviousCase;
    private int beLuckyCasePosition;

    public int getBeLuckyPreviousCase() {
        return beLuckyPreviousCase;
    }

    public void setBeLuckyPreviousCase(int beLuckyPreviousCase) {
        this.beLuckyPreviousCase = beLuckyPreviousCase;
    }

    public int getBeLuckyCasePosition() {
        return beLuckyCasePosition;
    }

    public void setBeLuckyCasePosition(int beLuckyCasePosition) {
        this.beLuckyCasePosition = beLuckyCasePosition;
    }


    public int getBeLuckyCase() {
        return beLuckyCase;
    }

    public void setBeLuckyCase(int beLuckyCase) {
        this.beLuckyCase = beLuckyCase;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

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