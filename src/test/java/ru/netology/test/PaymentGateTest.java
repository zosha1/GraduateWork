package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import io.qameta.allure.selenide.AllureSelenide;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentGateTest {

    PurchasePage purchasePage;

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        purchasePage = new PurchasePage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void buyTestApproveCard() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.successMessage();
        assertEquals("APPROVED", DbHelper.getStatusDebitCard());
    }

    @Test
    public void buyTestDeclineCard() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getDeclineCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(16).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardNumberField();
        assertEquals("DECLINED", DbHelper.getStatusCredit());
    }

    @Test
    public void sendEmptyForm() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.confirmButtonClick();
        paymentPage.wrongFormatMessage();
        paymentPage.wrongCardMonthField();
        paymentPage.emptyCardYearField();
        paymentPage.emptyCardHolderFieldMessage();
        paymentPage.failedCardCvcField();
    }

    @Test
    public void buyTestRandomCardNumber() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getRandomCardNumber());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedMessage();
    }

    @Test
    public void buyTestFakeCardNumber() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getFakeCardNumber());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.wrongFormatMessage();
    }

    @Test
    public void buyWithoutCardNumber() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.wrongFormatMessage();
    }

    @Test
    public void buyWithoutMonth() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());

        paymentPage.confirmButtonClick();
        paymentPage.wrongCardMonthField();
    }

    @Test
    public void cardMonthNonExist() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth("13");
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardMonthField();
    }

    @Test
    public void cardZeroMonth() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth("00");
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardMonthField();
    }

    @Test
    public void cardMonthWithOneNumber() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth("8");
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.wrongCardMonthField();
    }

    @Test
    public void cardLessCurrentYear() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(-13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardYearField();
    }

    @Test
    public void cardMoreThanFiveYears() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(63).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.wrongCardYearField();
    }

    @Test
    public void cardYearWithOneNumber() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear("2");
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.emptyCardYearField();
    }

    @Test
    public void buyCyrillicCardHolder() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder("Иван Иванов");
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardHolderMessage();
    }

    @Test
    public void buyNumberCardHolder() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder("1111111 22222");
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardHolderMessage();
    }

    @Test
    public void buySymbolCardHolder() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder("@@@ /////");
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardHolderMessage();
    }

    @Test
    public void buyWithoutCardHolder() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.emptyCardHolderFieldMessage();
    }

    @Test
    public void buyLongCardHolder() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder("AnnaMariaValentina PetrovaIvanovaSidorovaTaburetkina");
        paymentPage.setCvc(DataHelper.getCvc());
        paymentPage.confirmButtonClick();
        paymentPage.fieldErrorWhenValueCannotBeLong();
    }

    @Test
    public void buyWithoutCardCvc() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardCvcField();
    }

    @Test
    public void buyWrongCardCvc() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc(DataHelper.getFakeCvc());
        paymentPage.confirmButtonClick();
        paymentPage.failedCardCvcField();
    }

    @Test
    public void buyZeroCardCvc() {
        var paymentPage = purchasePage.paymentButtonClick();
        paymentPage.setCardNumber(DataHelper.getApproveCard());
        paymentPage.setMonth(DataHelper.generateDate(1).getMonth());
        paymentPage.setYear(DataHelper.generateDate(13).getYear());
        paymentPage.setCardHolder(DataHelper.getCardHolder());
        paymentPage.setCvc("000");
        paymentPage.confirmButtonClick();
        paymentPage.failedCardCvcField();
    }
}