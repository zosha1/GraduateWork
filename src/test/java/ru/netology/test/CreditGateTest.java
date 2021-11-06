package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditGateTest {

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
    public void buyTestApproveCreditCard() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.successMessage();
        assertEquals("APPROVED", DbHelper.getStatusCredit());
    }

    @Test
    public void buyTestDeclineCard() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardHolderField(DataHelper.getDeclineCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(16).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardNumberField();
        assertEquals("DECLINED", DbHelper.getStatusCredit());
    }

    @Test
    public void sendEmptyForm() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardNumberField();
        creditPage.wrongCreditCardMonthField();
        creditPage.emptyCreditCardYearField();
        creditPage.failedCreditCardHolderMessage();
        creditPage.failedCreditCardCvcField();
    }

    @Test
    public void buyTestRandomCreditCardNumber() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getRandomCardNumber());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedMessage();
    }

    @Test
    public void buyTestFakeCreditCardNumber() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getFakeCardNumber());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardNumberField();
    }

    @Test
    public void buyWithoutCreditCardNumber() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardNumberField();
    }

    @Test
    public void buyWithoutMonth() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.wrongCreditCardMonthField();
    }

    @Test
    public void creditCardMonthNonExist() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth("13");
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardMonthField();
    }

    @Test
    public void creditCardZeroMonth() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth("00");
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardMonthField();
    }

    @Test
    public void creditCardMonthWithOneNumber() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth("8");
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.wrongCreditCardMonthField();
    }

    @Test
    public void creditCardLessCurrentYear() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(-13).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardYearField();
    }

    @Test
    public void creditCardMoreThanFiveYears() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(63).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.wrongCreditCardYearField();
    }

    @Test
    public void creditCardYearWithOneNumber() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField("7");
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.emptyCreditCardYearField();
    }

    @Test
    public void buyCyrillicCreditCardHolder() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField("Иван Иванов");
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardHolderMessage();
    }

    @Test
    public void buyNumberCreditCardHolder() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField("1111111 22222");
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardHolderMessage();
    }

    @Test
    public void buySymbolCreditCardHolder() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField("@@@ /////");
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardHolderMessage();
    }

    @Test
    public void buyWithoutCreditCardHolder() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.emptyCardHolderFieldMessage();
    }

    @Test
    public void buyLongCreditCardHolder() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField("AnnaMariaValentina PetrovaIvanovaSidorovaTaburetkina");
        creditPage.setCvc(DataHelper.getCvc());
        creditPage.confirmButtonClick();
        creditPage.fieldErrorWhenValueCannotBeLong();
    }

    @Test
    public void buyWithoutCreditCardCvc() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardCvcField();
    }

    @Test
    public void buyWrongCreditCardCvc() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc(DataHelper.getFakeCvc());
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardCvcField();
    }

    @Test
    public void buyZeroCreditCardCvc() {
        var creditPage = purchasePage.creditButtonClick();
        creditPage.setCardNumberField(DataHelper.getApproveCard());
        creditPage.setMonth(DataHelper.generateDate(1).getMonth());
        creditPage.setYearField(DataHelper.generateDate(13).getYear());
        creditPage.setCardHolderField(DataHelper.getCardHolder());
        creditPage.setCvc("000");
        creditPage.confirmButtonClick();
        creditPage.failedCreditCardCvcField();
    }
}
