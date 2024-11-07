package org.example.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

public class GameResults implements GameDeterminable {
    private int matrixLength;
    final private List<Player> players;

    GameResults() {
        this.players = new ArrayList<>();
        this.matrixLength = 3;
    }

    GameResults(int matrixSideLength, List<Player> players) {
        this.players = new ArrayList<>(players);
        this.matrixLength = matrixSideLength;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (players.contains(player))
            removePlayer(player);
        int index = player.getLinearRepresentation();
        this.players.add(index, player);

    }

    public void initializeGameBoard() {
        List<Player> emptyBoard = new ArrayList<>();
        for (int i = 0; i < (matrixLength*matrixLength); i++) {
            emptyBoard.add(i, Player.valueOf(i/matrixLength, i%matrixLength, PlayerToken.EMPTY));

        }
        this.players.addAll(emptyBoard);

    }

    public boolean isGameBoardEmpty() {
        var outcome = players.stream().filter(player -> player.token() == PlayerToken.EMPTY).count();
        return outcome == (int)(matrixLength * matrixLength);
    }


    public void removePlayer(Player player) {
        int index = player.getLinearRepresentation();
        this.players.remove(index);
    }

    public void removeAllPlayers() {
        this.players.clear();
    }

    @Override
    public boolean rowWinner(PlayerToken token) {
        List<PlayerToken> expected = getPlayerTokens(token, matrixLength);
        for (int i = 0; i < matrixLength; i++) {
            int index = i;
            var outcome = players.stream()
                    .filter(p -> p.row() == index)
                    .toList()
                    .stream()
                    .map(Player::token)
                    .toList().equals(expected);
            if (outcome)
                return true;
        }
        return false;


    }

    public List<PlayerToken> getPlayerTokens(PlayerToken chip, int numberOfLoops) {
        List<PlayerToken> expectedTokens = new ArrayList<>();
        for (int i = 0; i < numberOfLoops; i++) {
            expectedTokens.add(chip);
        }
        return expectedTokens;
    }


    @Override
    public boolean columnWinner(PlayerToken token) {
        List<PlayerToken> expected = getPlayerTokens(token, matrixLength);
        for (int i = 0; i < matrixLength; i++) {
            int index = i;
            var outcome = players.stream()
                    .filter(p -> p.column() == index)
                    .toList()
                    .stream()
                    .map(Player::token)
                    .toList().equals(expected);
            if (outcome)
                return true;
        }
        return false;
    }

    @Override
    public boolean diagonalWinner(PlayerToken token) {
        List<PlayerToken> expected = getPlayerTokens(token, matrixLength);
        List<PlayerToken> diagonal = new ArrayList<>();

        for (int i = 0; i < matrixLength; i++) {
            int index = i;
            var outcome = players.stream()
                    .filter(player -> player.row() == index && player.column() == index)
                    .map(Player::token)
                    .toList();
            if (!outcome.isEmpty())
                diagonal.add(outcome.getFirst());
        }

        return expected.equals(diagonal);

    }

    @Override
    public boolean invertedDiagonalWinner(PlayerToken token) {
        List<PlayerToken> expected = getPlayerTokens(token, matrixLength);
        List<PlayerToken> invertedDiagonal = new ArrayList<>();

        for (int i = 0, j = matrixLength - 1; i < matrixLength; i++, j--) {
            int rowIndex = i;
            int columnIndex = j;
            var outcome = players.stream()
                    .filter(player -> player.row() == rowIndex && player.column() == columnIndex)
                    .map(Player::token)
                    .toList();
            if (!outcome.isEmpty())
                invertedDiagonal.add(outcome.getFirst());
        }

        return expected.equals(invertedDiagonal);


    }

    @Override
    public boolean tieGame() {
        if (!players.isEmpty() && players.size() < (matrixLength * matrixLength)) {
            return false;
        }

        var outcome = players.stream().map(Player::token).toList();
        if (!outcome.contains(PlayerToken.EMPTY))
            return true;

        return false;
    }


}
