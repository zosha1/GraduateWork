package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CreditPage {
    private SelenideElement confirmButton = $(withText("Продолжить"));
    private SelenideElement cardNumberField = $("[class=input__control]");
    private SelenideElement monthField = $$("[class=input__control]").get(1);
    private SelenideElement yearField = $$("[class=input__control]").get(2);
    private SelenideElement cardHolderField = $$("[class=input__control]").get(3);
    private SelenideElement codeField = $$("[class=input__control]").get(4);
    private SelenideElement heading = $(withText("Кредит по данным карты"));

    private SelenideElement cardNumberErrorField = $(withText("Номер карты")).parent().$("[class=input__sub]");
    private SelenideElement monthErrorField = $(withText("Месяц")).parent().$("[class=input__sub]");
    private SelenideElement yearErrorField = $(withText("Год")).parent().$("[class=input__sub]");
    private SelenideElement cvcErrorField = $(withText("CVC/CVV")).parent().$("[class=input__sub]");
    private SelenideElement cardHolderErrorField = $(withText("Владелец")).parent().$("[class=input__sub]");

    public CreditPage() {
        heading.shouldBe(Condition.visible);
    }

    public void confirmButtonClick() {
        confirmButton.click();
    }

    public void setCardNumberField(String number) {
        cardNumberField.setValue(number);
    }

    public void setMonth(String cardMonth) {
        monthField.doubleClick().sendKeys(Keys.DELETE);
        monthField.setValue(cardMonth);
    }

    public void setYearField(String cardYear) {
        yearField.setValue(cardYear);
    }

    public void setCardHolderField(String owner) {
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

    public void emptyCardHolderFieldMessage() {
        cardHolderErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    public void failedCreditCardHolderMessage() {
        cardHolderErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void fieldErrorWhenValueCannotBeLong() {
        cardHolderErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Поле может содержать не более 30 символов"));
    }

    public void failedCreditCardNumberField() {
        cardNumberErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void failedCreditCardMonthField() {
        monthErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void wrongCreditCardMonthField() {
        monthErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void wrongCreditCardYearField() {
        yearErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void failedCreditCardYearField() {
        yearErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    public void emptyCreditCardYearField() {
        yearErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void failedCreditCardCvcField() {
        cvcErrorField.shouldBe(Condition.visible,
                Duration.ofSeconds(14)).shouldHave(Condition.exactText("Неверный формат"));
    }
}