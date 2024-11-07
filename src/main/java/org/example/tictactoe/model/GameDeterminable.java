package org.example.tictactoe.model;

public interface GameDeterminable {

    boolean rowWinner(PlayerToken token);
    boolean columnWinner(PlayerToken token);
    boolean diagonalWinner(PlayerToken token);
    boolean invertedDiagonalWinner(PlayerToken token);
    boolean tieGame();

}
