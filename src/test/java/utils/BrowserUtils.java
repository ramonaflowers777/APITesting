package utils;

import aquality.selenium.browser.AqualityServices;

public class BrowserUtils {
    public static void goBack() {
        AqualityServices.getBrowser().goBack();
    }
}
