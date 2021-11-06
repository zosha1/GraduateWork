package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Locale;

public class DataHelper {

    public static String getApproveCard() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclineCard() {
        return "4444 4444 4444 4442";
    }

    public static String getRandomCardNumber() {
        Faker faker = new Faker(new Locale("en"));
        return faker.business().creditCardNumber();
    }

    public static String getCardHolder() {
        Faker faker = new Faker(new Locale("en"));
        var firstname = faker.name().firstName();
        var lastname = faker.name().lastName();
        return firstname + " " + lastname;
    }

    public static String getCvc() {
        Faker faker = new Faker(new Locale("en"));
        return faker.numerify("###");
    }

    public static String getFakeCvc() {
        Faker faker = new Faker(new Locale("en"));
        return faker.numerify("##");
    }

    public static String getFakeCardNumber() {
        Faker faker = new Faker(new Locale("en"));
        return faker.internet().password(1, 15, true, true, true);
    }

    @Value
    public static class DateInfo {
        String month;
        String year;
    }

    public static DateInfo generateDate(int monthShift) {
        var cardDate = LocalDate.now().plusMonths(monthShift);
        return new DateInfo(cardDate.format(DateTimeFormatter.ofPattern("MM")),
                cardDate.format(DateTimeFormatter.ofPattern("yy")));
    }
}
