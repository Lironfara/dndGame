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
    private Map<Position, Tile> map = new HashMap<>();
    private Map<Position, Units> mapOfUnits = new TreeMap<>();
    private List<Units> listOfUnitsInBoard = new ArrayList<>();
    private Position playerPosition ;

    public Board(List<List<String>> cuurentLevel, Player player) {
        if (!(this.map == null)) {
            map = new TreeMap<>();
            tiles = new ArrayList<>();
        }
        for (int height = 0; height < cuurentLevel.get(0).size(); height++) {
            String line = cuurentLevel.get(0).get(height);
            for (int width = 0; width < line.length(); width++) {
                Position position = new Position(new int[]{width, height});
                Tile t = tileFactory.generate(line.charAt(width), position);
                if (line.charAt(width)=='@'){
                    map.put(position, player);
                }
                else{
                    map.put(position, t);
                }
            }
        }
    }


    public List<Enemy> getEnemies(){
        List<Enemy> enemies = new ArrayList<>();
        System.out.println(map.values());
        for (Tile tile: map.values()) {
            if (tile.getTile()!='.' && tile.getTile()!='#' && tile.getTile()!='@'){
                enemies.add(tileFactory.produceEnemy(tile.getTile(), tile.getPosition()));
            }
        };
        return enemies;
    }


    public Tile get(int x, int y)  {
        Position position = new Position(new int[]{x,y});
        Tile tile= null;
        tile = map.get(position);
        return tile;
    }

    public void gameTick(){
        for (Units unit : listOfUnitsInBoard) {
            //gameTick(unit);
        }
    }

    //public void gameTick(Player p);

    public void updateBoard(){};

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

    public void replace(Units units, Empty empty) {
        Position emptyPos = empty.getPosition();
        Position unitsPosition = units.getPosition();
        Tile tile1 = units;
        tile1.setPosition(emptyPos);
        Tile tile2 = empty;
        tile2.setPosition(emptyPos);
        int newUnit= tiles.indexOf(empty);
        int newEmpty =tiles.indexOf(units);
        tiles.remove(empty);
        tiles.remove(units);
        tiles.add(newUnit,tile1);
        tiles.add(newEmpty, tile2);
    }

    @Override
    public String toString() {
        String board = "";
        tiles = tiles.stream().sorted().collect(Collectors.toList());
        int i = 0;
        for (Tile tile: tiles) {
            if(tile.getPosition().getPosition()[1] == i){
                board = board +tile.getTile();
            }
            else {
                board = board + "\n" + tile.getTile();
                i++;
            }
        }
        return board;
    }
}
