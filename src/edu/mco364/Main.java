package edu.mco364;

public class Main {

    public static void main(String[] args) {
        EightQueens queens = new EightQueens(8);
        queens.solve();
        System.out.println(queens);
    }
}
