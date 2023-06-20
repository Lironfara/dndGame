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

    public Empty (Position position,char c){
        super('X');
        this.initialize(position);
        this.position = position;
    }

    //Who visits me? the unit

    public void setPosition(Position position){
        this.position = position;
    }

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
    public void visit(Empty empty) {
    }



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

    }

    @Override
    public void accept(Player player) {

    }


    public Empty ProduceEmpty(Position position){
        return new Empty(position);

    }


    public String toString(){
        return ".";
    }

}
