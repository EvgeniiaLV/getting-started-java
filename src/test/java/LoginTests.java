import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class LoginTests {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://school.qa.guru";
//        Configuration.browser = "firefox"; // mozilla
//        Configuration.browserVersion = "120.0";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "none";
    }
    

    @AfterEach
    void teardown() {
        closeWebDriver();
    }

    @Test
    void successfulLoginTest() {
        open("/");
        stopPageLoading();
        $("[name=email]").setValue("qagurubot@gmail.com");
        $("[name=password]").setValue("somepasshere").pressEnter();

        $(".page-header").shouldHave(text("Список тренингов"));

        open("/cms/system/login");
        $(".logined-form").shouldHave(text("QA_GURU_BOT"));
    }


    @Test
    void wrongPasswordTest() {
        open("/");
        stopPageLoading();
        $("[name=email]").setValue("qagurubot@gmail.com");
        $("[name=password]").setValue("some1a3241passhere").pressEnter();

        $(".btn-error").shouldHave(text("Неверный пароль"));
    }

    @Test
    void missedPasswordTest() {
        open("/");
        stopPageLoading();
        $("[name=email]").setValue("qagurubot@gmail.com").pressEnter();

        $(".btn-error").shouldHave(text("Не заполнено поле Пароль"));
    }

    @Test
    void missedLoginTest() {
        open("/");
        stopPageLoading();
        $("[name=password]").setValue("some1a3241passhere").pressEnter();

        $(".btn-error").shouldHave(text("Не заполнено поле E-Mail"));
    }

    @Test
    void missedLoginAndPasswordTest() {
        open("/");
        stopPageLoading();
        $(".btn-success").click();

        $(".btn-error").shouldHave(text("Не заполнено поле E-Mail"));
    }


    //WORKAROUND
    //https://connect.facebook.net/en_US/fbevents.js request tries to be loaded and fails in ~34 seconds --> time out exception
    private void stopPageLoading() {
        sleep(6000);
        Selenide.executeJavaScript("window.stop();");
    }
}

