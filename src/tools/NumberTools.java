package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberTools {

    public boolean isNumber(String numberStr) {
        Pattern pattern = Pattern.compile("^\\d{1,2}$");
        Matcher matcher = pattern.matcher(numberStr.trim());

        return matcher.find();


    }
}
