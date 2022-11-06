package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private static SelenideElement headingText = $(byText("Пополнение карты"));
    private static SelenideElement amount = $("[data-test-id=amount] .input__control");
    private static SelenideElement from = $("[data-test-id=from] .input__control");
    private static SelenideElement actionTransfer = $("[data-test-id=action-transfer]");
    private static SelenideElement actionCancel = $("[data-test-id=action-cancel]");


    public TransferPage() {
        headingText.shouldBe(visible);
    }

    public static DashboardPage makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amount.setValue(amountToTransfer);
        from.setValue(cardInfo.getCardNumber());
        actionTransfer.click();
        return new DashboardPage();
    }
}
