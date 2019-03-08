import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
public class Tokenizer{
    private BufferedReader reader;
    /*
    The tokenField will have the following system:
    An ArrayList filled with int arrays. Each int array will contain information
    regarding a single instance of a token. The information will be as follows:
    [type, position, length] Keep will be a 1 or 0.
    TYPES:
    0: space/tab
    1: newline
    2: word
    3: parenthesis
    4: CurlyBrackets
    5: HardBrackets
    6: Comment
    7: String
    8: Other
    */
    ArrayList<int[]> fullTokenField = new ArrayList<int[]>();
    int position;

    public Tokenizer(BufferedReader reader){
        this.reader = reader;
    }

    public void tokenize() throws IOException{
        System.out.print("Tokenzing:");
        char c;
        char last = 'x';
        while(reader.ready()){
            c = (char) reader.read();
            int[] token = null;
            if(c == ' '){
                fullTokenField.add(space());
            }else if(c == '\n'){
                fullTokenField.add(newline());
            }else if(Character.isLetter(c)){
                fullTokenField.add(word());
            }else if(c == '(' || c == ')'){
                fullTokenField.add(parenthesis());
            }else if(c == '{' || c == '}'){
                fullTokenField.add(curlyBrackets());
            }else if(c == '[' || c == ']'){
                fullTokenField.add(hardBrackets());
            }else if(c == '/'){
                fullTokenField.add(comment());
            }else if((c == '\"' || c == '\'') && last != '\\'){
                fullTokenField.add(string(c));
            }else{
                fullTokenField.add(other());
            }
            position++;
            last = c;
        }
        System.out.println(" Done.");
    }

    private int[] space(){
        int[] token = {0,position,1};
        return token;
    }

    private int[] newline(){
        int[] token = {1,position,1};
        return token;
    }

    private int[] word() throws IOException{
        char c = 'x';
        int[] token = {2,position,1}; //Length is 1 since we already read one char.
        while (reader.ready()) {
            reader.mark(1);
            c = (char) reader.read();
            if(!(Character.isLetter(c) || Character.isDigit(c))){//It aint a word no more
                reader.reset();
                return token;
            }
            position++;
            token[2]++;
        }
        return token;
    }

    private int[] parenthesis(){
        int[] token = {3,position,1};
        return token;
    }

    private int[] curlyBrackets(){
        int[] token = {4,position,1};
        return token;
    }

    private int[] hardBrackets(){
        int[] token = {5,position,1};
        return token;
    }

    private int[] comment() throws IOException{
        char c = 'x';
        int[] token = {6,position,1}; //Length is 1 since we already read one char.
        while (reader.ready()) {
            reader.mark(1);
            c = (char) reader.read();
            if(c == '\n'){//End of comment
                reader.reset();
                return token;
            }
            position++;
            token[2]++;
        }
        return token;
    }

    private int[] string(char openSign) throws IOException{
        char c;
        char last = 'x';
        int[] token = {7,position,1}; //Length is 1 since we already read one char.
        while (reader.ready()) {
            c = (char) reader.read();
            position++;
            token[2]++;
            if(c == openSign && last != '\\'){//End of string
                return token;
            }
        }
        return token;
    }

    private int[] other(){
        int[] token = {8,position,1};
        return token;
    }

    @Override
    public String toString(){
        String retur = "";
        for (int[] t : fullTokenField) {
            switch (t[0]) {
                case 0: retur += "<SPACE>";
                    break;
                case 1: retur += "<NEWLINE>";
                    break;
                case 2: retur += "<WORD>";
                    break;
                case 3: retur += "<PAR>";
                    break;
                case 4: retur += "<CUR>";
                    break;
                case 5: retur += "<BRA>";
                    break;
                case 6: retur += "<COMMENT>";
                    break;
                case 7: retur += "<STRING>";
                    break;
                case 8: retur += "<OTHER>";
                    break;
            }
        }
        return retur;
    }

    public ArrayList<int[]> getTokens(){
        return fullTokenField;
    }

    public void fullPrint(){
        for (int[] t: fullTokenField) {
            System.out.println(t[0] + ", " + t[1] + ", " + t[2]);
        }
    }
}
