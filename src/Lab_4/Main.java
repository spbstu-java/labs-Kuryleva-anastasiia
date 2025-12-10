package Lab_4;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    // Метод, возвращающий среднее значение списка целых чисел
    public static OptionalDouble averageOfList(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
    }

    // Метод, приводящий все строки в списке в верхний регистр и добавляющий к ним префикс «_new_»
    public static List<String> addPrefixAndUpperCase(List<String> strings) {
        return strings.stream()
                .map(str -> "_new_" + str.toUpperCase())
                .collect(Collectors.toList());
    }

    // Метод, возвращающий список квадратов всех встречающихся только один раз элементов списка
    public static List<Integer> squaresOfUniqueElements(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(entry -> entry.getKey() * entry.getKey())
                .collect(Collectors.toList());
    }

    // Метод, принимающий на вход коллекцию и возвращающий ее последний элемент
    // или кидающий исключение, если коллекция пуста
    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Collection is empty"));
    }

    // Метод, принимающий на вход массив целых чисел, возвращающий сумму чётных чисел
    // или 0, если чётных чисел нет
    public static int sumOfEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    // Метод, преобразовывающий все строки в списке в Map, где первый символ – ключ, оставшиеся – значение
    public static Map<Character, String> mapFirstCharToRest(List<String> strings) {
        return strings.stream()
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toMap(
                        str -> str.charAt(0), // ключ - первый символ
                        str -> str.substring(1), // значение - остальная часть строки
                        (existing, replacement) -> existing // обработка дубликатов ключей
                ));
    }


    public static void main(String[] args) {

        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        averageOfList(numbers1).ifPresent(avg ->
                System.out.println("Среднее значение: " + avg));


        List<String> strings1 = Arrays.asList("apple", "banana", "cherry");
        System.out.println("С префиксом: " + addPrefixAndUpperCase(strings1));


        List<Integer> numbers2 = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        System.out.println("Квадраты уникальных: " + squaresOfUniqueElements(numbers2));


        List<String> strings2 = Arrays.asList("first", "second", "third");
        System.out.println("Последний элемент: " + getLastElement(strings2));


        int[] numbers3 = {1, 2, 3, 4, 5, 6};
        System.out.println("Сумма четных: " + sumOfEvenNumbers(numbers3));


        List<String> strings3 = Arrays.asList("apple", "banana", "apricot", "cherry");
        System.out.println("Map: " + mapFirstCharToRest(strings3));


        try {
            getLastElement(new ArrayList<>());
        } catch (NoSuchElementException e) {
            System.out.println("Исключение для пустой коллекции: " + e.getMessage());
        }
    }
}
