package Utils;

import java.util.Scanner;

public class StringOperations {

    public static final Scanner SC = new Scanner(System.in);

    public static String getAnimalWithArticle() {
        String line = SC.nextLine().toLowerCase();
        line = LanguageRules.applyRules("tree.search", line);
        return LanguageRules.applyRules("animal", line);
    }

    public static int checkYesOrNo(String answer) {
        if (LanguageRules.isCorrect("positiveAnswer", answer)) return 1;
        if (LanguageRules.isCorrect("negativeAnswer", answer)) return -1;
        return 0;
    }

    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
