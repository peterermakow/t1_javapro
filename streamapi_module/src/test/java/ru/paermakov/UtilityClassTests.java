package ru.paermakov;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class UtilityClassTests {

    static UtilityClass utilityClass;
    static List<Integer> ints;
    static List<String> uniqueStrings;
    static  List<Employee> employees;
    static String[] array;
    static String[] array2;
    static List<String> cities;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeAll
    static void setUp() {
        utilityClass = new UtilityClass();
        ints = new ArrayList<>(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13));
        uniqueStrings = new ArrayList<>(List.of("s1", "s2", "s3"));
        array = new String[]{"animal", "cat", "mouse"};

        employees = new ArrayList<>(List.of(
                new Employee("Беляш (МА-9)", 50, Employee.Position.ENGINEER),
                new Employee("Ибис", 27, Employee.Position.CLEANER),
                new Employee("Лаборант (ВОВ-А6)", 25, Employee.Position.LABORANT),
                new Employee("Пчела (ПЧ-9)", 38, Employee.Position.ENGINEER),
                new Employee("Инженер (РАФ-9)", 41, Employee.Position.ENGINEER),
                new Employee("Лаборант (ВОВ-А13)", 29, Employee.Position.ENGINEER),
                new Employee("Неизвестный робот", 20, Employee.Position.LABORANT)));

        array2 = new String[]{
                "bmw honda toyota daihatsu mercedes-benzzz",
                "vaz tesla yamaha yyyyyyyy harley-davidson",
                "uaz haval suzuki skodaaaa unknown"
        };

        cities = new ArrayList<>(List.of("Moscow", "Berlin", "London", "Rome", "Saint Petersburg", "New York", "Vancouver"));
    }

    @Test
    @Order(1)
    @DisplayName("Удаляем дубликаты из листа с целыми числами")
    void testDeleteDuplicatesFromListAllTypeWithIntegers() {
        List<Integer> uniqueIntegers = new ArrayList<>(List.of(5, 2, 10, 9, 4, 3, 1, 13));

        List<Integer> result = utilityClass.deleteDuplicatesFromListAllType(ints);
        assertEquals(uniqueIntegers, result);
    }

    @Test
    @Order(2)
    @DisplayName("Удаляем дубликаты из листа со строками")
    void testDeleteDuplicatesFromListAllTypeWithStrings() {
        List<String> list = new ArrayList<>(List.of("s1", "s1", "s2", "s3", "s3"));

        List<String> result = utilityClass.deleteDuplicatesFromListAllType(list);
        assertEquals(uniqueStrings, result);
    }

    @Test
    @Order(3)
    @DisplayName("Ищем в списке целых чисел 3-е наибольшее неуникальное число")
    void testFindThirdBiggestInteger() {
        assertEquals(10, utilityClass.findThirdBiggestInteger(ints));
    }

    @Test
    @Order(4)
    @DisplayName("Ищем в списке целых чисел 3-е наибольшее уникальное число")
    void testFindThirdUniqueBiggestInteger() {
        assertEquals(9, utilityClass.findThirdUniqueBiggestInteger(ints));
    }

    @Test
    @Order(5)
    @DisplayName("Проверяем получение списка имен 3 самых старших сотрудников с должностью «Инженер» в порядке убывания возраста")
    void testGetNamesThreeEngineersAgeDesc() {
        List<String> expectedNames = new ArrayList<>(List.of("Беляш (МА-9)", "Инженер (РАФ-9)", "Пчела (ПЧ-9)"));
        assertEquals(expectedNames, utilityClass.getNamesThreeEngineersAgeDesc(employees));
    }

    @Test
    @Order(6)
    @DisplayName("Считаем средний возраст сотрудников с должностью «Инженер»")
    void testСountAvgEngineerAge() {
        assertEquals(39.5d, utilityClass.countAvgEngineerAge(employees));
    }

    @Test
    @Order(7)
    @DisplayName("Проверяем правильность нахождения самого длинного слова")
    void testGetLongestWordFromArray() {
        assertEquals("animal", utilityClass.getLongestWordFromArray(array));
    }

    @Test
    @Order(8)
    @DisplayName("Проверяем соответсвие хеш-мап")
    void testGetWordFrequencyDictionary() {
        Map<String, Long> expectedMap = new HashMap<>();
        expectedMap.put("слово", 2L);
        expectedMap.put("ноутбук", 3L);
        expectedMap.put("магазин", 1L);
        expectedMap.put("автомобиль", 1L);
        expectedMap.put("электричество", 1L);

        assertEquals(expectedMap, utilityClass.getWordFrequencyDictionary("слово ноутбук слово магазин автомобиль электричество ноутбук ноутбук"));
    }

    @Test
    @Order(9)
    @DisplayName("Проверяем правильность печати слов в консоль в методе printWordsGroupByLengthAsc")
    void testPrintWordsGroupByLengthAsc() {
        System.setOut(new PrintStream(outputStreamCaptor));
        utilityClass.printWordsGroupByLengthAsc(cities);

        assertEquals(String.join(System.lineSeparator(),
                "Rome",
                "Berlin",
                "London",
                "Moscow",
                "New York",
                "Vancouver",
                "Saint Petersburg"
        ), outputStreamCaptor.toString()
                .trim());
        System.setOut(standardOut);
    }

    @Test
    @Order(10)
    @DisplayName("Проверяем правильность нахождения самых длинных слов из массива строк, в каждой из которых лежит 5 слов, разделенных пробелом")
    void  testGetLongestWordAnyIfMultiple() {
        String[] expectedTitles = {"mercedes-benzzz", "harley-davidson"};
        List<String> expectedTitlesList = Arrays.asList(expectedTitles);

        assertTrue(expectedTitlesList.contains(utilityClass.getLongestWordAnyIfMultiple(array2)));
    }
}
