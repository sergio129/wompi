package automation.wompi.Utilities;

import java.util.ResourceBundle;

public class DatosPagoPSE {
    public static ResourceBundle resourceBundle() {
        if (System.getProperty("env") != null) {
            return ResourceBundle.getBundle(System.getProperty("env"));
        } else {
            return ResourceBundle.getBundle("PagoPSE");
        }
    }

    public static String getDatosPagoPSE(String keys) {
        return resourceBundle().getString(keys);
    }

}
