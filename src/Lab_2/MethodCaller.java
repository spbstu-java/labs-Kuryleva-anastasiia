package Lab_2;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodCaller {
    public void callAnnotatedMethods(MyAnnotatedClass obj) {
        // Получаем все методы класса
        Method[] methods = MyAnnotatedClass.class.getDeclaredMethods();

        for (Method method : methods) {
            // Проверяем, есть ли аннотация @CallCounter

            if(!Modifier.isPublic(method.getModifiers())) {
                if (method.isAnnotationPresent(CallCounter.class)) {
                    // Получаем аннотацию
                    CallCounter annotation = method.getAnnotation(CallCounter.class);
                    int callCount = annotation.value();

                    // Делаем метод доступным (для private и protected)
                    method.setAccessible(true);

                    System.out.println("\n=== Метод: " + method.getName() +
                            ", количество вызовов: " + callCount + " ===");

                    // Вызываем метод указанное количество раз
                    for (int i = 1; i <= callCount; i++) {
                        try {
                            // Автоматически создаем параметры
                            Object[] params = generateParameters(method.getParameterTypes());

                            System.out.print("Вызов " + i + ": ");
                            Object result = method.invoke(obj, params);

                        } catch (Exception e) {
                            System.out.println("Ошибка при вызове: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    // Создает параметры для метода на основе типов
    private Object[] generateParameters(Class<?>[] paramTypes) {
        Object[] parameters = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            parameters[i] = getDefaultValue(paramTypes[i], i);
        }

        return parameters;
    }

    // Возвращает значения по умолчанию для разных типов
    private Object getDefaultValue(Class<?> type, int paramIndex) {
        if (type == int.class || type == Integer.class) {
            return 1 + paramIndex; // разные числа для разных параметров
        } else if (type == double.class || type == Double.class) {
            return 2.5 + paramIndex; // разные числа с плавающей точкой
        } else if (type == boolean.class || type == Boolean.class) {
            return paramIndex % 2 == 0; // чередующиеся true/false
        } else if (type == String.class) {
            return "просто строка " + (paramIndex + 1); // разные строки
        } else {
            return null; // для неизвестных типов
        }
    }
}
