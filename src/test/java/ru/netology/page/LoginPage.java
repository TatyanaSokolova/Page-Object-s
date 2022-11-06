package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private static SelenideElement loginField = $("[data-test-id=login] .input__control");
    private static SelenideElement passwordField = $("[data-test-id=password] .input__control");
    private static SelenideElement loginButton = $("[data-test-id=action-login]");

    public static VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}
