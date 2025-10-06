package Lab_1;

public interface Strategy {
    String move();
}

class ConcreteStrategyRun implements Strategy{
    @Override
    public String move() {
        return "I'm running!";
    }
}

class ConcreteStrategySwim implements Strategy{
    @Override
    public String move() {
        return "I'm swimming!";
    }
}

class ConcreteStrategyWalk implements Strategy{
    @Override
    public String move() {
        return "I'm walking!";
    }
}

class ConcreteStrategyStand implements Strategy{
    @Override
    public String move() {
        return "I'm standing!";
    }
}

