//PageObject для формы авторизации в админке. Здесь должна быть полная функциональность (3 элемента: логин, пароль, кнопка «Войти»).

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class AuthPage {
    private SelenideElement login = $x("//p/input[@type=\"text\"]");
    private SelenideElement password = $x("//p/input[@type=\"password\"]");
    private SelenideElement submit = $x("//*[@type=\"submit\"]");


    public void loginContainsText (){
        login.$x("./..").shouldHave(text("Username"));
    }

    public void passwordContainsText (){
        password.$x("./..").shouldHave(text("Password"));
    }

    public void submitIsVisible(){
        submit.shouldBe(Condition.visible);
    }

    public void loginEntry(){
        login.sendKeys(ConfigReader.get("admin.login"));
    }

    public void passwordEntry(){
        password.sendKeys(ConfigReader.get("admin.password"));
    }

    public void submitClick(){
        submit.click();
    }
}

