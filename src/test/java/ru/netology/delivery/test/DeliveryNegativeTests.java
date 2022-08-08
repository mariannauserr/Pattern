package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
public class DeliveryNegativeTests {
    @BeforeEach
    void form() {
        open("http://localhost:9999");
        int daysToAddForFirstMeeting = 4;
        var borderDays = DataGenerator.getBorderDays(3);
        var daysWeek = DataGenerator.getWeekDays(7);
        $("[data-test-id=date] .input__icon").click();
        if (borderDays.getMonthValue() != daysWeek.getMonthValue()) {
            $("[data-step='1']").click();
        }
        $$("tr td").findBy(text(String.valueOf(LocalDate.now().plusDays(daysToAddForFirstMeeting).getDayOfMonth()))).click();
        $("[data-test-id=agreement]").click();
    }

    @Test
    void rePlainMeetingOnTheSameDay() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(3));
        $("[data-test-id=replan-notification] [class='notification__title']").shouldNotHave(exactText("Необходимо подтверждение"));
    }

    @Test
    void emptyCityBox() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptyNameBox() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptyPhoneBox() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void emptyDateBox() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        DeliveryTest.clearDate();
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Неверно введена дата"));
    }
    @Test
    void invalidCityBox() {
        var validUser = DataGenerator.Registration.generateUser("en");
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone("ru"));
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }
    @Test
    void invalidNameBox() {
        var validUser = DataGenerator.Registration.generateUser("en");
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone("ru"));
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void invalidNameBoxV2() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var invalidUser = DataGenerator.Registration.generateInvalidUser();
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(invalidUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаны неверно"));
    }

    @Test
    void invalidPhoneBox() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var invalidUser = DataGenerator.Registration.generateInvalidUser();
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(invalidUser.getPhone());
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Номер телефона указан неверно"));
    }


    @Test
    void noClickCheckBox() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id=agreement].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}
