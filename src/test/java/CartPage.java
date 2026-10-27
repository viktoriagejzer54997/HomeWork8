import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CartPage {
    public SelenideElement makeOrder = $x("//*[@id = 'makeOrder']");

    public void makeOrderClick(){
        makeOrder.click();
    }
}


