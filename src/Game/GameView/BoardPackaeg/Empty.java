package Game.GameView.BoardPackaeg;

import Game.GameView.Units.Units;

public class Empty extends Tile {

    protected Position position;


    public Empty(Position position){
        super('.');
        this.initialize(position);
    }

    //Same operation for both enemy and player
    public boolean accept(Units unit, Position newPosition) {
         unit.setPosition(newPosition);
         return true;
    }

    @Override
    public void accept(Units unit) {
        unit.accept(this);
    }

    public void accept(Tile tile) {

        tile.accept(this);
    }

    public void accept(Empty empty){}
    public void accept (Wall wall){}


    public Empty ProduceEmpty(Position position){
        return new Empty(position);

    }


    public String toString(){
        return ".";
    }

}
