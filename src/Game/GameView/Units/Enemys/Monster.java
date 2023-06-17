package Game.GameView.Units.Enemys;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.Units.Health;
import Game.GameView.Units.Players.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Monster extends Enemy {

    private int visionRange;

    private ArrayList<Character> randomMovments;
    public Monster(char c, String name, int health, int attackPoints, int defensePoints, int experienceValue, int visionRange) {
        super(c,name, health, attackPoints, defensePoints, experienceValue);
        this.visionRange = visionRange;
        this.randomMovments  = initilizeMovments(new ArrayList<>()) ;
    }

    public ArrayList<Character> initilizeMovments(ArrayList randomMovments){
        randomMovments.add(0, 'w');
        randomMovments.add(1, 's');
        randomMovments.add(2, 'a');
        randomMovments.add(3, 'd');
        return randomMovments;
    }

    @Override
    public char toChar() {
        return super.toChar();
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public void victory(Enemy enemy) {}
    public void victory(Player player){
        player.onDeath();
    }


    // to check movements
    public void gameTick(Player player){
        if (new Range(this.position.getPosition(), player.position.getPosition()).getRange()< visionRange){
            int dx = this.position.getPosition()[0]- player.position.getPosition()[0];
            int dy = this.position.getPosition()[1]- player.position.getPosition()[1];
            if (Math.abs(dx) > Math.abs(dy)){
                if (Math.abs(dx) > 0){
                    moveLeft(); //To check to new position is valid
                }
                else {
                    moveRight();
                }

            }
            else{
                if (Math.abs(dy)>0){
                    moveUp();
                }
                else {
                    moveDown();
                }
            }
        }
        else{
            Random rand = new Random();
            char movement = randomMovments.get(randomMovments.get(rand.nextInt(randomMovments.size())));
            moveRandomly(movement);
        }
    }
}
