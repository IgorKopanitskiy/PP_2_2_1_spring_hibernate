package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        //Создание пользователей
        User user1 = new User("Kobe", "Bryant", "champion@nba.com");
        User user2 = new User("Tim", "Duncan", "fundamental@nba.com");
        User user3 = new User("Kevin", "Garnet", "ticket@nba.com");

        //Создание машин
        Car car1 = new Car("Toyota Land Cruiser", 300);
        Car car2 = new Car("Mercedes GLA", 200);
        Car car3 = new Car("Porsche", 911);

        //Назначение машин пользователям
        user1.setCar(car1);
        user2.setCar(car2);
        user3.setCar(car3);

        //Добавление пользователей в БД
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);

        //Проверка
        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        //Получение пользователя из БД по модели и серии машины
        User result = userService.getUserByCarModelAndSeries("Mercedes GLA", 200);
        System.out.println(result);

        context.close();
    }
}
