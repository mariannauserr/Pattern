package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static LocalDate getBorderDays(int daysBorder) {
        return LocalDate.now().plusDays(daysBorder);
    }
    public static LocalDate getWeekDays(int days) {
        return LocalDate.now().plusDays(days);
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker((new Locale(locale)));
        return faker.address().cityName();
    }

    public static String generateFullName(String locale) {
        Faker faker = new Faker((new Locale(locale)));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generatedName(String locale) {
        Faker faker = new Faker((new Locale(locale)));
        return faker.name().firstName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static String generateInvalidPhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.regexify("123[9999-2-10]");
    }

    public static class Registration {

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(locale),
                    generateFullName(locale),
                    generatePhone(locale));
        }

        public static UserInfo generateCustomName(String name) {
            String city = generateCity("ru");
            String phone = generatePhone("ru");
            return new UserInfo(city, name, phone);
        }

        public static UserInfo generateInvalidUser() {
            String city = generateCity("en");
            String name = generatedName("ru");
            String phone = generateInvalidPhone("en");
            return new UserInfo(city, name, phone);
        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
}
