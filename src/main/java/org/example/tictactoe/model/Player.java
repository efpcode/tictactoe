package org.example.tictactoe.model;



public record Player(Matrix2dPositions position, PlayerToken token) {
    public int getLinearRepresentation(int steps){
        int step = position.row()>=1 ? steps-1 : 0;
        return position().row() + step +position().column();

    }

}

record Matrix2dPositions(int row, int column){}
