package by.javateam;

import java.util.ArrayList;
import java.util.List;

public class StringSplit {

    private static String[] fillArray(String str) {
        String[] ar = str.split(",");
        for (int i = 0; i < ar.length; i++) {
            ar[i] = ar[i].trim();
        }
        return ar;
    }

    private static String[] diffArray(String[] ar1, String[] ar2) {
        List<String> result = new ArrayList();
        for (String str1 : ar1) {
            boolean find = false;
            for (String str2 : ar2) {
                if (str1.equals(str2)) {
                    find = true;
                }
            }
            if (!find) {
                result.add(str1);
            }
        }
        String s = String.valueOf(result);
        return result.toArray(new String[result.size()]);
    }

    public String[] stringSplit(String s2) {
        String s1 = "id, firstName, lastName, age, sex, city, income, createdTimestamp, modifiedTimestamp";
        String[] ar1 = null, ar2 = null;
        ar1 = fillArray(s1);
        ar2 = fillArray(s2);
        String[] result = diffArray(ar1, ar2);
        return result;
    }

}