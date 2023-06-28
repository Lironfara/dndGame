package Game.GameView.BoardPackaeg;

import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;

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
    public abstract void visit(Enemy enemy);
    public abstract void visit (Player player);
    public abstract void visit(Empty empty);
    public abstract void visit(Tile tile);
    public abstract void accept(Wall wall);
    public void setTile(char newTile){
        this.tile = newTile;
    }
    public abstract void accept(Enemy enemy);
    public abstract void accept(Player player);
    public abstract void accept(Tile tile);
    public int compareTo(Tile tile) {
        if(this.position.getPosition()[0] > tile.position.getPosition()[0]){
            return 1;
        }
        else if (this.position.getPosition()[0] < tile.position.getPosition()[0]){
            return -1;
        }
        return 0;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
}
