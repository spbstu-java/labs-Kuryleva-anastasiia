package Lab_1;

public class Context {
    private Strategy strategy;

    public Context(){

    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public String moveStrategy(){
        return strategy.move();
    }
}
