package ru.paermakov;

import ru.paermakov.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class TestRunner {

    public static void main(String[] args)  {
        runTests(MyTestClass.class);
    }

    public static int runTests(Class c)  {
        Object targetClass = null;

        try {
            targetClass = c.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // получаем все методы класса, в том числе привытные, затем отфильтровываем только аннотированные методы
        Method[] declaredMethods = c.getDeclaredMethods();
        List<Method> annotatedMethods = Arrays.stream(declaredMethods)
                .filter(dm -> dm.getDeclaredAnnotations().length > 0)
                .collect(Collectors.toList());

        // объявляем список, в который будут попадать методы, которые необходимо "прогнать"
        List<Method> methodsToInvoke = new ArrayList<>();

        // определяем статический метод, помеченный аннотацией @BeforeSuite. Осуществляем все необходимые проверки(такой метод в классе один и он статический). Кладем в methodsToInvoke если он не null
        Method beforeSuiteMethod = getStaticMethodAnnotatedWith(BeforeSuite.class, annotatedMethods);
        if (nonNull(beforeSuiteMethod)) {
            methodsToInvoke.add(beforeSuiteMethod);
        }

        // определяем методы, аннотированные как @BeforeTest, @AfterTest, @Test. Сортируем @Test-методы компаратором
        List<Method> beforeTestMethods = getMethodsWithAnnotation(annotatedMethods, BeforeTest.class);
        List<Method> afterTestMethods = getMethodsWithAnnotation(annotatedMethods, AfterTest.class);
        Comparator<Method> comparator = Comparator.comparing(m -> m.getAnnotation(Test.class).priority());
        List<Method> testMethods = getMethodsWithAnnotation(annotatedMethods, Test.class).stream()
                .sorted(comparator.reversed())
                .collect(Collectors.toList());

        // проверяем @Test-методы на диапазон 1-10 и что они не static. Кладем @BeforeTest-, @AfterTest-, @Test-методы в methodsToInvoke в необходимом порядке
        for (Method testMethod : testMethods) {
            if (!Modifier.isStatic(testMethod.getModifiers())) {
                if (testMethod.getAnnotation(Test.class).priority() < 1 || testMethod.getAnnotation(Test.class).priority() > 10) {
                    throw new AnnotationUsageException("set priority when using annotation @Test from 1 to 10. Program will terminate\n");
                }
            } else {
                throw new AnnotationUsageException("only non-static methods can be annotated with @Test. Program will terminate\n");
            }

            methodsToInvoke.addAll(beforeTestMethods);
            methodsToInvoke.add(testMethod);
            methodsToInvoke.addAll(afterTestMethods);
        }

        // определяем статический метод, помеченный аннотацией @AfterSuite. Осуществляем все необходимые проверки(такой метод в классе один и он статический). Кладем в methodsToInvoke если он не null
        Method afterSuiteMethod = getStaticMethodAnnotatedWith(AfterSuite.class, annotatedMethods);
        if (nonNull(afterSuiteMethod)) {
            methodsToInvoke.add(afterSuiteMethod);
        }

        // запускаем методы из methodsToInvoke
        for (Method method : methodsToInvoke) {
            method.setAccessible(true);
            try {
                method.invoke(targetClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        // возвращаем кол-во "прогнанных" методов
        return methodsToInvoke.size();
    }

    private static Method getStaticMethodAnnotatedWith(Class annotation, List<Method> annotatedMethods) {
        List<Method> methods = getMethodsWithAnnotation(annotatedMethods, annotation);
        if (methods.size() == 1) {
            Method method = methods.get(0);
            if (Modifier.isStatic(method.getModifiers())) {
                return method;
            } else {
                throw new AnnotationUsageException(String.format("only static methods can be annotated with annotation %s. Program will terminate\n", annotation.getName()));
            }
        } else if (methods.size() > 1) {
            throw new AnnotationUsageException(String.format("only one method in class can be annotated with annotation %s. Program will terminate\n", annotation.getName()));
        }
        return null;
    }

    private static List<Method> getMethodsWithAnnotation(List<Method> methods, Class annotation) {
        return methods.stream()
                .filter(m -> nonNull(m.getDeclaredAnnotation(annotation)))
                .collect(Collectors.toList());
    }
}
