package org.example.tictactoe.model;

public interface GameOutcomes {
    boolean rowWinner(PlayerToken token);
    boolean columnWinner(PlayerToken token);
    boolean diagonalWinner(PlayerToken token);
    boolean invertedDiagonalWinner(PlayerToken token);
    boolean tieGame();

}
