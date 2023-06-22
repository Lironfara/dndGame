package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.CLI;
import Game.GameView.MessageCallback;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Units;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rogue extends Player{
    int cost;
    int currentEnergy;

    protected String abilityCastName;
    private MessageCallback messageCallback;

    public Rogue(String name, int health, int attackPoints, int defensePoints, int cost){
        super('@', name,health,attackPoints,defensePoints);
        this.cost = cost;
        this.currentEnergy = 100;
        this.abilityCastName = "Fan of knives";
        this.messageCallback = new CLI();

    }


    public void interact(Tile tile) {
        this.accept(tile);
        setCurrentEnergy(currentEnergy+10);

    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.currentEnergy=100;
        this.attackPoints = attackPoints+(3*playerLevel);
        messageCallback.levelUp(this.name + " reached level "+ playerLevel+ ":" + "+"+ 3*playerLevel+ " Attack points");

    }

    public Position gameTick(String movement){
        setCurrentEnergy(currentEnergy+10);
        messageCallback.describe(describe());
        return super.gameTick(movement);
    }

    public String describe(){
        String s = getName() + "   Health :"+ getHealthPool()+"/"+getHealthAmount() + "   Attack :"+getAttack() + "   Defense :"+getDefense()+ "   Experience : "+getExperience() + "   Current Energy : "+currentEnergy+"/"+cost;
        return s;

    }


    @Override
    public List<Enemy> abilityCast(List<Enemy> enemiesOnBoard){
        List<Enemy> enemiesToRemove = new ArrayList<>();
        if (currentEnergy<cost){
            messageCallback.abilityCast(name + " tried to cast "+ abilityCastName +" but has not enough sorceresses.");
        }
        else {
            setCurrentEnergy(currentEnergy - cost);
            ArrayList<Enemy> enemiesOnRange = new ArrayList<>();
            for (Enemy enemy : enemiesOnBoard) {
                if (new Range(this.getPosition().getPosition(), enemy.getPosition().getPosition()).getRange() < 2 && !enemy.isDead()) {
                    enemiesOnRange.add(enemy);
                }
            }
            for (Enemy toAttack:enemiesOnRange) {
                int playerAttacker = new Random().nextInt(0, this.attackPoints);
                int rollDefender = new Random().nextInt(0,toAttack.getDefense());
                if (playerAttacker-rollDefender >0){
                toAttack.setHealth(toAttack.getHealth() - this.attackPoints);
                if (toAttack.getHealth() <= 0) {
                    this.setExperience(toAttack.getExperienceValue() + experience);
                    toAttack.onDeath();
                    enemiesToRemove.add(toAttack);
                    messageCallback.abilityCast(name + " cast " + abilityCastName + ",  and killed " + toAttack.getName() + "."
                            + name + " gained another " + toAttack.getExperienceValue() + " experience points");
                    enemiesOnRange.remove(toAttack);
                }
                else {
                    messageCallback.abilityCast(name + " cast " + abilityCastName + "and damaged " + toAttack.getName());
                }
            }
        }
    }
        return enemiesToRemove;
    }


    private void setCurrentEnergy(int newCurrentEnergy){
        if (newCurrentEnergy > 100){
            currentEnergy = 100;
        }
        else {
            currentEnergy= newCurrentEnergy;
        }
    }

    @Override
    public void visit(Empty e) {


    }


    @Override
    public void visit(Tile tile) {

    }

    @Override
    public void accept(Wall wall) {

    }


    @Override
    public void visit(Wall w) {
        super.visit(w);
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    public void accept(Units unit, Position newPosition) {
        unit.accept(this);
    }

}
