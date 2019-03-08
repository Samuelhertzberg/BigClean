import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
public class Handler{
    File file;
    public Handler(File file){
        this.file = file;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Tokenizer tokenizer = new Tokenizer(reader);
            tokenizer.tokenize();
            Filter filter = new Filter(tokenizer.getTokens());
            filter.filterTokens();
            File compact = makeCompactFile(file, "Compact");
            reader = new BufferedReader(new FileReader(file)); //Re-instantiate to reset
            BufferedWriter writer = new BufferedWriter(new FileWriter(compact));
            Builder builder = new Builder(filter.getTokens(), reader, writer);
            builder.build();
        }catch (IOException e) {
            System.out.println("Could not construct FileReader");
        }
    }

    private File makeCompactFile(File file, String dirName){
        String path = file.getParent();
        path = (path == null) ? "" : path + "/";
        path += dirName + "/";
        new File(path).mkdirs();
        return new File(path + file.getName());
    }
}
