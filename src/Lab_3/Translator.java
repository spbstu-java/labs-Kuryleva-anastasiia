package Lab_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Translator {
    private final Map<String, String> dictionary;

    public Translator() {
        // Сортировка словаря по длинне строки и по алфовиту
        dictionary = new TreeMap<>(Comparator.comparing(String::length).reversed()
                .thenComparing(String::compareTo));
    }

    // Чтение словаря из файла
    public void loadDictionary(String filename) throws InvalidFileFormatException, FileReadException {
        dictionary.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Если строка пустая - пропускаю
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                // Если неверный формат строки - вывод ошибки
                if (parts.length != 2) {
                    throw new InvalidFileFormatException(
                            "Неверный формат строки " + lineNumber + ": " + line +
                                    ". Ожидается формат: слово | перевод"
                    );
                }

                //Привожу к нижнему регистру
                String word = parts[0].trim().toLowerCase();
                String translation = parts[1].trim();

                if (word.isEmpty() || translation.isEmpty()) {
                    throw new InvalidFileFormatException(
                            "Пустое слово или перевод в строке " + lineNumber + ": " + line
                    );
                }

                // Добавляю в словарь
                dictionary.put(word, translation);
            }
            // Проверка исключений
        } catch (FileNotFoundException e) {
            throw new FileReadException("Файл не найден: " + filename);
        } catch (SecurityException e) {
            throw new FileReadException("Нет доступа к файлу: " + filename);
        } catch (IOException e) {
            throw new FileReadException("Ошибка чтения файла: " + e.getMessage());
        }
    }

    // Перевод текста
    public String translate(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        // Добавляю пробелы вокруг знаков препинания
        String spacedText = addSpacesAroundPunctuation(text);
        String[] words = spacedText.split("\\s+");
        List<String> result = new ArrayList<>();
        List<String> textWords = Arrays.asList(spacedText.toLowerCase().split("\\s+"));

        int i = 0;
        while (i < words.length) {
            String translated = null;
            String originalWord = words[i];

            // Ищю самое длинное совпадение
            for (String dictWord : dictionary.keySet()) {

                if (startsWithIgnoreCase(textWords, i, dictWord)) {
                    translated = dictionary.get(dictWord);

                    // Пропускаю оставшиеся слова, которые входят в найденное выражение
                    int wordCount = dictWord.split("\\s+").length;
                    i += wordCount - 1;
                    break;
                }
            }

            if (translated != null) {
                result.add(translated); // Добавление перевода
            } else {
                result.add(originalWord); // Добавление оригинального слова
            }

            i++;
        }
        String translatedText = String.join(" ", result);

        // Убираю лишние пробелы вокруг знаков препинания
        return removeSpacesAroundPunctuation(translatedText);
    }

    // Добавляю пробелы вокруг знаков препинания
    private String addSpacesAroundPunctuation(String text) {
        return text.replaceAll("([.,!?;:)(\\[\\]{}«»\"'])", " $1 ").replaceAll("\\s+", " ");
    }

    // Убираю лишние пробелы вокруг знаков препинания
    private String removeSpacesAroundPunctuation(String text) {
        String result = text;
        result = result.replaceAll("\\s+([.,!?;:)(\\[\\]{}«»\"'])", "$1");
        result = result.replaceAll("([.,!?;:)(\\[\\]{}«»\"'])\\s+", "$1 ");
        return result.replaceAll("\\s+", " ").trim();
    }

    // Сравнение выражения из словаря и текста
    private boolean startsWithIgnoreCase(List<String> textWords, int startIndex, String prefix) {
        String[] prefixWords = prefix.split("\\s+");

        // Если выражение в словаре больше, чем слов в тексте
        if (startIndex + prefixWords.length > textWords.size()) {
            return false;
        }

        for (int j = 0; j < prefixWords.length; j++) {
            String textWord = textWords.get(startIndex + j).toLowerCase();
            String prefixWord = prefixWords[j];
            if (!textWord.equals(prefixWord)) {
                return false;
            }
        }

        return true;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Загрузка словаря
        try {
            loadDictionary("src/Lab_3/dictionary.txt");
            System.out.println("Словарь успешно загружен. Записей: " + dictionary.size());
        } catch (InvalidFileFormatException | FileReadException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("\nВведите текст для перевода (или 'выход' для завершения):");

        while (true) {
            System.out.print(":) ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("выход")) {
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            String translation = translate(input);
            System.out.println("Перевод: " + translation);
        }

        scanner.close();
    }
}

class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) {
        super(message);
    }
}

class FileReadException extends Exception {
    public FileReadException(String message) {
        super(message);
    }
}
