package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.CLI;
import Game.GameView.MessageCallback;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mage extends Player {
    protected int manaPool;
    protected int currentMana;
    protected int manaCost;
    protected int spellPower;
    protected int hitsCount;
    protected int abilityRange;

    protected String specialAbilityName;

    private MessageCallback messageCallback;

    public Mage( String name, int health, int attackPoints, int defensePoints, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange){
        super('@', name,health,attackPoints,defensePoints);
        this.manaPool= manaPool;
        this.currentMana = manaPool/4;
        this.manaCost =manaCost;
        this.spellPower= spellPower;
        this.hitsCount =hitsCount;
        this.abilityRange= abilityRange;
        this.specialAbilityName = "Blizzard";
        this.messageCallback = new CLI();

    }

    @Override
    public void levelUp(){
        super.levelUp();
        manaPool= manaPool+ 25*playerLevel;
        setCurrentMana(currentMana+ manaPool/4);
        spellPower= spellPower+ 10*playerLevel;
        messageCallback.levelUp(this.name + " reached level "+ playerLevel+ ":" + "+"+ 25*playerLevel+ " Mana pool"
        +", +"+ manaPool/4+ "Mana " + ", +"+ 10*playerLevel+ " sepll power");
    }


    public void interact(Tile tile){
        this.accept(tile);
        setCurrentMana(manaPool+playerLevel);

    }

    public Position gameTick(String movement){
        setCurrentMana(currentMana+1*playerLevel);
        messageCallback.describe(describe());
        return super.gameTick(movement);
    }

    public String describe(){
        String s = getName() + "   Health :"+ getHealthPool()+"/"+getHealthAmount()+ "   Attack :"+getAttack() + "   Defense :"+getDefense()+ "   Experience : "+getExperience() + "   Mana : "+currentMana+"/"+manaPool+ "  Mana Cost: " +manaCost;
        return s;
    }

    @Override
    public List<Enemy> abilityCast(List<Enemy> enemiesOnBoard) {
        List<Enemy> enemiesToRemove = new ArrayList<>();
        if (currentMana > manaCost) {
            setCurrentMana(currentMana - manaCost);
            int hits = 0;
            ArrayList<Enemy> enemiesOnRange = new ArrayList<>();
            for (Enemy enemy : enemiesOnBoard) {
                if (new Range(this.getPosition().getPosition(), enemy.getPosition().getPosition()).getRange() < abilityRange && !enemy.isDead()) {
                    enemiesOnRange.add(enemy);
                }
            }
            while (hits < hitsCount && enemiesOnRange.size()>0) {
                Collections.shuffle(enemiesOnRange);
                Enemy toAttack = enemiesOnRange.get(0);
                int playerAttacker = new Random().nextInt(0, this.attackPoints);
                int rollDefender = new Random().nextInt(0,toAttack.getDefense());
                if (playerAttacker-rollDefender >0){
                    toAttack.setHealth(toAttack.getHealth() - spellPower);
                    if (toAttack.getHealth() <= 0) {
                        this.setExperience(toAttack.getExperienceValue() + experience);
                        toAttack.onDeath();
                        enemiesToRemove.add(toAttack);
                        enemiesOnRange.remove(toAttack);
                        super.setPosition(toAttack.getPosition());
                        this.setPosition(toAttack.getPosition());
                        messageCallback.abilityCast(name + " cast " + specialAbilityName + ",  and killed " + toAttack.getName() + "."
                                + name + " gained another " + toAttack.getExperienceValue() + " experience points");
                        enemiesOnRange.remove(toAttack);
                    }
                    else{
                        messageCallback.abilityCast(name + " cast " + specialAbilityName + "and damaged " + toAttack.getName());

                    }
                }
                hits++;
            }
        }
        else{
            messageCallback.abilityCast(this.getName() + " try to cast "+ specialAbilityName + " but had not enough resources");
        }
        return enemiesToRemove;

    }


    private void setCurrentMana(int newCurrentMana){
        if(newCurrentMana>manaPool)
            currentMana = manaPool;

        else{
            currentMana = newCurrentMana;
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


    public void accept(Empty empty) {}

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
