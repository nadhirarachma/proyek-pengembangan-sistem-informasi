package propensi.b02.sobatarlydia.setting;

public class Setting {

    private Setting() {}

    public static final String CLIENT_BASE_URL = System.getenv("CLIENT_BASE_URL") == null ? "http://localhost:8080" : System.getenv("CLIENT_BASE_URL");

}
