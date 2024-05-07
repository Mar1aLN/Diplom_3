import org.junit.Test;
import service.PropertiesHelper;
import service.YandexDriverHelper;

public class temp {
    @Test
    public void tempTest(){
        PropertiesHelper propertiesHelper = new PropertiesHelper();
        propertiesHelper.getPropertyValue("yandexDriver.binary");
    }
}
