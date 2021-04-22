package langPackage;

/**
 *
 */
public class BaseTest {
    public Object getVal(int i){
        if(i == 1){
            return getVal(i);
        }else{
            return 1;
        }
    }
}
