package animals;

import Utils.Args;
import com.beust.jcommander.JCommander;

public class Main {
    public static void main(String[] args) {
        Args argv = new Args();
        JCommander.newBuilder()
                .addObject(argv)
                .build()
                .parse(args);
        GuessTheAnimal.start(argv);
    }
}
