import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.$x;

public class MarketAssert {
    public static void assertOrderIsSuccessfullyProcessed()  {
        $x("//div[@class='toast' and text()='Заказ принят в обработку!']")
                .shouldBe(Condition.visible);
    }

    public static void assertTotalPrice(int totalProductPrice){
        int totalPrice = Integer.parseInt($x("//span[@id='total-price']").getText());
        Assertions.assertEquals(totalProductPrice, totalPrice, "Сумма в корзине не совпадает с ожидаемой!");
    }

    public static void assertShowToast(){
        $x("//div[@class='toast' and text()='Товар успешно добавлен!']").shouldBe(Condition.visible);
    }

    public static void assertHaveProductCard(String newName){
        $x("//div[@class='product-card' and @data-name='" + newName + "']").shouldBe(Condition.visible);
    }

}
