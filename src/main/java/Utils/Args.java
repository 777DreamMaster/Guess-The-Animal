package Utils;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-type", description = "Type of file")
    private String type;

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}

