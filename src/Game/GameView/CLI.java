package Game.GameView;

import Game.GameView.Units.Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI implements MessageCallback   {

        @Override
        public String abilityCast(String message) {
            System.out.println(message);
            return message;

        }

        @Override
        public String onDeath(String name) {
            String message = name + " died! Game over :(";
            System.out.println(message);
            return message;
        }

    @Override
    public void combat(String s) {
        System.out.println(s);
    }


    public String battleResult(String message) {
            System.out.println(message);
            return message;
        }

        public String gameTickDesc(String message) {
            System.out.println(message);
            return message;
        }

        public String levelUp(String s){
            String message = s+ " Whoray! :D";
            System.out.println(message);
            return message;
        }

        public void gameStartOutPut(){
            System.out.println("Select player:\n" +
                    "1. Jon Snow             Health: 300/300         Attack: 30              Defense: 4              Level: 1               Experience: 0/50         Cooldown: 0/3\n" +
                    "2. The Hound            Health: 400/400         Attack: 20              Defense: 6              Level: 1               Experience: 0/50         Cooldown: 0/5\n" +
                    "3. Melisandre           Health: 100/100         Attack: 5               Defense: 1              Level: 1               Experience: 0/50         Mana: 75/300            Spell Power: 15\n" +
                    "4. Thoros of Myr                Health: 250/250         Attack: 25              Defense: 4              Level: 1       Experience: 0/50         Mana: 37/150            Spell Power: 20\n" +
                    "5. Arya Stark           Health: 150/150         Attack: 40              Defense: 2              Level: 1               Experience: 0/50         Energy: 100/100\n" +
                    "6. Bronn                Health: 250/250         Attack: 35              Defense: 3              Level: 1               Experience: 0/50         Energy: 100/100\n" +
                    //"7. Ygritte              Health: 220/220         Attack: 30              Defense: 2              Level: 1               Experience: 0/50         Arrows: 10              Range: 6\n" +
                    "\n");
        }

        @Override
        public void printBoard(String board) {
            System.out.println(board+"\n");
        }

        @Override
        public void choosePlayerSelection(String s) {
            System.out.println(s);
        }

        public String playerMoveSelection(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose action");
            String playerSelection = scanner.nextLine();
            List<String> validMoves = new ArrayList<>();
            validMoves.add("w"); validMoves.add("s"); validMoves.add("d"); validMoves.add("a");
            validMoves.add("q"); validMoves.add("e");
            while (!validMoves.contains(playerSelection)){
                System.out.println("Not a valid input. Try again (a/w/d/s/e/q)");
                playerSelection = scanner.nextLine();
            }
            return playerSelection;
        }

    @Override
    public void describe(String s) {
        System.out.println(s);
    }

    public String readerInput(){
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }

    }



