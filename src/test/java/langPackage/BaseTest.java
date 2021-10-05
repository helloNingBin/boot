package langPackage;

import com.macasaet.fernet.Key;
import com.macasaet.fernet.StringValidator;
import com.macasaet.fernet.Token;
import com.macasaet.fernet.Validator;
import org.testng.reporters.jq.Main;

import java.util.function.Function;

/**
 *
 */
public class BaseTest {
    public static void main(String[] args) {
    String v = "gAAAAABgyay49iiiJpgyyo2yvIW-x1hc_HHSdBTvf3ftDS_XZbRY1xyCSgkXoXjWyyCwMLDy7yJVNbKpaE1bsWVcLmKwQTbewRx3pTugRMIBGSm0A-pKENT1QU_INy_6GC3OauwE2gkyiZ7R8ncYv78m3alQy3awuvvQTCfjQ4Bj__XfZ_eSEsjtFSyS6LkWbyw2-DdjvWjbK9BTSbxCgorYSJLRMLBdl1hNmmD0FZoAF1c8-ha9FNo=";
  //  v="gAAAAABgyYX30ZNA6HAZFH9UFCX3vQMRZN60u_8dlfAJmOVJufeNimI9ls3dQi0KL1RNsFmx6oiFXDfZVQloAw6O-8cqxarxH8iqQJ_7rzd_9tj6l8HEjfm85KxHSkP_zij7_KmVzI5HnRReBK4Vlt4XlME8LlCNmaNJiFOE975NzBsrz8LTHFAcStPLJgCcxWLIPMw6K2FgtY11cxoUUDSYWuVXRBeaNA==";
        Key key = new Key("rryDUI0Y4bO8rYEOBWEZaXS0HIU5yeXya5Zwudw9Pks=");
       // Token token = Token.generate(key, v);
       final Token token = Token.fromString(v);
        final Validator<String> validator = new StringValidator() {
        };
        String s = validator.validateAndDecrypt(key, token);
        System.out.println(s);
    }
    public static String fernetDecript(String json){
        try {
            String keyString = "rryDUI0Y4bO8rYEOBWEZaXS0HIU5yeXya5Zwudw9Pks=";
            Key key = new Key(keyString);
            final Token token = Token.fromString(json);
            final Validator<String> validator = new StringValidator() {
            };
            String s = validator.validateAndDecrypt(key, token);
            return s;
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }
}
