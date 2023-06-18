package Game.GameView.BoardPackaeg;

import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Units;

public class Empty extends Tile {

    protected Position position;


    public Empty(Position position){
        super('.');
        this.initialize(position);
        this.position = position;
    }

    //Same operation for both enemy and player
    public boolean accept(Units unit, Position newPosition) {
         unit.setPosition(newPosition);
         return true;
    }

    //Who visits me? the unit


    @Override
    public void visit(Enemy enemy) {
        Position temp = enemy.getPosition();
        enemy.setPosition(this.position);
        this.setPosition(temp);
    }

    public void visit(Player player) {
        Position temp = player.position;
        player.setPosition(this.position);
        this.setPosition(temp);
    }


    @Override
    public void visit(Empty empty) {}


    @Override
    public void visit(Wall wall) {}

    @Override
    public void visit(Tile tile) {

    }


    public void accept(Tile tile) {

        tile.visit(this);
    }

    public void accept(Empty empty){}
    public void accept (Wall wall){}

    @Override
    public void accept(Enemy enemy) {
        enemy.visit(this);
    }

    @Override
    public void accept(Player player) {
        player.visit(this);
    }


    public Empty ProduceEmpty(Position position){
        return new Empty(position);

    }


    public String toString(){
        return ".";
    }

}
