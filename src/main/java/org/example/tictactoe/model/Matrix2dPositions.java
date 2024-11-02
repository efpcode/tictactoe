package org.example.tictactoe.model;

/**
 * Matrix2dPositions.
 * Represents a Matrix with column and row values >= 0.
 * The matrix created is perfect squared with rows and columns being of equal length.
 * Default constructor set values of row and colum to 0 and matrixLength = 2.
 * @param row int value > 0
 * @param column int value > 0
 * @param maxLength ensure that matrix is squared and length indices of matrix start from 0 to maxlength -1
 */
public record Matrix2dPositions(int row, int column, int maxLength) {

    public Matrix2dPositions() {
        this(0, 0, 2);
    }

    public Matrix2dPositions(int row, int column){
        this(row, column, 3);
    }

    public Matrix2dPositions(int row, int column, int maxLength) {
        if (column < 0 || column >= maxLength || row < 0 || row >= maxLength) {
            throw new IllegalArgumentException("Row and column must be greater than zero");
        }

        this.row = row;
        this.column = column;
        this.maxLength = maxLength;

    }

    public static Matrix2dPositions of(int row, int column, int maxLength) {
        return new Matrix2dPositions(row, column, maxLength);
    }

}
