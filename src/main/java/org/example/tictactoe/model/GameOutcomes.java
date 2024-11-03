package org.example.tictactoe.model;

import java.util.List;
import java.util.Optional;

public interface GameOutcomes {
    boolean rowWinner(PlayerToken token);
    boolean columnWinner(PlayerToken token);
    boolean diagonalWinner(PlayerToken token);
    boolean invertedDiagonalWinner(PlayerToken token);
    boolean tieGame();

}
