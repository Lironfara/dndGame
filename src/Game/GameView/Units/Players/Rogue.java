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
        this.attack = attack+(3*playerLevel);

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
                int playerAttacker = new Random().nextInt(0, getAttack());
                int rollDefender = new Random().nextInt(0,toAttack.getDefense());
                if (playerAttacker-rollDefender >0){
                toAttack.setHealth(toAttack.getHealth() - this.attack);
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
    public void visit(Tile tile) {
    }
    @Override
    public void accept(Wall wall) {
    }
    @Override
    public String describe(){
        String s = super.describe() + " , Energy: " + currentEnergy + "/" + 100 + "\n";
        return s;
    }
    public void accept(Units unit, Position newPosition) {
        unit.accept(this);
    }

}
