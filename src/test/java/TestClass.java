import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class TestClass {
    private Integer productToDeleteId = null;

    @BeforeAll
    public static void setUp() {
        open(ConfigReader.get("base.url"));
    }

    @Test
    public void testMainPageAndAuthPage() {
        MainPage mainPage = new MainPage();
        AuthPage authPage = new AuthPage();

        mainPage.productCardIsVisible();
        mainPage.cartIsVisible();
        mainPage.adminIsVisible();
        mainPage.addProductPlusIsVisible();
        mainPage.addProductHaveText();

        mainPage.clickAddProduct();
        mainPage.clickAddProductPlus();
        mainPage.dragAndDropProductCard();
        mainPage.clickAdmin();
        Selenide.back();
        mainPage.clickCart();
        $x("//*[@id='close-modal']").click();

        mainPage.clickAdmin();
        authPage.loginContainsText();
        authPage.passwordContainsText();
        authPage.submitIsVisible();

    }

    //Задача 2: переписать (или написать заново) следующие кейсы в проекте с использованием архитектуры PageObject:
    //3.1. Добавить три единицы товара в корзину и оплатить их (общая стоимость не должна превышать 300 рублей).
// Проверить уведомление об обработке заказа.
    @Test
    void task3_1(){
        MainPage mainPage = new MainPage();
        AuthPage authPage = new AuthPage();
        AdminPage adminPage = new AdminPage();
        CartPage cartPage = new CartPage();

        mainPage.clickAdmin();

        //Авторизация в админке
        authPage.loginEntry();
        authPage.passwordEntry();
        authPage.submitClick();

        //Ввод нового товара
        int randomNumber = (int) (Math.random() * 10000);
        String productName = "Товар_" + randomNumber;
        adminPage.nameProduct.sendKeys(productName);
        adminPage.countProduct.sendKeys("50");
        adminPage.createProduct.click();

        //Переход на витрину
        adminPage.backButton.click();

        //Добавление нового товара в корзину
        $x("//div[@data-name='"+productName+"'][@data-price='50']//button[@data-action='add-to-cart']").click();
        $x("//div[@data-name='"+productName+"'][@data-price='50']//button[@data-action='add-to-cart']").click();
        $x("//div[@data-name='"+productName+"'][@data-price='50']//button[@data-action='add-to-cart']").click();

        //Проверка отображения тоста
        mainPage.clickCart();
        cartPage.makeOrderClick();
        MarketAssert.assertOrderIsSuccessfullyProcessed();
    }

    //3.2. Добавить в корзину несколько разных товаров и проверить, что общая цена в корзине считается корректно.
    @Test
    void task3_2(){

        MainPage mainPage = new MainPage();

        //Добавляем в корзину
        mainPage.addProduct.get(0).click();
        mainPage.addProduct.get(1).click();
        mainPage.addProduct.get(2).click();

        //Подсчет общей стоимости
        String rawPrice1 = mainPage.addProduct.get(0).getAttribute("data-price");
        int productPrice1 = Integer.parseInt(rawPrice1);

        String rawPrice2 = mainPage.addProduct.get(1).getAttribute("data-price");
        int productPrice2 = Integer.parseInt(rawPrice2);

        String rawPrice3 = mainPage.addProduct.get(2).getAttribute("data-price");
        int productPrice3 = Integer.parseInt(rawPrice3);

        int totalProductPrice = productPrice1 + productPrice2 + productPrice3;

        //Переход в корзину и проверка общей стоимости
        mainPage.clickCart();
        MarketAssert.assertTotalPrice(totalProductPrice);
    }

    //3.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.
    @Test
    void task3_3(){
        MainPage mainPage = new MainPage();
        AuthPage authPage = new AuthPage();
        AdminPage adminPage = new AdminPage();

        mainPage.clickAdmin();
        //Авторизация в админке
        authPage.loginEntry();
        authPage.passwordEntry();
        authPage.submitClick();

        //Ввод нового товара
        int randomNumber = (int) (Math.random() * 10000);
        String productName = "Товар_" + randomNumber;
        adminPage.nameProduct.sendKeys(productName);
        adminPage.countProduct.sendKeys("350");
        adminPage.createProduct.click();

        //Проверка уведомления
        MarketAssert.assertShowToast();
    }

    //3.4. Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились.
    @Test
    void task3_4(){

        MainPage mainPage = new MainPage();
        AuthPage authPage = new AuthPage();
        AdminPage adminPage = new AdminPage();

        int randomNumber = (int) (Math.random() * 10000);
        String initialName = "API_Товар_" + randomNumber;
        String jsonBody = "{\"name\":\"" + initialName + "\", \"price\":200}";

        //Отправляем POST-запрос с помощью RestAssured
        productToDeleteId = RestAssured.given()
                .auth().basic("admin", "secret123")
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("http://localhost:8080/goods/add")
                .path("data.id");

        //Редактирование созданного товара
        mainPage.clickAdmin();
        //Авторизация в админке
        authPage.loginEntry();
        authPage.passwordEntry();
        authPage.submitClick();
        Selenide.refresh();

        //Находим в таблице админки товар
        String newName = "Картофель_" + randomNumber;
        $x("//tr[td/input[@value='" + initialName + "']]//input[@type='text']").clear();
        $x("//tr[td/input[@value='" + initialName + "']]//input[@type='text']").sendKeys(newName);
        $x("//tr[td/input[@value='" + initialName + "']]//button[contains(@class, 'btn-upd')]").click();

        //Провека на витрине
        adminPage.backButton.click();
        MarketAssert.assertHaveProductCard(newName);

    }

}

