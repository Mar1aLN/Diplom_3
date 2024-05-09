package service;

import enums.BrowserTypes;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

public class WebDriverHelperFactory {
    private final Map<BrowserTypes.BrowserType, WebDriverHelper> driversMap;

    public WebDriverHelperFactory() {
        driversMap = new HashMap<>();

        driversMap.put(BrowserTypes.BrowserType.CHROME, new ChromeDriverHelper());
        driversMap.put(BrowserTypes.BrowserType.YANDEX_BROWSER, new YandexDriverHelper());
    }

    @Step("Получение нужного объекта WebDriverHelper по переданному enum")
    public WebDriverHelper createWebDriverHelper(BrowserTypes.BrowserType browserType) {
        return driversMap.get(browserType);
    }

    @Step("Получение типа браузера из .properties с преобразованием в enum, вызов получения WebDriverHelper по enum")
    public WebDriverHelper createWebDriverHelper() {
        BrowserTypes.BrowserType browserType = BrowserTypes.BrowserType.valueOf(PropertiesHelper.getPropertyValue("browserType"));

        return createWebDriverHelper(browserType);
    }
}
