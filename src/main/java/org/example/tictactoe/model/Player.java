package org.example.tictactoe.model;



public record Player(Matrix2dPositions position, PlayerToken token) {
    public int getLinearRepresentation(int steps){
        int factor = position.row()/steps;
        int step = factor>=1 ? 0 : steps-1;
        return factor + step +position().column();

    }

}

record Matrix2dPositions(int row, int column){}
