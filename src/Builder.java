import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
public class Builder{
    TokenHandler tokens;
    BufferedReader reader;
    BufferedWriter writer;

    public Builder(TokenHandler th, BufferedReader reader, BufferedWriter writer){
        tokens = th;
        this.reader = reader;
        this.writer = writer;
    }

    public void build() throws IOException{
        for (int i = 0 ; i < tokens.size() && reader.ready(); i++) {
            if(i>0){
                int endOfLastWord = tokens.pos(i-1) + tokens.length(i-1);
                int skip = tokens.pos(i)-endOfLastWord;
                System.out.println("endOfLastWord: " + endOfLastWord);
                System.out.println("token pos: " + tokens.pos(i));
                System.out.println("skip: " + skip);
                reader.skip(skip);
            }
            writeToken(i);
        }
        writer.flush();
    }

    private void writeToken(int token) throws IOException{
        for (int j = 0 ; j < tokens.length(token) && reader.ready(); j++) {
            char c = (char) reader.read();
            writer.write((tokens.type(token) == 0) ? ' ' : c); //Just write a space if the token is a space
        }
    }
}
