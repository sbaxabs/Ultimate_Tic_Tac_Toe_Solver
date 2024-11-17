public class Minimax {
    // 3x3 grid, each containing 9 cells
    private char[][][] board = new char[3][3][9];
    // 3x3 grid to track winners of smaller boards
    private char[][] winners = new char[3][3];

    // Constructor to initialize the board
    public Minimax() {
        initializeBoard();
    }

    // Method to initialize the board
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Initialize each small board with '-'
                for (int k = 0; k < 9; k++) {
                    board[i][j][k] = '-';
                }
                // No winner initially
                winners[i][j] = '-';
            }
        }
    }

    // Method to print the board for debugging
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("Small Board (" + i + "," + j + "): ");
                for (int k = 0; k < 9; k++) {
                    System.out.print(board[i][j][k] + " ");
                }
                System.out.println();
            }
        }
    }

    // Minimax implementation
    private int minimax(boolean isMaximizing) {
        // Base case: Check for a winner or draw
        int score = evaluateBoard();
        if (score != 0 || isDraw()) {
            return score;
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            // Simulate all possible moves for the maximizing player
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 9; k++) {
                        if (board[i][j][k] == '-') { // If the cell is empty
                            board[i][j][k] = 'X'; // Simulate 'X' move
                            maxEval = Math.max(maxEval, minimax(false));
                            board[i][j][k] = '-'; // Undo move
                        }
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            // Simulate all possible moves for the minimizing player
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 9; k++) {
                        if (board[i][j][k] == '-') { // If the cell is empty
                            board[i][j][k] = 'O'; // Simulate 'O' move
                            minEval = Math.min(minEval, minimax(true));
                            board[i][j][k] = '-'; // Undo move
                        }
                    }
                }
            }
            return minEval;
        }
    }

    // Evaluate the current state of the board (stub for now)
    private int evaluateBoard() {
        // Return a positive score for 'X' wins, negative for 'O', and 0 otherwise
        return 0;
    }

    // Check if the game is a draw
    private boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 9; k++) {
                    if (board[i][j][k] == '-') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Minimax game = new Minimax();
        game.printBoard(); // Verify initialization
    }
}

// Place a move on the board
public boolean makeMove(int bigRow, int bigCol, int smallCell, char player) {
    if (bigRow < 0 || bigRow >= 3 || bigCol < 0 || bigCol >= 3 || smallCell < 0 || smallCell >= 9) {
        System.out.println("Invalid move: Out of bounds.");
        return false;
    }
    if (board[bigRow][bigCol][smallCell] != '-') {
        System.out.println("Invalid move: Cell already occupied.");
        return false;
    }
    board[bigRow][bigCol][smallCell] = player;
    return true;
}

// Find the best move for the computer
public int[] findBestMove() {
    int bestScore = Integer.MIN_VALUE;
    int[] bestMove = new int[3]; // [bigRow, bigCol, smallCell]

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                if (board[i][j][k] == '-') { // If the cell is empty
                    board[i][j][k] = 'X'; // Simulate the computer's move
                    int score = minimax(false); // Evaluate the move
                    board[i][j][k] = '-'; // Undo the move
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestMove[2] = k;
                    }
                }
            }
        }
    }
    return bestMove;
}

import java.util.Scanner;

public static void main(String[] args) {
    Minimax game = new Minimax();
    Scanner scanner = new Scanner(System.in);
    boolean isPlayerTurn = true;

    System.out.println("Welcome to Ultimate Tic Tac Toe!");
    game.printBoard();

    while (true) {
        if (game.isDraw()) {
            System.out.println("It's a draw!");
            break;
        }

        if (isPlayerTurn) {
            // Player's turn
            System.out.println("Your turn (O). Enter your move as 'bigRow bigCol smallCell':");
            int bigRow = scanner.nextInt();
            int bigCol = scanner.nextInt();
            int smallCell = scanner.nextInt();

            if (game.makeMove(bigRow, bigCol, smallCell, 'O')) {
                isPlayerTurn = false;
                game.printBoard();
            } else {
                System.out.println("Invalid move. Try again.");
            }
        } else {
            // Computer's turn
            System.out.println("Computer's turn (X):");
            int[] bestMove = game.findBestMove();
            game.makeMove(bestMove[0], bestMove[1], bestMove[2], 'X');
            isPlayerTurn = true;
            game.printBoard();
        }
    }

    scanner.close();
}
