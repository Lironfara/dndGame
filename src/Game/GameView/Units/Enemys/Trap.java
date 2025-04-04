package Game.GameView.Units.Enemys;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.CLI;
import Game.GameView.MessageCallback;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Units;

import java.util.Random;

public class Trap extends Enemy{

    protected int visibilityTime;
    protected int invisibilityTime;
    protected int tickCount;
    protected boolean visible;
    protected int attackPoints;
    protected char trapTile;
    protected MessageCallback CLI;


    public Trap(char c, String name, int health, int attackPoints, int defensePoints, int experienceValue,
                int visibilityTime, int invisibilityTime) {
        super(c,name, health, attackPoints, defensePoints, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.tickCount=0;
        this.visible=true;
        this.attackPoints = attackPoints;
        this.trapTile = c;
        this.CLI = new CLI();
    }

    public Position gameTick(Player player){
        visible =tickCount<visibilityTime;
        if (tickCount==visibilityTime+invisibilityTime){
            tickCount=0;
        }
        else{
            tickCount++;
        }
        if (new Range(player.position.getPosition(), this.position.getPosition()).getRange()<2){
            combat(player);
        }
        if (visible){
            super.setTile(trapTile);
            this.tile = trapTile;
        }
        else{
            super.setTile('.');
            this.tile = '.';
        }
        return this.position;
    }



    public void combat(Units unit) {
        int rollAttacker = new Random().nextInt(0, this.attackPoints);
        int rollDefender = new Random().nextInt(0, unit.getDefense());
        CLI.combat(this.Name + " rolled "+ rollAttacker + " attack points");
        CLI.combat(this.Name + " rolled "+ rollAttacker + " attack points");
        if ((rollAttacker - rollDefender)>0){
            unit.setHealth(unit.getHealth()- (rollAttacker-rollDefender));
            CLI.combat(this.Name+" dealt "+ (rollAttacker-rollDefender)+" damage to "+unit.getName());
            if (unit.getHealth() <= 0){
                unit.victory(this);
            }
        }

    }


    @Override
    public void victory(Enemy enemy) {

    }

    @Override
    public char toChar() {
        if(visible)
            return super.toChar();
        else
            return '.';
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }


    @Override public void visit(Empty e){

    }

    @Override
    public void visit(Tile tile) {

    }

    @Override
    public void accept(Wall wall) {}


}
