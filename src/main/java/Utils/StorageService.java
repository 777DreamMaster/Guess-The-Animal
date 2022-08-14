package Utils;

import KnowledgeTree.Node;
import KnowledgeTree.Tree;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

public class StorageService {
    private static ObjectMapper objectMapper;
    private static final String fileName = System.getProperty("user.dir");
    private static String TYPE = "yaml";
    private static final String NAME = "animals";

    private static File getFile() {
        String language = System.getProperty("user.language", "en").toLowerCase();
        String fileSuffix = "en".equals(language) ? "" : "_" + language;
        return new File(fileName + "/" + NAME + fileSuffix + "." + TYPE);
    }

    public static void setObjectMapper(Args args) {
        if (args.getType() != null) {
            TYPE = args.getType();
        }
        objectMapper = switch (TYPE) {
            case "xml" -> new XmlMapper();
            case "json" -> new JsonMapper();
            default -> new YAMLMapper();
        };
    }

    public static void saveToFile(Tree tree) {
        try {
            File file = getFile();
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(file, tree.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromFile(Tree tree) {
        try {
            File file = getFile();
//            System.out.println(file.getAbsolutePath());
            if (file.exists()) tree.setRoot(objectMapper.readValue(file, Node.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
