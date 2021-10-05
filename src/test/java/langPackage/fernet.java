package langPackage;


import java.text.SimpleDateFormat;
import java.util.Date;

public class fernet {
    public static void main(String[] args) {
        String params = "32432432&djfosf&fjdlsjfd";
        int i = params.indexOf("&");
        System.out.println(params.substring(0,i));
        System.out.println(params.substring(i + 1));
    }
}
