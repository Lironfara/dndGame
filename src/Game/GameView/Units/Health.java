package Game.GameView.Units;
public class Health {
    protected int healthPool;
    protected int healthAmount;
    public Health(int healthPool, int healthAmount){
        this.healthAmount= healthAmount;
        this.healthPool= healthPool;
    }
    public int getHealthPool(){
        return healthPool;
    }
    public int getHealthAmount(){
        return healthAmount;
    }
    public void setHealthPool(int newHealthPool){
        healthPool= newHealthPool;
    }
    public void setHealthAmount(int newHealthAmount){
        if(newHealthAmount> healthPool){
            this.healthAmount = healthPool;
        }
        else {
            healthAmount = newHealthAmount;
        }
    }

}
