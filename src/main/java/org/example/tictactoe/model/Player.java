package org.example.tictactoe.model;


import java.util.Objects;

public record Player(int row, int column, int matrixLength , PlayerToken token) {

    public Player(){
        this(0, 0, 2, PlayerToken.EMPTY);
    }
    public Player(int row, int column, PlayerToken token){
        this(row, column, 3, token);
    }

   public Player(int row, int column, int matrixLength, PlayerToken token){
        if (column < 0 || column >= matrixLength || row < 0 || row >= matrixLength) {
            throw new IllegalArgumentException("Row and column must be greater than zero and not greater than matrix length");
        }
        this.row = row;
        this.column = column;
        this.matrixLength = matrixLength;
        this.token = token;


    }

    public static Player of(int row, int column, PlayerToken token) {
        return new Player(row, column, token);

    }

    public int getLinearRepresentation(){
        int step = matrixLength();
        step = row()>=1 ? step-1 : 0;
        if (step == 0){
            return row() + step + column();
        }
        return (int) Math.pow(step, row() ) + column()+ row();

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return row == player.row && column == player.column && matrixLength == player.matrixLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, matrixLength);
    }
}
