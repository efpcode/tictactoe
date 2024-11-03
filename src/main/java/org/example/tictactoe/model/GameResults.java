package org.example.tictactoe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameResults implements GameOutcomes {
    final int matrixLength = 3;
    final private List<Player> players;

    GameResults() {
        this.players = new ArrayList<>();
    }

    GameResults(int matrixSideLength, List<Player> players) {
        this.players = new ArrayList<>(players);
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
                    .filter(player -> player.row() == index && player.column()==index)
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

        for (int i = 0, j = matrixLength-1; i < matrixLength; i++, j--) {
            int rowIndex = i;
            int columnIndex = j;
            var outcome = players.stream()
                    .filter(player -> player.row() == rowIndex && player.column()==columnIndex)
                    .map(Player::token)
                    .toList();
            if (!outcome.isEmpty())
                invertedDiagonal.add(outcome.getFirst());
        }

        return expected.equals(invertedDiagonal);


    }

    @Override
    public boolean tieGame() {
        if (!players.isEmpty() && players.size()<(matrixLength*matrixLength)) {
            return false;
        }

        var outcome = players.stream().map(Player::token).toList();
        if(!outcome.contains(PlayerToken.EMPTY))
            return true;

        return false;


    }

    public static void main(String[] args) {
        GameResults gameResults = new GameResults();
        gameResults.addPlayer(Player.of(0, 0, PlayerToken.CIRCLE));
        gameResults.addPlayer(Player.of(0, 1, PlayerToken.CROSS));
        gameResults.addPlayer(Player.of(0, 2, PlayerToken.CIRCLE));
        gameResults.addPlayer(Player.of(1, 0, PlayerToken.CROSS));
        gameResults.addPlayer(Player.of(1, 1, PlayerToken.CIRCLE));
        gameResults.addPlayer(Player.of(1, 2, PlayerToken.CROSS));
        gameResults.addPlayer(Player.of(2, 0, PlayerToken.CIRCLE));
        gameResults.addPlayer(Player.of(2, 1, PlayerToken.EMPTY));
        gameResults.addPlayer(Player.of(2, 2, PlayerToken.CROSS));
        System.out.println(gameResults.tieGame());


    }


}
