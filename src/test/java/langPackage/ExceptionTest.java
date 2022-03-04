package langPackage;

import java.util.Arrays;

public class ExceptionTest {
    public static void main(String[] args) {
        String countStr = "00,02,03,04,05,06,07,08,09,15,10,11,12,13,14,15,16,17,18,15,19,20,21,22,23,24,25,26,27,15,28,29,30,31,32,33,34,35,36,15,37,38,39,40,41,42,43,44,45,15,46,47,48,49,50,51,52,53,54,15,55,56,57,58,59,60";
        String[] split = countStr.split(",");
        Arrays.sort(split);
        System.out.println(split.length);
        for(String s : split){

            System.out.println(s);
        }

    }
    public void t() throws Exception {
        throw new Exception();
    }
}
