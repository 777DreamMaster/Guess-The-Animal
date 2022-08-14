package animals;

import KnowledgeTree.Tree;
import Utils.*;

import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GuessTheAnimal {

    private static final Scanner SC = new Scanner(System.in);
    private static final Random R = new Random();

    private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

    private static final Pattern DELIMITER = Pattern.compile("\\f");

    private static final Tree TREE = new Tree();
    private static String STATEMENT;
    private static boolean bug = false;

    public static void start(Args args) {
        StorageService.setObjectMapper(args);
        greatTheUser();
        knowledge();
        startExpertSystem();
        sayBye();
    }

    private static void startExpertSystem() {
        System.out.println(getStr("welcome"));
        boolean working = true;
        while (working) {
            System.out.println("\n" + getStr("menu.property.title"));
            System.out.println("\n1. " + getStr("menu.entry.play") +
                    "\n2. " + getStr("menu.entry.list") +
                    "\n3. " + getStr("menu.entry.search") +
                    "\n4. " + getStr("menu.entry.statistics") +
                    "\n5. " + getStr("menu.entry.print") +
                    "\n0. " + getStr("menu.property.exit")
            );
            String op = SC.next();
            switch (op) {
                case "1" -> startGame();
                case "2" -> listOfAnimals();
                case "3" -> factsAboutAnimal();
                case "4" -> statistics();
                case "5" -> printKnowledgeTree();
                case "0" -> working = false;
                default -> System.out.println(getFormat("menu.property.error", 5));
            }
        }
    }

    private static void listOfAnimals() {
        System.out.println(getStr("tree.list.animals"));
        TREE.findAllAnimals()
                .forEach(a ->
                        System.out.printf(getStr("tree.list.printf"),
                                LanguageRules.applyRules("animalName", a))
                );
    }

    private static void factsAboutAnimal() {
        System.out.println(getStr("animal.prompt"));
        SC.nextLine();
        String animal = StringOperations.getAnimalWithArticle();
        List<String> facts = TREE.findAnimalFacts(animal);

        animal = LanguageRules.applyRules("animalName", animal);
        if (facts == null) {
            System.out.println(getFormat("tree.search.noFacts", animal));
        } else {
            System.out.println(getFormat("tree.search.facts", animal));
            facts.forEach(f -> System.out.printf(getStr("tree.search.printf"), StringOperations.capitalize(f)));
        }
    }

    private static void statistics() {
        List<Object> stats = TREE.getStatistics();
        System.out.println(getStr("tree.stats.title"));
        System.out.println(getFormat("tree.stats.root", stats.get(0)) + "\n" +
                          getFormat("tree.stats.nodes", stats.get(1)) + "\n" +
                          getFormat("tree.stats.animals", stats.get(2)) + "\n" +
                          getFormat("tree.stats.statements", stats.get(3)) + "\n" +
                          getFormat("tree.stats.height", stats.get(4)) + "\n" +
                          getFormat("tree.stats.minimum", stats.get(5)) + "\n" +
                          getFormat("tree.stats.average", stats.get(6))
        );
    }

    private static void printKnowledgeTree() {
        if (TREE.getRoot() != null) System.out.println(TREE.getRoot().toString());
    }

    private static void startGame() {
        do {
            System.out.println(getStr("game.think"));
            System.out.println(getStr("game.enter"));
            SC.nextLine();
            if (!bug) {
                SC.nextLine();
                bug = true;
            }
            TREE.reset();
            while (true) {
                boolean isAnimal = TREE.getCurrent().isAnimal();

                if (isAnimal) {
                    String animal = TREE.getCurrent().getValue();
                    System.out.println(StringOperations.capitalize(LanguageRules.applyRules("guessAnimal", animal)));
                } else {
                    String statement = TREE.getCurrent().getValue();
                    System.out.println(StringOperations.capitalize(LanguageRules.applyRules("question", statement)) + "?");
                }

                boolean isYes = yesOrNo();
                if (isYes && isAnimal) {
                    System.out.println(getRandomStr("game.win"));
                    break;
                }
                if (isAnimal) {
                    System.out.println(getStr("game.giveUp"));
                    String animal = StringOperations.getAnimalWithArticle();
                    specifyFact(TREE.getCurrent().getValue(), animal);
                    break;
                }
                TREE.move(isYes);
            }
            System.out.println(getRandomStr("game.again"));
        } while (yesOrNo());
    }

    private static void knowledge() {
        if (TREE.getRoot() == null){
            System.out.println(getStr("animal.wantLearn") + "\n");
            System.out.println(getStr("animal.askFavorite"));

            String animal = StringOperations.getAnimalWithArticle();
            TREE.setRoot(animal);
        }
    }

    private static void specifyFact(String animal1, String animal2) {
        boolean isCorrect;
        do {
            System.out.println(getFormat("statement.prompt", animal1, animal2));
            isCorrect = checkFactFormat();
        } while (!isCorrect);

        System.out.println(getFormat("game.isCorrect", animal2));
        boolean isYesForAnimal2 = yesOrNo();
        whatIsLearned(animal1, animal2, isYesForAnimal2);
    }

    private static boolean checkFactFormat() {
        String fact = SC.nextLine().toLowerCase();
        boolean f = LanguageRules.isCorrect("statement", fact);
        if (!f) {
            System.out.println(getStr("statement.error"));
        } else {
            STATEMENT = fact;
        }
        return f;
    }

    private static void whatIsLearned(String an1, String an2, boolean isYes) {
        System.out.println(getStr("game.learned"));

        String positiveFact = LanguageRules.applyRules("animalFact", STATEMENT);
        String negativeFact = LanguageRules.applyRules("animalFact",
                                            LanguageRules.applyRules("negative", STATEMENT));

        System.out.printf(" - " + (!isYes ? positiveFact : negativeFact) + ".%n",
                StringOperations.capitalize(LanguageRules.applyRules("definite", an1))
        );
        System.out.printf(" - " + (isYes ? positiveFact : negativeFact) + ".%n",
                StringOperations.capitalize(LanguageRules.applyRules("definite", an2))
        );

        System.out.println(getStr("game.distinguish"));

        TREE.addAnimal(an2, STATEMENT, isYes);
        System.out.printf(getStr("tree.list.printf"),
                StringOperations.capitalize(LanguageRules.applyRules("question", STATEMENT)) + "?");

        System.out.println(getRandomStr("animal.nice") + getStr("animal.learnedMuch"));
    }

    private static boolean yesOrNo() {
        String answer;
        int yesNo;
        while (true) {
            answer = SC.nextLine().toLowerCase();
            yesNo = StringOperations.checkYesOrNo(answer);
            if (yesNo != 0) break;
            System.out.println(getRandomStr("ask.again"));
        }
        return yesNo == 1;
    }

    private static void greatTheUser() {
        StorageService.loadFromFile(TREE);

        LocalTime time = LocalTime.now();
        String timeOfDay;
        if (time.isAfter(LocalTime.parse(getStr("evening.time.after")))) timeOfDay = "evening";
        else if (time.isAfter(LocalTime.parse(getStr("afternoon.time.after")))) timeOfDay = "afternoon";
        else if (time.isAfter(LocalTime.parse(getStr("morning.time.after")))) timeOfDay = "morning";
        else if (time.isAfter(LocalTime.parse(getStr("early.time.after")))) timeOfDay = "early";
        else timeOfDay = "night";

        System.out.println(getStr("greeting." + timeOfDay) + "\n");
    }

    private static void sayBye() {
        StorageService.saveToFile(TREE);
        System.out.println(getRandomStr("farewell"));
    }

    private static String getStr(String key) {
        return messages.getString(key);
    }

    private static String getFormat(String key, Object... args) {
        return MessageFormat.format(getStr(key), args);
    }

    private static String getRandomStr(String key) {
        String[] arr = DELIMITER.split(messages.getString(key));
        return arr[R.nextInt(arr.length)];
    }

}