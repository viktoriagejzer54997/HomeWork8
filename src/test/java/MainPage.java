//PageObject для главной страницы с товарами. Можно добавить на неё любые объекты и коллекции, главное, чтобы их было не меньше 5.
// Дополнительно необходимо написать методы взаимодействия с каждым из них в зависимости от типа (click(), sendKeys());

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.DragAndDropOptions.to;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    //карточка товара
    private SelenideElement productCard = $x("//*[@class = 'product-card']");

    //корзина
    private SelenideElement cart = $x("//*[@class = 'btn btn-cart']");

    //кнопка В корзину для первого товара
    public ElementsCollection addProduct = $$x("//button[@data-action='add-to-cart']");

    //Админка
    private SelenideElement admin = $x("//div/a[@href=\"/admin\"]");

    //Добавление товара через плюс
    private SelenideElement addProductPlus = $x("(//button[@class=\"qty-btn\"][@data-step='1'])[1]");




    //Взаимодействие с элементами
    public void dragAndDropProductCard (){
        productCard.dragAndDrop(to(cart));
    }

    public void clickCart (){
        cart.click();
    }

    public void clickAddProduct(){
        addProduct.get(0).click();
    }

    public void clickAdmin(){
        admin.click();
    }

    public void clickAddProductPlus(){
        addProductPlus.click();
    }



    //Проверки для элементов
    public void productCardIsVisible(){
        productCard.shouldBe(Condition.visible);
    }

    public void cartIsVisible(){
        cart.shouldBe(Condition.visible);
    }

    public void addProductHaveText(){
        addProduct.get(0).should(text("В корзину"));
    }

    public void adminIsVisible(){
        admin.shouldBe(Condition.visible);
    }

    public void addProductPlusIsVisible(){
        addProductPlus.shouldBe(Condition.visible);
    }


}
