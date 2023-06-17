package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Health;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warrior extends Player{
    protected int abilityCoolDown;
    private int remainingCoolDown;

    private String specialAbilityName;


    public Warrior(String name, int health, int attackPoints, int defensePoints, int abilityCoolDown){
        super('@', name,health,attackPoints,defensePoints);
        this.abilityCoolDown = abilityCoolDown;
        this.remainingCoolDown = 0;
        this.specialAbilityName = "Avenger's Shield";
    }

    public void onDeath(){
        super.onDeath();

    }

    @Override
    public void abilityCast(){
        if (remainingCoolDown>0){
            abilityCastMessage(name+" tried to cast "+ specialAbilityName+",  but there is a cooldown: "+ remainingCoolDown);
        }
        else{
            ArrayList<Enemy> enemiesOnBoard = new ArrayList<>() ;;
            ArrayList<Enemy> enemiesOnRange = new ArrayList<>() ;
            for (Enemy enemy: enemiesOnBoard) {
                if (new Range(this.getPosition().getPosition(), enemy.getPosition().getPosition()).getRange() < 3){
                    enemiesOnRange.add(enemy);
                }
            }
            Collections.shuffle(enemiesOnRange);

            this.health.setHealthAmount(health.getHealthAmount()+10*defensePoints);
            abilityCastMessage(name + " cast "+ specialAbilityName+" , healing for "+ 10*defensePoints);

            if (enemiesOnBoard.size()>=1) {
                Enemy toAttack = enemiesOnRange.get(0);
                toAttack.setHealth(toAttack.getHealth() - this.health.getHealthPool() / 10);
                if (toAttack.getHealth() <= 0) {
                    this.setExperience(toAttack.getExperienceValue() + experience);
                    toAttack.onDeath();
                    abilityCastMessage(name+" cast "+ specialAbilityName+",  and killed "+ toAttack.getName()+"."
                    + name + " gained another "+ toAttack.getExperienceValue() +" experience points");
                }
                else{
                    abilityCastMessage(name +" cast "+ specialAbilityName + "and damaged " + toAttack.getName() );
                }
            }
            remainingCoolDown = abilityCoolDown;

        }
    }
    @Override
    public void levelUp() {
        super.levelUp();
        remainingCoolDown =0;
        health.setHealthPool(health.getHealthPool()+(5*playerLevel));
        attackPoints = attackPoints+(2*playerLevel);
        defensePoints = defensePoints+(playerLevel);

    }

    public void gameTick(char c){
        if (remainingCoolDown>0){
            remainingCoolDown--;
        }
    }

    @Override
    public void visit(Empty e) {
        super.visit(e);
    }

    @Override
    public void visit(Wall w) {
        super.visit(w);
    }

    @Override
    public void victory(Player player) {}

    @Override
    public Position getPosition() {
        return this.getPosition();
    }


}
