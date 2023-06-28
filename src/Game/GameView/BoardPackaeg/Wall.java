package Game.GameView.BoardPackaeg;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Units;

public class Wall extends Tile {
    public Wall(Position position) {
        super('#');
        this.initialize(position);
    }
    public void visit(Player player){}
    public void visit(Enemy enemy){}
    @Override
    public void visit(Empty empty) {}
    public void visit(Wall wall) {}
    @Override
    public void visit(Tile tile) {}
    public void accept(Units unit) {}
    public void accept(Empty empty) {
        empty.visit(this);
    }
    @Override
    public void accept(Wall wall) {
        wall.visit(this);
    }
    @Override
    public void accept(Enemy enemy) {}
    @Override
    public void accept(Player player) { }
    @Override
    public void accept(Tile tile) {}
    public String toString(){
        return "#";
    }
}
