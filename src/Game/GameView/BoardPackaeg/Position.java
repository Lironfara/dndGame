package Game.GameView.BoardPackaeg;

import Game.GameView.Units.Units;

import java.util.ArrayList;
import java.util.List;

public class Position {

    private int[] position;

    public Position(int[] position){
        this.position = position;
    }
    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
}



