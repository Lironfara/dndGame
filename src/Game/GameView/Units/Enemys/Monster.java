package Game.GameView.Units.Enemys;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.CLI;
import Game.GameView.MessageCallback;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Units;

import java.util.*;

public class Monster extends Enemy {
    private int visionRange;
    protected MessageCallback CLI;
    private ArrayList<Character> randomMovments;
    public Monster(char c, String name, int health, int attackPoints, int defensePoints, int experienceValue, int visionRange) {
        super(c,name, health, attackPoints, defensePoints, experienceValue);
        this.visionRange = visionRange;
        this.randomMovments  = initilizeMovments(new ArrayList<>()) ;
        this.CLI = new CLI();
    }

    public ArrayList<Character> initilizeMovments(ArrayList randomMovments){
        randomMovments.add(0, 'w');
        randomMovments.add(1, 's');
        randomMovments.add(2, 'a');
        randomMovments.add(3, 'd');
        return randomMovments;
    }
    @Override
    public void visit(Empty empty) {
    }
    @Override
    public void visit(Tile tile) {
    }
    @Override
    public void accept(Wall wall) {
    }
    public void accept(Empty empty) {}
    public void accept(Units unit, Position newPosition) {
        unit.accept(this);
    }
    @Override
    public void victory(Enemy enemy) {}
    public void victory(Player player){
        player.onDeath();
    }
    public Position gameTick(Player player){
        if (new Range(this.position.getPosition(), player.position.getPosition()).getRange()< visionRange){
            int dx = this.position.getPosition()[0]- player.position.getPosition()[0];
            int dy = this.position.getPosition()[1]- player.position.getPosition()[1];
            if (Math.abs(dx) > Math.abs(dy)){
                if (dx > 0){
                    return moveLeft(); //To check to new position is valid
                }
                else {
                    return moveRight();
                }

            }
            else{
                if (dy>0){
                    return moveUp();
                }
                else {
                    return moveDown();
                }
            }
        }
        else{
            Collections.shuffle(randomMovments);
            return moveRandomly(randomMovments.get(0));
        }
    }
}
