package Utils;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LanguageRules {

    private static final Map<String, Pattern> PATTERNS;
    private static final ResourceBundle BUNDLE;

    static {
        BUNDLE = ResourceBundle.getBundle("patterns");
        PATTERNS = BUNDLE.keySet().stream()
                .filter(key -> !key.endsWith(".replace"))
                .collect(Collectors.toMap(key -> key, key -> Pattern.compile(BUNDLE.getString(key))));
    }

    public static String applyRules(String rule, String data) {
        for (int i = 1; ; i++) {
            String key = rule + "." + i;
            Pattern pattern = PATTERNS.get(key + ".pattern");

            if (pattern == null) return data;

            Matcher matcher = pattern.matcher(data);
            if (matcher.matches())
                return matcher.replaceFirst(BUNDLE.getString(key + ".replace"));
        }
    }

    public static boolean isCorrect(String key, String data) {
        return PATTERNS.get(key + ".isCorrect").matcher(data).matches();
    }
}
