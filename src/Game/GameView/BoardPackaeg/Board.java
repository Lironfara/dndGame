package Game.GameView.BoardPackaeg;

import Game.GameView.MessageCallback;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Players.Warrior;
import Game.GameView.Units.Units;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private List<Tile> tiles;
    private TileFactory tileFactory = new TileFactory();
    private Tile[][] map ;
    private List<Enemy> listOfEnemiesInBoard = new ArrayList<>();
    private List<Units> listOfUnitsInBoard = new ArrayList<>();
    private int numberOfEnemies;
    private String boardOutput;
    private int height;
    private int width;
    private List<List<String>> boardMapForToString;
    private Player player;

    public Board(List<List<String>> cuurentLevel, Player player) {
        if (!(this.map == null)) {
            map = null;
            tiles = new ArrayList<>();
        }
        this.player = player;
        this.boardMapForToString = cuurentLevel;
        this.height = cuurentLevel.get(0).size();
        this.width = cuurentLevel.get(0).get(0).length();
        map = new Tile[width][height];
        for (int i = 0; i < height; i++) {
            String line = cuurentLevel.get(0).get(i);
            for (int j = 0; j < width; j++) {
                Position position = new Position(new int[]{j, i});
                Tile t = tileFactory.generate(line.charAt(j), position);
                if (line.charAt(j)=='@'){
                    this.player.setPosition(position);
                    map[j][i] = player;
                }
                else{
                    map[j][i] = t;
                }
            }
        }
    }

    public void gameTick(String movement){
        Position p = player.gameTick(movement);
        player.interact(map[p.getPosition()[0]][p.getPosition()[1]]);
        for (Enemy enemy: getEnemies()) {
            if (!enemy.isDead()){
                Position p1 = enemy.gameTick();
            }

        }
    }

    public Board gameTick(Player player, String movement){
        Position prevPosition = player.getPosition();
        Position newPosition = player.gameTick(movement);
        player.interact(map[newPosition.getPosition()[0]][newPosition.getPosition()[1]]);
        Tile t = map[prevPosition.getPosition()[0]][prevPosition.getPosition()[1]];
        map[newPosition.getPosition()[0]][newPosition.getPosition()[1]] = player;
        map[prevPosition.getPosition()[0]][prevPosition.getPosition()[1]] = t;

        List<Enemy> enemies = getEnemies();
        for (Enemy enemy: enemies) {
            if (!enemy.isDead()){
                Position prev = enemy.getPosition();
                Position newEnemyPosition = enemy.gameTick();
                Tile toInter = map[newEnemyPosition.getPosition()[0]][newEnemyPosition.getPosition()[1]];
                enemy.interact(toInter);
                map[newEnemyPosition.getPosition()[0]][newEnemyPosition.getPosition()[1]] = enemy;
                map[prev.getPosition()[0]][prev.getPosition()[1]] = toInter;
            }
        }
        return null;


    }



    public List<Enemy> getEnemies(){
        List<Enemy> enemies = new ArrayList<>();
        for (Tile[] tileline: map) {
            for(Tile tile: tileline){
                if (tile.getTile()!='.' && tile.getTile()!='#' && tile.getTile()!='@'){
                    enemies.add(tileFactory.produceEnemy(tile.getTile(), tile.getPosition()));
                }
            }
        };
        this.numberOfEnemies = enemies.size();
        return enemies;
    }

    public int getNumberOfEnemies(){
        return this.numberOfEnemies;
    }


    public Tile get(int x, int y)  {
        Position position = new Position(new int[]{x,y});
        Tile tile= null;
        tile = map[x][y];
        return tile;
    }




    public void remove(Enemy e) {
        tiles.remove(e);
        Position p = e.getPosition();
        tiles.add(new Empty(p));
    }
    public void remove(Units unit){
        tiles.remove(unit);
        Position p = unit.getPosition();
        tiles.add(new Empty(p));
    }

    public void replace(Tile tile1, Tile tile2) {
        Position pos= tile1.getPosition();
        Position pos2 = tile2.getPosition();
        map[pos.getPosition()[0]][pos.getPosition()[1]] = tile2;
        tile2.initialize(pos);
        map[pos2.getPosition()[0]][pos2.getPosition()[1]] = tile1;
        tile1.initialize(pos2);
    }

    @Override
    public String toString() {
        String board = "";
        List<Character> lines = new ArrayList<>();
        for (Tile[] tile: map) {
            for (Tile tile1: tile) {
                board= board+tile1.getTile();
            }
            board+="\n";

        }
      /*  for (int i=0; i<lines.size(); i++){
            board = board+lines.get(i);
            if (i!=0 && i%49==48){
                if (i!=(lines.size()-1)){
                    board = board+"\n";
                }
            }
        }
       */
        return board;
    }
}
