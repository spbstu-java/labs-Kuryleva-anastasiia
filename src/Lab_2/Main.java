package Lab_2;

public class Main {
    public static void main(String[] args) {
        MyAnnotatedClass myObject = new MyAnnotatedClass();
        MethodCaller caller = new MethodCaller();

        caller.callAnnotatedMethods(myObject);
    }
}
