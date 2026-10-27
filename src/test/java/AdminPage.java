import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AdminPage {
    public SelenideElement nameProduct = $x("//div/input[@type=\"text\"]");
    public SelenideElement countProduct = $x("//div/input[@type=\"number\"]");
    public SelenideElement createProduct = $x("//div/button[@id=\"add-btn\"]");
    public SelenideElement backButton = $x("//div/a[@href=\"/\"]");
}
