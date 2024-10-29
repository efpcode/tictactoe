package org.example.tictactoe.model;

/**
 * Matrix2dPositions.
 * Represents a Matrix with column and row values >= 0.
 * The matrix created is perfect squared with rows and columns being of equal length.
 * Default constructor set values of row and colum to 0.
 * @param row int value > 0 and length of row == column
 * @param column int value > 0 and length of column == row
 */
public record Matrix2dPositions(int row, int column){

    public Matrix2dPositions() {
        this(0, 0);
    }

    public Matrix2dPositions(int row, int column){
        if(!(row == column)){
            throw new IllegalArgumentException("Row and column must be the same");
        }

        if(column < 0){
            throw new IllegalArgumentException("Row and column must be greater than zero");
        }

        this.row = row;
        this.column = column;

    }
}
