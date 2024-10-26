package org.example.tictactoe.model;

public class Model {
    private BoardState boardState = BoardState.UNSET;

    // Might not be needed if I solve this with Controllers instead.

    public BoardState getBoardState() {
        return boardState;
    }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;

    }
}
