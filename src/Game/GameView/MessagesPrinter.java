package Game.GameView;
    public class MessagesPrinter implements MessageCallback  {
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
        public String combatResult(String s) {
            return null; ///To complete
        }

        public String battleResult(String message) {
            System.out.println(message);
            return message;
        }

        public String gameTickDesc(String message) {
            System.out.println(message);
            return message;
        }

        public String levelUp(String name, int nextLevel){
            String message = name + " leveled up to level " + nextLevel+ "! Whoray! :D";
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
            System.out.println(board);
        }

        @Override
        public void choosePlayerSelection(String s) {
            System.out.println(s);
        }
    }


