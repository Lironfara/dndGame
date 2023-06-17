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
    private Map<Position, Tile> map = new TreeMap<>();
    private List<Units> listOfUnitsInBoard = new ArrayList<>();
    private Position playerPosition ;

    public void createBoard(File file, int idx){ //idx - the selected number of player
        if (!(this.map==null)){
            map = new TreeMap<>();
            tiles= new ArrayList<>();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int i=0;
            while((line=reader.readLine())!=null){
                for(int x=0; x<line.length(); x++){
                    Position position = new Position(new int[]{x,i});
                    Tile t = tileFactory.produceTile(line.charAt(x), position, idx);
                    if(line.charAt(x)== '@') {
                        playerPosition = position;
                    }
                    map.put(position,t);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Tile get(int x, int y)  {
        Position position = new Position(new int[]{x,y});
        Tile tile= null;
        tile = map.get(position);
        return tile;
    }

    public void gameTick(){
        for (Units unit : listOfUnitsInBoard) {
            gameTick(unit);
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
