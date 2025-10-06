package Lab_1;

import java.util.Scanner;

public class Hero {
    public static void main(String[] args) {
        Context context = new Context();
        int movementNumber = 1;
        context.setStrategy(new ConcreteStrategyStand());
        System.out.println(context.moveStrategy());
        Scanner input = new Scanner(System.in);

        while (movementNumber != 0) {

            System.out.println("Input 1 to run, 2 to swim, 3 to walk and 0 to exit: ");
            if (input.hasNextInt()) {
                movementNumber = input.nextInt();
                switch (movementNumber) {
                    case 0 -> context.setStrategy(new ConcreteStrategyStand());
                    case 1 -> context.setStrategy(new ConcreteStrategyRun());
                    case 2 -> context.setStrategy(new ConcreteStrategySwim());
                    case 3 -> context.setStrategy(new ConcreteStrategyWalk());
                    default -> System.out.println("Not right command");
                }
                System.out.println(context.moveStrategy());
            } else {
                System.out.println("Not right command");
            }
        }
        input.close();
    }
}