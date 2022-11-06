package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class DashboardPage {
    private static ElementsCollection cards = $$(".list__item div");
    private static ElementsCollection button = $$("[data-test-id=action-deposit]");
    private static SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public static int getCardBalance(String id) {
        var text = cards.findBy(Condition.attribute("data-test-id")).getText();
        String[] subtext = text.split(":");
        String balance = subtext[1].substring(0, subtext[1].indexOf("р.")).trim();
        return Integer.parseInt(balance);
    }

    public static TransferPage selectFirstCardToTransfer() {
        button.first().click();
        return new TransferPage();
    }

    public static TransferPage selectSecondCardToTransfer() {
        button.last().click();
        return new TransferPage();
    }
}
