package ru.paermakov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UtilityClass {

    // Реализуйте удаление из листа всех дубликатов
    public <T> ArrayList<T> deleteDuplicatesFromListAllType(List<T> initList) {
        return (ArrayList<T>) initList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    // Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
    public Integer findThirdBiggestInteger(List<Integer> ints) {
        return ints.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
    }

    // Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9,
    // в отличие от прошлой задачи здесь разные 10 считает за одно число)
    public Integer findThirdUniqueBiggestInteger(List<Integer> ints) {
        return ints.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
    }

    // Имеется список объектов типа Сотрудник (имя, возраст, должность),
    // необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
    public List<String> getNamesThreeEngineersAgeDesc(List<Employee> employees) {
        return employees.stream()
                .filter(e -> Objects.equals(e.getPosition(), Employee.Position.ENGINEER))
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .map(Employee::getName)
                .limit(3)
                .collect(Collectors.toList());
    }

    // Имеется список объектов типа Сотрудник (имя, возраст, должность),
    // посчитайте средний возраст сотрудников с должностью «Инженер»
    public double countAvgEngineerAge(List<Employee> employees) {
        return employees.stream()
                .filter(e -> Objects.equals(e.getPosition(), Employee.Position.ENGINEER))
                .mapToDouble(Employee::getAge)
                .average()
                .orElse(0.0);
    }

    // Найдите в списке слов самое длинное
    public String getLongestWordFromArray(String[] arr) {
        return Arrays.stream(arr)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }

    // Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
    // Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
    public Map<String, Long> getWordFrequencyDictionary(String str) {
        return Arrays.stream(str.split(" "))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    // Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
    // если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
    public void printWordsGroupByLengthAsc(List<String> words) {
        words.stream()
                .sorted(Comparator.comparing(Function.identity()))
                .sorted(Comparator.comparingInt(String::length))
                .forEach(System.out::println);
    }

    // Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом,
    // найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
    public String getLongestWordAnyIfMultiple(String[] arr) {
        return Objects.requireNonNull(Arrays.stream(arr)
                        .flatMap(s -> Arrays.stream(s.split(" ")))
                        .collect(Collectors.groupingBy(String::length))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByKey())
                        .map(Map.Entry::getValue)
                        .orElse(null))
                .stream()
                .findAny()
                .orElse(null);
    }
}