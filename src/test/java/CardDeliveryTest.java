import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setup(){
        open("http://localhost:9999/");
    }

DataGenerator generator = new DataGenerator();

    @Test
    void shouldBeBookingDelivery() {
        String date = generator.generateDate(7);

        $("[data-test-id=city] input").setValue("Омск");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Нестеров Александр");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content")
                .shouldHave(exactText("Встреча забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    @Test
    void shouldSelectCityFromList() {
        String date = generator.generateDate(7);
        String city = "Мо";

        $("[data-test-id=city] input").setValue(city);
        $$("[class=menu-item__control]").findBy(Condition.ownText(city)).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Нестеров Александр");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content")
                .shouldHave(exactText("Встреча забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(visible);
    }
    @Test
    void shouldSelectDateFromCalendar() {
        int days = 7;
        String date = generator.generateDate(days);
        String day = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd"));

        $("[data-test-id=city] input").setValue("Омск");
        $("[class=input__icon]").click();
        $$("[role=gridcell]").findBy(text(day)).click();
        $("[data-test-id=name] input").setValue("Нестеров Александр");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content")
                .shouldHave(exactText("Встреча забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(visible);
    }
}
