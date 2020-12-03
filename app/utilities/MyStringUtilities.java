package app.utilities;
public class MyStringUtilities {
    public static String trimEnd(String s, char c) {
        StringBuilder edit = new StringBuilder(s);
        while (edit.toString().endsWith(Character.toString(c))) {
            edit.deleteCharAt(edit.length() - 1);
        }
        return edit.toString();
    }
}
