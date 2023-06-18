package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.MessageCallback;
import Game.GameView.MessagesPrinter;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Units;

public abstract class Player extends Units {
    protected int experience;
    protected int playerLevel;

    protected MessageCallback messageCallBack;

    public Position position;
    private boolean dead;

    public Player(char c, String name, int health, int attackPoints, int defensePoints){
        super('@', name,health, attackPoints, defensePoints);
        this.experience=0;
        this.playerLevel=1;
        dead = false;
        this.messageCallBack = new MessagesPrinter();
    }

    public boolean isDead(){
        return dead;
    }


    //Imppossible sitiation
    @Override
    public void visit(Player p) {}

    @Override
    public void visit(Enemy e) {
        e.accept(this);
    }

    public void levelUp(){
        this.experience= experience- 50*playerLevel;
        this.playerLevel++;
        this.health.setHealthPool(health.getHealthPool()+(10*playerLevel));
        this.health.setHealthAmount(health.getHealthPool());
        this.attackPoints =attackPoints+ 4*playerLevel;
        this.defensePoints =defensePoints+ playerLevel;
        messageCallBack.levelUp(this.name, this.playerLevel);
    }

    public Position gameTick(String movment){
        Position newPosition =  move(movment);
        if (experience >= 50*playerLevel){
            levelUp();
        }
        describe();
        return newPosition;
    }

    public String describe() {
        String s = String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth(), getAttack(), getDefense());
        return messageCallBack.gameTickDesc(s);
    }

    @Override
    public void initialize(Position position) {
        super.initialize(position);
        this.position = position;
    }

    public void interact(Tile tile) {
        this.accept(tile);
    };
    public void accept(Empty empty){
        Position temp = empty.getPosition();
        empty.setPosition(this.position);
        this.position = temp;

    }

    public void abilityCastMessage(String m){};
    public abstract void abilityCast();

    public void onDeath(){

        this.tile = 'X';
        messageCallBack.onDeath(this.Name);
    };

    public void accept(Player player) {
    }
    public void accept(Units unit){
        unit.accept(this);
    }
    public void accept(Enemy enemy) {
        this.combat(enemy);
    }

    public void victory(Units unit){
        unit.victory(this);
    }

    public void victory(Enemy enemy){
        setExperience(this.experience+enemy.getExperienceValue());
        enemy.remove();
        this.setPosition(enemy.getPosition());
        messageCallBack.combatResult(this.name + " won the combat agains "+ enemy.getName() + " and gained " +enemy.getExperienceValue() +" experince points");
    }

    public void victory(Player player){}
    public void setExperience(int newex){
        this.experience = newex;
    }



    @Override
    public void visit(Wall w) {
        visit(w);
    }

    @Override
    public void visit(Empty e) {
        e.accept(this);
    }

    public String getName(){
        return this.name;
    }


    @Override
    public Position getPosition() {
        return super.getPosition();
    }




    public Position move (String movement){
        if (movement.equals("a")){
            return moveLeft();
        }
        if (movement.equals("w")){
            return moveUp();
        }
        if (movement.equals("s")){
            return moveDown();
        }
        if (movement.equals("d")){
            return moveRight();
        }
        if (movement.equals("e")){
           this.abilityCast();
        }
        else{
            return doNothing();
        }
        return null;
    }
}
