package ru.netology.test;

import com.codeborne.selenide.Selenide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {


    @BeforeEach
    void setup() {
        var openPage = open("http://localhost:9999", LoginPage.class);
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    void shouldTransferMoneyToCard1() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = VerificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var firstCardBalance = DashboardPage.getCardBalance(firstCardInfo.getCardID());
        var secondCardBalance = DashboardPage.getCardBalance(secondCardInfo.getCardID());
        var transferPage = DashboardPage.selectFirstCardToTransfer();
        var dashboardPageNew = TransferPage.makeTransfer(String.valueOf(secondCardBalance), secondCardInfo);

        var expectedSumOnFirstCard = firstCardBalance + secondCardBalance;
        var expectedSumOnSecondCard = secondCardBalance - secondCardBalance;
        var actualSumOnFirstCard = DashboardPage.getCardBalance(firstCardInfo.getCardID());
        var actualSumOnSecondCard = DashboardPage.getCardBalance(secondCardInfo.getCardID());

        assertEquals(expectedSumOnFirstCard, actualSumOnFirstCard);
        assertEquals(expectedSumOnSecondCard, actualSumOnSecondCard);
    }

    @Test
    void shouldTransferMoneyToCard2() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = VerificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var firstCardBalance = DashboardPage.getCardBalance(firstCardInfo.getCardID());
        var secondCardBalance = DashboardPage.getCardBalance(secondCardInfo.getCardID());
        var transferPage = DashboardPage.selectSecondCardToTransfer();
        var dashboardPageNew = TransferPage.makeTransfer(String.valueOf(firstCardBalance), firstCardInfo);

        var expectedSumOnFirstCard = firstCardBalance - firstCardBalance;
        var expectedSumOnSecondCard = secondCardBalance + firstCardBalance;
        var actualSumOnFirstCard = DashboardPage.getCardBalance(firstCardInfo.getCardID());
        var actualSumOnSecondCard = DashboardPage.getCardBalance(secondCardInfo.getCardID());

        assertEquals(expectedSumOnFirstCard, actualSumOnFirstCard);
        assertEquals(expectedSumOnSecondCard, actualSumOnSecondCard);
    }
}

