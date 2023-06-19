package Game.GameView.BoardPackaeg;

import Game.GameView.Units.Units;

import java.util.ArrayList;
import java.util.List;

public class Position implements Comparable<Position>{

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

    @Override
    public int compareTo(Position o) {
        if (this.position[0] > o.position[0]){
            return 1;
        }
        if (this.position[0] < o.position[0]){
            return -1;
        }
        if (this.position[0] == o.position[0] && this.position[1] > o.position[1]){
            return 1;
        }
        if (this.position[0] == o.position[0] && this.position[1] < o.position[1]){
            return -1;
        }
        return 0;
    }
}



