package improviseadaptovercome11;


import java.util.Objects;

public class Logger extends Globals {

    public static String s = "";

    public static void reset()
    {
        s = "";
    }
    public static void addToIndicatorString(String newS)
    {
        //System.out.println(newS);
        if (Objects.equals(s, "")) {
            s = newS;

        }
        else
        {
            s = s + ", " + newS;
        }
        rc.setIndicatorString(s);
    }
}
