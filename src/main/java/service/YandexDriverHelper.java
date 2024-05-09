package service;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class YandexDriverHelper implements WebDriverHelper {
    private final String caption = "Яндекс.Браузер";

    @Override
    public WebDriver setUpDriver() {
        String binaryYandexDriverPath = PropertiesHelper.getPropertyValue("yandexDriver.binary");
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(binaryYandexDriverPath);
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));

        return driver;
    }

    @Override
    public String getCaption() {
        return caption;
    }
}
