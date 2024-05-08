# Diplom_3
Тип браузера указывается в файле src/test/resources/application.properties
в параметре browserType
доступные значения:
YANDEX_BROWSER
CHROME

Для тестирования Яндекс.Браузер требуется установленный YandexDriver 24.1.0, доступный по ссылке:
https://github.com/yandex/YandexDriver/releases/tag/v24.1.0-stable

Путь к yandexDriver.exe нужно указать в файле: src/test/resources/application.properties
в параметре: yandexDriver.binary.
