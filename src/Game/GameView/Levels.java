package Game.GameView;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class Levels {
    public List<List<String>> levels;
    public Levels(){
        levels = new ArrayList<>();
        String levelInput = "#################################################\n"+
                "#....s...###..........................#.........#\n" +
                "#........#B#....##..........##........#.........#\n" +
                "#........#......##..........##........#.........#\n" +
                "#........#............................#.........#\n" +
                "#........#............................#.........#\n" +
                "#........#......##..........##........#.........#\n" +
                "#........#......##s........k##........#.........#\n" +
                "#........#s.................##.......k#.........#\n" +
                "#@...........................Q.................q#\n" +
                "#........#s.................##.......k#.........#\n" +
                "#........#......##s........k##........#.........#\n" +
                "#........#......##..........##........#.........#\n" +
                "#........#............................#.........#\n" +
                "#........#............................#.........#\n" +
                "#........#......##..........##........#.........#\n" +
                "#........#B#....##..........##........#.........#\n" +
                "#....s...###..........................#.........#\n" +
                "#################################################";

        levels.add(Arrays.asList(levelInput.split("\n")));

        levelInput="#########################\n" +
                "#.........M.C...........#\n" +
                "#........qqqqqq.........#\n" +
                "#...###...........###...#\n" +
                "#....Q.............Q....#\n" +
                "#.......................#\n" +
                "#.......................#\n" +
                "#...###...........###...#\n" +
                "#...###...q...q...###...#\n" +
                "#........kk...kk........#\n" +
                "#.......................#\n" +
                "#.......................#\n" +
                "#.......................#\n" +
                "#..k##k...........k##k..#\n" +
                "#...##.............##...#\n" +
                "#..ssss...........ssss..#\n" +
                "#.......................#\n" +
                "#.......................#\n" +
                "#.......................#\n" +
                "#...........@...........#\n" +
                "#########################";

        levels.add(Arrays.asList(levelInput.split("\n")));

        levelInput="####################################################\n" +
                "#B........................g.......................B#\n" +
                "#..................................................#\n" +
                "#...####....................................####...#\n" +
                "#...####....................................####...#\n" +
                "#...####....................................####...#\n" +
                "#.....................z.......z....................#\n" +
                "#..................................................#\n" +
                "#..................................................#\n" +
                "#..................................................#\n" +
                "#............b............@............b...........#\n" +
                "#..................................................#\n" +
                "#..................................................#\n" +
                "#..................................................#\n" +
                "#.....................z.......z....................#\n" +
                "#..................................................#\n" +
                "#..................................................#\n" +
                "#..................................................#\n" +
                "#..................................................#\n" +
                "#.........................w........................#\n" +
                "####################################################";

        levels.add(Arrays.asList(levelInput.split("\n")));

        levelInput="#########################\n" +
                "#b..........@..........b#\n" +
                "#.......................#\n" +
                "#...###...........###...#\n" +
                "#.......................#\n" +
                "#...zzz...........zzz...#\n" +
                "#...###g...w.w...g###...#\n" +
                "#.......................#\n" +
                "#......D.........D......#\n" +
                "#..####...........####..#\n" +
                "#..z##z...........z##z..#\n" +
                "#..####...........####..#\n" +
                "#.......................#\n" +
                "########.........########\n" +
                "########.........########\n" +
                "########.ww.K.ww.########\n" +
                "#########################";

        levels.add(Arrays.asList(levelInput.split("\n")));

    }

    public List<List<String>> getLevels() {
        return levels;
    }
}
