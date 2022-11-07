package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyToCard1() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.selectFirstCardToTransfer();
        var dashboardPageNew = transferPage.makeValidTransfer(String.valueOf(secondCardBalance), secondCardInfo);

        var expectedSumOnFirstCard = firstCardBalance + secondCardBalance;
        var expectedSumOnSecondCard = secondCardBalance - secondCardBalance;
        var actualSumOnFirstCard = dashboardPage.getFirstCardBalance();
        var actualSumOnSecondCard = dashboardPage.getSecondCardBalance();

        assertEquals(expectedSumOnFirstCard, actualSumOnFirstCard);
        assertEquals(expectedSumOnSecondCard, actualSumOnSecondCard);
    }

    @Test
    void shouldTransferMoneyToCard2() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.selectSecondCardToTransfer();
        var dashboardPageNew = transferPage.makeValidTransfer(String.valueOf(firstCardBalance), firstCardInfo);

        var expectedSumOnFirstCard = firstCardBalance - firstCardBalance;
        var expectedSumOnSecondCard = secondCardBalance + firstCardBalance;
        var actualSumOnFirstCard = dashboardPage.getFirstCardBalance();
        var actualSumOnSecondCard = dashboardPage.getSecondCardBalance();

        assertEquals(expectedSumOnFirstCard, actualSumOnFirstCard);
        assertEquals(expectedSumOnSecondCard, actualSumOnSecondCard);
    }

    @Test
    void shouldTryNotToTransferMoreThanAvailableSum() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.selectFirstCardToTransfer();
        var dashboardPageNew = transferPage.makeTransferWithInvalidSum(String.valueOf(secondCardBalance + 1), secondCardInfo);

        var actualSumOnFirstCard = dashboardPage.getFirstCardBalance();
        var actualSumOnSecondCard = dashboardPage.getSecondCardBalance();

        assertEquals(firstCardBalance, actualSumOnFirstCard);
        assertEquals(secondCardBalance, actualSumOnSecondCard);
    }
}

