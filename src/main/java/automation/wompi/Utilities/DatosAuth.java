package automation.wompi.Utilities;

import automation.wompi.Model.AuthModel;

import java.util.ResourceBundle;

public class DatosAuth {
    public static ResourceBundle resourceBundle() {
        if (System.getProperty("env") != null) {
            return ResourceBundle.getBundle(System.getProperty("env"));
        } else {
            return ResourceBundle.getBundle("Auth");
        }
    }

    public static String getDatosAuth(String keys) {
        return resourceBundle().getString(keys);
    }

    public static AuthModel llaves() {
        AuthModel model = new AuthModel();
        model.setPublicKey(DatosAuth.getDatosAuth("auth.publicKey"));
        model.setPrivateKey(DatosAuth.getDatosAuth("auth.privateKey"));
        return model;
    }
}
