package Game.GameView.Units;

import Game.GameView.BoardPackaeg.Empty;
import Game.GameView.BoardPackaeg.Position;
import Game.GameView.BoardPackaeg.Tile;
import Game.GameView.MessageCallback;
import Game.GameView.BoardPackaeg.Wall;
import Game.GameView.MessagesPrinter;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;

import java.util.Random;

public abstract class Units extends Tile {
    protected String Name;
    protected Health health;
    protected int attackPoints;
    protected int defensePoints;


    protected char tile;
    protected String name;
    protected Health healthState;
    protected int attack;
    protected int defense;

    protected MessageCallback messageCallBack;

    protected Units(char tile, String name, int healthPool, int attack, int defense) {
        super(tile);
        this.defense = defense;
        this. tile = tile;
        this.name = name;
        this.healthState = new Health(healthPool,healthPool);
        this.attack = attack;
        this.messageCallBack = new MessagesPrinter();

       ///
    }

    public void interact(Tile tile){
        tile.accept(this);
    }


    protected void initialize(char tile, int[] position, MessageCallback messageCallback){
        this.messageCallBack = messageCallback;

    }

    public Position getPosition(){
        return this.position;
    }

    public void accept(Tile tile){
        tile.accept(this);
    }
    public void accept(Empty empty ){
        empty.accept(this);
    }
    public void accept(Enemy enemy){}
    public void accept(Player player){}
    public void accept(Wall wall){}

    public int defend(){
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth(){
        return healthState.healthAmount;
    };
    public void setHealth(int i) {this.health.setHealthAmount(i);}

    public int getAttack(){
        return attack;
    }

    public int getDefense() {
        return defense;
    }


    public void visit(Empty e){
        e.accept(this);
    }

    public abstract void visit(Player p);
    public abstract void visit(Enemy e);
    public void visit(Wall w){};


    public void combat(Units unit) {
        int rollAttacker = new Random().nextInt(0, this.attackPoints);
        int rollDefender = new Random().nextInt(0, unit.getDefense());
        if ((rollAttacker - rollDefender)>0){
            unit.setHealth(unit.getHealth()- (rollAttacker-rollDefender));
            if (unit.getHealth() <= 0){
                unit.victory(this);
            }
        }
    }

    public abstract void victory(Units unit);
    public abstract void victory(Player player);
    public abstract void victory(Enemy enemy);



    public Position moveLeft(){
        return new Position(new int[]{this.position.getPosition()[0] + 1, this.position.getPosition()[1]});

    }

    public Position moveUp(){
        return new Position(new int[]{this.position.getPosition()[0] , this.position.getPosition()[1] - 1});
    }

    public Position moveDown(){
        return new Position(new int[]{this.position.getPosition()[0], this.position.getPosition()[1]+1});
    }

    public Position moveRight(){
        return new Position(new int[]{this.position.getPosition()[0] - 1, this.position.getPosition()[1]});

    }
    public Position doNothing(){
        return this.position;
    }

    public int getAttackPoints(){
        return this.attackPoints;
    }
    public int getDefensePoints(){
        return this.defensePoints;
    }
    public void setAttackPoints(int i){
        this.attackPoints=i;
    }
    public void setDefensePoints(int i){
        this.defensePoints=i;
    }


}
