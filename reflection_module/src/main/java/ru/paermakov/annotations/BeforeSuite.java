package ru.paermakov.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * аннотация @BeforeSuite для статических методов. Аннотированный метод выполняется до "прогона" всех тестов
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeSuite {
}
