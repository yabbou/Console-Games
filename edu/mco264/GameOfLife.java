package edu.mco264;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameOfLife {

    enum Position {LEFT, RIGHT, UP, DOWN, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT}

    private int BOARD_SIZE;
    private final int BS_COLS = 3;
    private final boolean live = true;

    private boolean[][] board;
    private ArrayList<Position> liveNeighbours = new ArrayList<>();

    GameOfLife() {
        BOARD_SIZE = 7;
        board = new boolean[BOARD_SIZE][BOARD_SIZE];
    }

    GameOfLife(String pattern) {
        if (pattern.equals("blinker")) {
            BOARD_SIZE = 3;
            board = new boolean[BOARD_SIZE][BOARD_SIZE];

            board[1][0] = live;
            board[1][1] = live;
            board[1][2] = live;
        }

        if (pattern.equals("toad")) {
            BOARD_SIZE = 6;
            board = new boolean[BOARD_SIZE][BOARD_SIZE];

            board[2][2] = live;
            board[2][3] = live;
            board[2][4] = live;
            board[3][1] = live;
            board[3][2] = live;
            board[3][3] = live;
        }

        if (pattern.equals("beacon")) {
            BOARD_SIZE = 6;
            board = new boolean[BOARD_SIZE][BOARD_SIZE];

            board[1][1] = live;
            board[1][2] = live;
            board[2][1] = live;
            board[3][4] = live;
            board[4][4] = live;
            board[4][3] = live;
        }

        if (pattern.equals("glider")) {
            BOARD_SIZE = 7;
            board = new boolean[BOARD_SIZE][BOARD_SIZE];

            board[1][1] = live;
            board[2][2] = live;
            board[2][3] = live;
            board[3][1] = live;
            board[3][2] = live;
        }
    }

    public boolean[][] getBoard() {
        return board;
    }

    public String toString() {

        StringBuilder displayBoard = new StringBuilder();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {

                String cellDivider = "|";
                if (board[row][col] == live) {
                    displayBoard.append(" X ").append(cellDivider);
                } else
                    displayBoard.append(" _ ").append(cellDivider);
            }

            String rowDivider = new String(new char[BOARD_SIZE]).replace("\0", "---+");
            displayBoard.append("\n").append(rowDivider).append("\n");
        }
        return displayBoard.toString();
    }

    public void updateToNextGeneration() {
        boolean[][] updatedBoard = new boolean[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {

                if (isAliveNextGeneration(row, col)) {
                    updatedBoard[row][col] = live;
                }
            }
        }
        board = updatedBoard;
    }

    public boolean isAliveNextGeneration(int row, int col) {
        if (board[row][col] == live &&
                (liveNeighbourCount(row, col) == 2 || liveNeighbourCount(row, col) == 3))
            return live;
        else if (liveNeighbourCount(row, col) == 3)
            return live;
        return !live;
    }

    public int liveNeighbourCount(int row, int col) {
        liveNeighbours.clear();
        for (Position p : Position.values()) {
            Point neighbour = convertToPoint(row, col, p);

            if (neighbour.y >= 0 && neighbour.y < BOARD_SIZE && neighbour.x >= 0 && neighbour.x < BOARD_SIZE) {
                if (board[neighbour.y][neighbour.x] == live) {
                    liveNeighbours.add(p);
                }
            }
        }
        return liveNeighbours.size();
    }

    private Point convertToPoint(int row, int col, Position position) {
        switch (position) {
            case LEFT:
                return new Point(col - 1, row);
            case RIGHT:
                return new Point(col + 1, row);
            case UP:
                return new Point(col, row - 1);
            case DOWN:
                return new Point(col, row + 1);
            case UPLEFT:
                return new Point(col - 1, row - 1);
            case UPRIGHT:
                return new Point(col + 1, row - 1);
            case DOWNLEFT:
                return new Point(col - 1, row + 1);
            case DOWNRIGHT:
                return new Point(col + 1, row + 1);
            default:
                throw new RuntimeException(position.toString());
        }
    }

    private int periodCount() {
        boolean[][] initialBoard = board;
        boolean[][] updatedBoard = new boolean[BOARD_SIZE][BOARD_SIZE];
        int period = 0;

        while (!Arrays.deepEquals(initialBoard, updatedBoard)) {
            updateToNextGeneration();
            updatedBoard = board;
            period++;
        }
        return period;
    }

    public void displayPeriodCount() {
        System.out.println("Period: " + periodCount());
    }
}