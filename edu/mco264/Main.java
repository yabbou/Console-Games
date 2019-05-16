package edu.mco264;

public class Main {

    public static void main(String[] args) {
        Othello game = new Othello();
        do {
            game.togglePlayer();
            game.displayBoard();
            game.promptAndMakeMove();
        } while (!game.isGameOver());
        System.out.println(game.getWinner());
    }
}