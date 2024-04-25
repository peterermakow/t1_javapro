package ru.paermakov;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.paermakov.entity.User;
import ru.paermakov.service.UserService;

@ComponentScan
public class Main {
    public static void main(String[] args)  {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        UserService userService = context.getBean(UserService.class);

        // получаем всех пользователей
        userService.getAllUsers().forEach(System.out::println);

        // получаем пользователя по id
        User user = userService.getUserById(2L);
        System.out.println(user.toString());

        // сохраняем юзера
        userService.saveUser(new User(5L, "Anonymous user"));

        // удаляем юзера
        userService.deleteUserById(5L);
    }
}