package org.example.tictactoe.model;


import java.util.Objects;

public record Player(Matrix2dPositions position, PlayerToken token) {

    public static Player of(int row, int column, PlayerToken token) {
        Matrix2dPositions positions = new Matrix2dPositions(row, column);
        return new Player(positions, token);

    }

    public int getLinearRepresentation(int steps){
        int step = position.row()>=1 ? steps-1 : 0;
        if (step == 0){
            return position().row() + step + position().column();
        }
        return (int) Math.pow(step, position().row() ) + position().column()+position().row();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return Objects.equals(position, player.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
