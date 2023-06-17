package Game.GameView.BoardPackaeg;

import Game.GameView.Units.Units;

public class Wall extends Tile {

    public Wall(Position position) {
        super('#');
        this.initialize(position);
    }

    public void accept(Units unit) {
    }

    @Override
    public void accept(Tile tile) {}

    @Override
    public void accept(Empty empty) {}

    @Override
    public void accept(Wall wall) {}

    public String toString(){
        return "#";
    }
}
