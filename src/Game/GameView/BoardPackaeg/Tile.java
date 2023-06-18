package Game.GameView.BoardPackaeg;

import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Units;

public abstract class Tile implements Comparable<Tile> {
    protected char tile;
    protected Position position;

    protected Tile(char tile){
        this.tile = tile;
    }

    protected void initialize(Position position){
        this.position = position;
    }

    public char getTile() {
        return tile;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract void visit(Enemy enemy);

    public abstract void visit (Player player);

    public abstract void visit(Empty empty);

    public abstract void visit(Wall wall);

    public abstract void visit(Tile tile);
    public abstract void accept(Empty empty);
    public abstract void accept(Wall wall);

    public abstract void accept(Enemy enemy);
    public abstract void accept(Player player);


    public abstract void accept(Tile tile);
    public int compareTo(Tile tile) {
        if(this.position.getPosition()[0] > tile.position.getPosition()[0]){
            return 1;
        }
        if (this.position.getPosition()[0] < tile.position.getPosition()[0]){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }

}
