package edu.mco364;

import java.awt.*;
import java.util.ArrayList;

public class EightQueens {
    private boolean[][] board;
    private int SIZE, row = 0;
    private boolean isQueen = true;
    private ArrayList<Point> queens;

    EightQueens(int size) {
        SIZE = size;
        board = new boolean[SIZE][SIZE];
        queens = new ArrayList<>(SIZE);
    }

    void solve() {
        while (queens.size() < SIZE) {
            placeNextQueen(0);
            row++;
        }
    }

    private void placeNextQueen(int col) {
        for (; col < SIZE; col++) {
            if (isOutOfRangeOfQueens(row, col)) {
                board[row][col] = isQueen;
                queens.add(new Point(col, row));
                return;
            }
        }
        col--;
        if (col == SIZE - 1 && board[row][col] != isQueen) {
            row--;
            backtrackLastQueen();
        }
    }

    private void backtrackLastQueen() {
        Point lastQueen = queens.get(queens.size() - 1);
        board[lastQueen.y][lastQueen.x] = !isQueen;
        queens.remove(lastQueen);

        if (lastQueen.x + 1 < SIZE) {
            placeNextQueen(lastQueen.x + 1);
        } else {
            row--;
            backtrackLastQueen();
        }
    }

    private boolean isOutOfRangeOfQueens(int row, int col) {
        for (Point queen : queens) {
            if (col == queen.x
                    || col - queen.x == row - queen.y
                    || Math.abs(col - queen.x) == Math.abs(row - queen.y)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder displayBoard = new StringBuilder();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                String cellDivider = "|";
                displayBoard.append((board[row][col] == isQueen)? " Q " : "   " ).append(cellDivider);
            }

            String rowDivider = new String(new char[SIZE]).replace("\0", "---+");
            displayBoard.append("\n").append(rowDivider).append("\n");
        }
        return displayBoard.toString();
    }
}
