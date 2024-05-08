package service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ChromeDriverHelper implements WebDriverHelper {
    private final String caption = "Chrome";

    @Override
    public WebDriver setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    @Override
    public String getCaption() {
        return caption;
    }
}
