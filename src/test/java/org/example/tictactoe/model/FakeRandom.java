package org.example.tictactoe.model;
import java.util.Random;


public class FakeRandom extends Random{

    int indexAtZero = 0;

    @Override
    public int nextInt(int bound) {
        bound = 0;
        return bound;
    }

}
