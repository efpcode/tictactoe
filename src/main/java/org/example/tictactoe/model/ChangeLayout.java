package org.example.tictactoe.model;

import org.example.tictactoe.GameApplication;

import static org.example.tictactoe.model.BoardState.*;


public abstract class ChangeLayout {
    private GameApplication stage;

    public GameApplication getView() {
        return stage;

    }

    public void setView(GameApplication stage){
        this.stage = stage;
    }


}
