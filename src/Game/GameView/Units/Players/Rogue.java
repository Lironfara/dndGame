package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Units;

import java.util.ArrayList;

public class Rogue extends Player{
    int cost;
    int currentEnergy;

    protected String abilityCastName;

    public Rogue(String name, int health, int attackPoints, int defensePoints, int cost){
        super('@', name,health,attackPoints,defensePoints);
        this.cost = cost;
        this.currentEnergy = 100;
        this.abilityCastName = "Fan of knives";

    }


    public void gameTick() {
        setCurrentEnergy(currentEnergy+10);
        describe();
    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.currentEnergy=100;
        this.attackPoints = attackPoints+(3*playerLevel);

    }

    @Override
    public void abilityCast(){
        if (currentEnergy<cost){
            abilityCastMessage(name + " tried to cast "+ abilityCastName +" but has not enough sorceresses.");
        }
        else {
            setCurrentEnergy(currentEnergy - cost);
            ArrayList<Enemy> enemiesOnBoard = new ArrayList<>();
            ArrayList<Enemy> enemiesOnRange = new ArrayList<>();
            for (Enemy enemy : enemiesOnBoard) {
                if (new Range(this.getPosition().getPosition(), enemy.getPosition().getPosition()).getRange() < 2) {
                    enemiesOnRange.add(enemy);
                }
            }
            for (Enemy toAttack:enemiesOnRange) {
                toAttack.setHealth(toAttack.getHealth() - this.attackPoints);
                if (toAttack.getHealth() <= 0) {
                    this.setExperience(toAttack.getExperienceValue() + experience);
                    toAttack.onDeath();
                    abilityCastMessage(name + " cast " + abilityCastName + ",  and killed " + toAttack.getName() + "."
                            + name + " gained another " + toAttack.getExperienceValue() + " experience points");
                    enemiesOnRange.remove(toAttack);
                }
                else {
                    abilityCastMessage(name + " cast " + abilityCastName + "and damaged " + toAttack.getName());
                }
            }
        }
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
        super.visit(e);
    }


    @Override
    public void visit(Tile tile) {

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
