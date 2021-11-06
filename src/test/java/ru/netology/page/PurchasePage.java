package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class PurchasePage {
    private static SelenideElement paymentButton = $(withText("Купить"));
    private SelenideElement creditButton = $(withText("Купить в кредит"));
    private SelenideElement confirmButton = $(withText("Продолжить"));

    public PurchasePage() {
        paymentButton.shouldBe(Condition.visible);
        creditButton.shouldBe(Condition.visible);
        confirmButton.shouldBe(Condition.not(Condition.visible));
    }

    public PaymentPage paymentButtonClick() {
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage creditButtonClick() {
        creditButton.click();
        return new CreditPage();
    }
}