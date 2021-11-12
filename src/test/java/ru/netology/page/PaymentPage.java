package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.DELETE;

public class PaymentPage {
    private static SelenideElement confirmButtonField = $(withText("Продолжить"));
    private static SelenideElement cardNumberField = $("[class=input__control]");
    private static SelenideElement monthField = $(withText("Месяц")).parent().$(".input__control");
    private static SelenideElement yearField = $(withText("Год")).parent().$(".input__control");
    private static SelenideElement cardHolderField = $(withText("Владелец")).parent().$(".input__control");
    private static SelenideElement codeField = $(withText("CVC/CVV")).parent().$(".input__control");

    private static SelenideElement heading = $(withText("Оплата по карте"));
    private static SelenideElement cardNumberErrorField = $(withText("Номер карты")).parent().$("[class=input__sub]");
    private static SelenideElement monthErrorField = $(withText("Месяц")).parent().$("[class=input__sub]");
    private static SelenideElement yearErrorField = $(withText("Год")).parent().$("[class=input__sub]");
    private static SelenideElement cvcErrorField = $(withText("CVC/CVV")).parent().$("[class=input__sub]");
    private static SelenideElement cardHolderErrorField = $(withText("Владелец")).parent().$("[class=input__sub]");


    public PaymentPage() {
        heading.shouldBe(Condition.visible);
    }

    public void confirmButtonClick() {
        confirmButtonField.click();
    }

    public void setCardNumber(String number) {
        cardNumberField.setValue(number);
    }

    public void setMonth(String cardMonth) {
        monthField.doubleClick().sendKeys(DELETE);
        monthField.setValue(cardMonth);
    }

    public void setYear(String cardYear) {
        yearField.setValue(cardYear);
    }

    public void setCardHolder(String owner) {
        cardHolderField.setValue(owner);
    }

    public void setCvc(String codeCvc) {
        codeField.setValue(codeCvc);
    }

    public void successMessage() {
        $("[class=notification__title]").shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Успешно"));
    }

    public void failedMessage() {
        $$("[class=notification__content]").get(1).shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }

    public void wrongFormatMessage() {
        $("[class=input__sub]").shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void emptyCardHolderFieldMessage() {
        cardHolderErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    public void failedCardHolderMessage() {
        cardHolderErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void fieldErrorWhenValueCannotBeLong() {
        cardHolderErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Поле может содержать не более 30 символов"));
    }

    public void failedCardNumberField() {
        cardNumberErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void failedCardMonthField() {
        monthErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void wrongCardMonthField() {
        monthErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void wrongCardYearField() {
        yearErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void failedCardYearField() {
        yearErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    public void emptyCardYearField() {
        yearErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void failedCardCvcField() {
        cvcErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }
}
