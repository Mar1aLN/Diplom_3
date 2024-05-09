package service;

import org.openqa.selenium.WebDriver;

public interface WebDriverHelper {
    WebDriver setUpDriver();

    String getCaption();
}
