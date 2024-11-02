package org.example.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

public class GameResults implements GameOutcomes {
    private final int matrixSideLength;
    final private List<Player> players;
    GameResults() {
        this.players = new ArrayList<>();
        this.matrixSideLength = 3;
    }

    GameResults(int matrixSideLength, List<Player> players) {
        this.players= new ArrayList<>(players);
        this.matrixSideLength = matrixSideLength;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (players.contains(player))
            removePlayer(player);
        int index = player.getLinearRepresentation(matrixSideLength);
        this.players.add(index, player);

    }

    public void removePlayer(Player player) {
        int index = player.getLinearRepresentation(matrixSideLength);
        this.players.remove(index);
    }

    public void removeAllPlayers() {
        this.players.clear();
    }

    @Override
    public boolean rowWinner(PlayerToken token) {
        List<Boolean> isRowWinner = new ArrayList<>();
        for (int i = 0; i <matrixSideLength ; i++) {
            int finalI = i;
           isRowWinner.add( players.stream().filter(player -> player.position().row()== finalI)
                    .allMatch(p -> p.token().equals(token)));
        }
        return isRowWinner.contains(true);
    }

    @Override
    public boolean columnWinner(PlayerToken token) {
        List<Boolean> isColumnWinner = new ArrayList<>();
        for (int i = 0; i <matrixSideLength ; i++) {
            int finalI = i;
            isColumnWinner.add( players.stream()
                    .filter(player -> player.position().column()== finalI)
                    .toList()
                    .stream()
                    .allMatch(player -> player.token().equals(token)));
        }
        return isColumnWinner.stream().anyMatch(i -> i);
    }

    @Override
    public boolean diagonalWinner(PlayerToken token) {
        List<Boolean> isDiagonalWinner = new ArrayList<>();
        for (int i = 0; i <matrixSideLength ; i++) {
            int finalI = i;
            isDiagonalWinner.add(players.stream()
                    .filter(player ->player.position().row()== finalI && player.position().column()== finalI)
                    .toList()
                    .stream().allMatch(player -> player.token().equals(token)));
        }
        return isDiagonalWinner.stream().allMatch(i -> i);
    }

    @Override
    public boolean invertedDiagonalWinner(PlayerToken token) {
        List<Boolean> isInvertedDiagonalWinner = new ArrayList<>();
        for (int i = 0, j= matrixSideLength-1; i <matrixSideLength ; i++, j--) {
            int finalI = i;
            int finalJ = j;

            isInvertedDiagonalWinner.add(players.stream()
                    .filter(player -> player.position().row()==finalI && player.position().column()==finalJ)
                    .toList()
                    .stream()
                    .allMatch(p -> p.token().equals(token)));


        }
        return isInvertedDiagonalWinner.stream().allMatch(i -> i);


    }

    @Override
    public boolean tieGame() {
        return players.stream()
                .filter(player -> player.token().equals(PlayerToken.EMPTY))
                .toList()
                .isEmpty();
    }

    public static void main(String[] args) {
        GameResults gameResults = new GameResults();
        gameResults.addPlayer(Player.of(0,0, PlayerToken.CROSS));
        gameResults.addPlayer(Player.of(0,1, PlayerToken.CIRCLE));
        gameResults.addPlayer(Player.of(0,2, PlayerToken.CROSS));
        gameResults.addPlayer(Player.of(1,0, PlayerToken.CIRCLE));
        gameResults.addPlayer(Player.of(1,1, PlayerToken.CROSS));
        gameResults.addPlayer(Player.of(1,2, PlayerToken.CROSS));
        gameResults.addPlayer(Player.of(2,0, PlayerToken.CIRCLE));
        gameResults.addPlayer(Player.of(2,1, PlayerToken.CROSS));
        gameResults.addPlayer(Player.of(2,2, PlayerToken.EMPTY));
        System.out.println(gameResults.tieGame());
        gameResults.addPlayer(Player.of(2,2, PlayerToken.CIRCLE));
        System.out.println(gameResults.tieGame());
        System.out.println(gameResults.players);


    }


}
