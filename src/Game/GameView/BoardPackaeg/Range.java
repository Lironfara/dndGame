package Game.GameView.BoardPackaeg;

public class Range {

    protected int[] position1;
    protected int[] position2;

    protected double range;


    public Range(int[] position1, int[] position2){
        this.range =  Math.sqrt(Math.pow(position1[0]-position2[0], 2) - Math.pow(position1[1]-position2[1], 2)) ;
    }

    public double getRange(){
        return this.range;
    }


}
