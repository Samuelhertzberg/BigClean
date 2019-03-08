import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
public class Builder{
    ArrayList<int[]> tokens;
    BufferedReader reader;
    BufferedWriter writer;

    public Builder(ArrayList<int[]> tokens, BufferedReader reader, BufferedWriter writer){
        this.tokens = tokens;
        this.reader = reader;
        this.writer = writer;
    }

    public void build() throws IOException{
        int position = 0;
        for (int i = 0 ; i < tokens.size() && reader.ready(); i++) {
            int skip = tokens.get(i)[1] - position;
            reader.skip(skip);
            position += skip;
            if(tokens.get(i)[0] == 0){ //For writing fabricated spaces
                reader.read(); //Eat up whatever was there
                writer.write(' ');
                position++;
                continue;
            }
            for (int j = 0 ; j < tokens.get(i)[2] && reader.ready(); j++, position++) {
                writer.write((char) reader.read());
            }
        }
        writer.flush();
    }
}
