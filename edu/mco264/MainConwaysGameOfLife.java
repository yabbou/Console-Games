package edu.mco264;

public class Main {
    public static void main(String[] args) {
        GameOfLife pattern = new GameOfLife("glider");

        pattern.displayPeriodCount();
        do {
            System.out.println(pattern);
            pattern.updateToNextGeneration();
            repeat();
        } while (true);
    }

    private static void repeat() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}