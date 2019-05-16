package edu.mco264;

import java.awt.*;
import java.util.Scanner;

public class ConnectFour {

    enum Cell {NONE, O, X}

    private boolean isPlayerXMove, isDraw;
    private int moveCounter = 0;
    private Cell[][] grid;

    ConnectFour() {
        grid = new Cell[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                grid[row][col] = Cell.NONE;
            }
        }
    }

    public void togglePlayer() {
        isPlayerXMove = !isPlayerXMove;
    }

    public void displayBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                System.out.print(convertToString(grid[row][col]) + " | ");
            }
            System.out.println("\n----------------------------");
        }
    }

    private String convertToString(Cell cell) {
        switch (cell) {
            case NONE:
                return " ";
            case O:
                return "O";
            case X:
                return "X";
            default:
                throw new RuntimeException(cell.toString());
        }
    }

    public void promptAndMakeMove() {
        Point move = prompt();
        setCell(move.y, move.x, isPlayerXMove ? Cell.X : Cell.O);
        moveCounter++;
    }

    private Point prompt() {
        Scanner scan = new Scanner(System.in);
        int col, row = 0;
        do {
            System.out.println("\nEnter your column (A-G), Mr. "
                    + (isPlayerXMove ? "X" : "O"));
            col = scan.next().toUpperCase().charAt(0) - 'A';

            if (col < 7) {
                for (row = 5; row > 0; row--) {
                    if (grid[row][col] == Cell.NONE) {
                        break;
                    }
                }
            }
        } while (isValidMove(col));
        return new Point(col, row);
    }

    public void setCell(int row, int col, Cell cell) {
        if (grid[row][col] == Cell.NONE) {
            grid[row][col] = cell;
        }
    }

    private boolean isValidMove(int col) {
        if (col > 6) {
            System.out.println("Move must be within columns A-G. " +
                    "Please re-enter column value.");
            return true;
        } else if (grid[0][col] != Cell.NONE) {
            System.out.println("Column " + (char) (col + 'A') + " is full. " +
                    "Please try another one.");
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        int row, col, diagRow, diagCol;

        for (row = 5; row > 2; row--) {
            for (col = 0; col < 4; col++) {
                for (Cell[] rows : grid) {
                    if (grid[row][col] != Cell.NONE
                            && grid[row][col + 1] == grid[row][col]
                            && grid[row][col + 2] == grid[row][col + 1]
                            && grid[row][col + 3] == grid[row][col + 2]) {
                        return true;
                    }
                }
                for (Cell[] cols : grid) {
                    if (grid[row][col] != Cell.NONE
                            && grid[row - 1][col] == grid[row][col]
                            && grid[row - 2][col] == grid[row - 1][col]
                            && grid[row - 3][col] == grid[row - 2][col]) {
                        return true;
                    }
                }
                if (grid[row][col] != Cell.NONE
                        && grid[row][col] == grid[row - 1][col + 1]
                        && grid[row - 1][col + 1] == grid[row - 2][col + 2]
                        && grid[row - 2][col + 2] == grid[row - 3][col + 3]) {
                    return true;
                }
            }
        }

        for (diagRow = 0; diagRow < 3; diagRow++) {
            for (diagCol = 0; diagCol < 4; diagCol++) {
                if (grid[diagRow][diagCol] != Cell.NONE
                        && grid[diagRow][diagCol] == grid[diagRow + 1][diagCol + 1]
                        && grid[diagRow + 1][diagCol + 1] == grid[diagRow + 2][diagCol + 2]
                        && grid[diagRow + 2][diagCol + 2] == grid[diagRow + 3][diagCol + 3]) {
                    return true;
                }
            }
        }

        if (moveCounter == 42) {
            isDraw = true;
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