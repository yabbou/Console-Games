package edu.mco264;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

class Othello {

    enum Position {LEFT, RIGHT, UP, DOWN, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT}

    private boolean isPlayerXMove, isDraw;
    private int moveCounter = 0;
    private String[][] grid;
    private String otherPlayer;
    private ArrayList<Position> neighbours = new ArrayList<>();

    Othello() {
        grid = new String[9][9];

        for (int col = 1; col < 9; col++) {
            char colVal = 'A' - 1;
            grid[0][col] = String.valueOf((char) (colVal + col));
        }

        for (int row = 1; row < 9; row++) {
            grid[row][0] = String.valueOf(row);
        }

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                grid[row][col] = " ";
            }
        }
        grid[0][0] = " "; 
        grid[4][4] = "X";
        grid[5][5] = "X";
        grid[4][5] = "O";
        grid[5][4] = "O";
    }

    public void togglePlayer() {
        isPlayerXMove = !isPlayerXMove;
        otherPlayer = isPlayerXMove ? "O" : "X";
    }

    public void displayBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(grid[row][col] + " | ");
            }
            System.out.println("\n--+---+---+---+---+---+---+---+---+");
        }
    }

    public void promptAndMakeMove() {
        Point move = prompt();
        setCell(move, isPlayerXMove ? "X" : "O");
        moveCounter++;
    }

    private Point prompt() {
        Scanner scan = new Scanner(System.in);
        int col, row;
        do {
            System.out.println("\nEnter your cell (row 1-8, column A-H), Mr. "
                    + (isPlayerXMove ? "X" : "O"));
            String userMove = scan.next();
            col = userMove.toUpperCase().charAt(0) - ('A') + 1;
            row = userMove.charAt(1) - ('1') + 1;
        } while (!isValidMove(col, row));
        return new Point(col, row);
    }

    private boolean isValidMove(int col, int row) {
        if (col >= 9 || row >= 9) {
            System.out.println("Move must be within columns A-H and rows 1-8. " +
                    "Please re-enter cell value.");
            return false;
        }
        if (!grid[row][col].equals(" ")) {
            System.out.println("Cell is already taken. " +
                    "Please try another cell.");
            return false;
        }
        if (!hasNeighbouringCells(new Point(col, row))) {
            System.out.println("Move must be adjacent to cell of other player. " +
                    "Please re-enter cell value.");
            return false;
        }
        return true;
    }

    private boolean hasNeighbouringCells(Point move) {
        neighbours.clear();
        for (Position p : Position.values()) {
            Point neighbour = convertToPoint(move, p);
            if (neighbour.y > 0 && neighbour.y < 9 && neighbour.x > 0 && neighbour.x < 9) {
                if (grid[neighbour.y][neighbour.x].equals(otherPlayer)) {
                    neighbours.add(p);
                }
            }
        }
        return !neighbours.isEmpty();
    }

    private Point convertToPoint(Point move, Position position) {
        switch (position) {
            case LEFT:
                return new Point(move.x - 1, move.y);
            case RIGHT:
                return new Point(move.x + 1, move.y);
            case UP:
                return new Point(move.x, move.y - 1);
            case DOWN:
                return new Point(move.x, move.y + 1);
            case UPLEFT: 
                return new Point(move.x - 1, move.y - 1);
            case UPRIGHT:
                return new Point(move.x + 1, move.y - 1);
            case DOWNLEFT:
                return new Point(move.x - 1, move.y + 1);
            case DOWNRIGHT:
                return new Point(move.x + 1, move.y + 1);
            default:
                throw new RuntimeException(position.toString());
        }
    }

    private void setCell(Point move, String player) {
        ArrayList<Point> checkedCells = new ArrayList<>();
        int numChanged = 0;

        for (Position n : neighbours) {
            ArrayList<Point> changingCells = new ArrayList<>();
            Point changingCell = convertToPoint(move, n);
            while (grid[changingCell.y][changingCell.x].equals(otherPlayer)) {
                changingCells.add(changingCell);
                changingCell = convertToPoint(new Point(changingCell.x, changingCell.y), n);
                if (changingCell.y == 0 || changingCell.y > 8 || changingCell.x == 0 || changingCell.x > 8) {
                    break;
                }
            }
            for (Point changed : changingCells) {
                if (grid[changingCell.y][changingCell.x].equals(player)) {
                    checkedCells.add(changed);
                    numChanged++;
                }
            }
        }
        if (numChanged == 0) {
            System.out.println("Move must be sandwiched with current player's cell " +
                    "at the end of the attempted row, column, or diagonal. " +
                    "Please re-enter cell value.");
            promptAndMakeMove();
        } else {
            for (Point checked : checkedCells) {
                grid[checked.y][checked.x] = player;
            }
            grid[move.y][move.x] = player;
        }
    }

    public boolean isGameOver() {
        if (moveCounter == 60) {
            int XCounter = 0, OCounter = 0;
            for (int row = 1; row < 9; row++) {
                for (int col = 1; col < 9; col++) {
                    if (grid[row][col].equals("X")) {
                        XCounter++;
                    } else {
                        OCounter++;
                    }
                }
            }
            if (XCounter == OCounter) {
                isDraw = true;
            } else isPlayerXMove = XCounter > OCounter;
            return true;
        }
        return false;
    }

    public String getWinner() {
        displayBoard();
        if (isDraw) {
            return "\nDraw.";
        }
        return isPlayerXMove ? "\nPlayer X wins!" : "\nPlayer O wins!";
    }
}