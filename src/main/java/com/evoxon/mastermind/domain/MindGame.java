package com.evoxon.mastermind.domain;

import java.util.Arrays;

public class MindGame {

    final private String[] secretCombination;
    private int numberOfTries;
    boolean isFinished;


    public MindGame(String[] secretCombination, int numberOfTries, boolean isFinished) {
        this.secretCombination = secretCombination;
        this.numberOfTries = numberOfTries;
        this.isFinished = isFinished;
    }

    public String[] getSecretCombination() {
        return secretCombination;
    }

    public int getNumberOfTries() {
        return numberOfTries;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setNumberOfTries(int numberOfTries) {
        this.numberOfTries = numberOfTries;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return "MindGame{" +
                "secretCombination=" + Arrays.toString(secretCombination) +
                ", numberOfTries=" + numberOfTries +
                ", isFinished=" + isFinished +
                '}';
    }
}
