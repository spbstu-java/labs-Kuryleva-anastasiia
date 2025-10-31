package Lab_2;

public class MyAnnotatedClass {
    // Публичные методы (без аннотаций)
    public void publicMethod1(String text) {
        System.out.println("Public метод 1: " + text);
    }

    public int publicMethod2(int a, int b) {
        int result = a + b;
        System.out.println("Public метод 2: " + a + " + " + b + " = " + result);
        return result;
    }

    // Защищенные методы с аннотациями
    @CallCounter(3)
    protected void protectedMethod1(String name, int age) {
        System.out.println("Protected метод 1: " + name + ", число: " + age);
    }

    @CallCounter(2)
    protected double protectedMethod2(double x, double y) {
        double result = x * y;
        System.out.println("Protected метод 2: " + x + " * " + y + " = " + result);
        return result;
    }

    // Приватные методы с аннотациями
    @CallCounter(4)
    private void privateMethod1(boolean flag, String message) {
        System.out.println("Private метод 1: " + message + ", flag = " + flag);
    }

    @CallCounter(1)
    private String privateMethod2(String a, String b, String c) {
        String result = a + " - " + b + " - " + c;
        System.out.println("Private метод 2: " + result);
        return result;
    }

    // Методы без аннотаций (не должны вызываться)
    protected void protectedMethod3() {
        System.out.println("Этот метод не должен отображаться");
    }

    private void privateMethod3() {
        System.out.println("Этот метод не должен отображаться");
    }
}
