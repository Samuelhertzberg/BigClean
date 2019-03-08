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
    9: BigComment
    */
    int position;
    TokenHandler tokens;
    public Tokenizer(BufferedReader reader, TokenHandler th){
        this.reader = reader;
        this.tokens = th;
    }

    public void tokenize() throws IOException{
        System.out.print("Tokenzing:");
        char c;
        char last = 'x';
        while(reader.ready()){
            c = (char) reader.read();
            int[] token = null;
            if(c == ' '){
                tokens.add(space());
            }else if(c == '\n'){
                tokens.add(newline());
            }else if(Character.isLetter(c)){
                tokens.add(word());
            }else if(c == '(' || c == ')'){
                tokens.add(parenthesis());
            }else if(c == '{' || c == '}'){
                tokens.add(curlyBrackets());
            }else if(c == '[' || c == ']'){
                tokens.add(hardBrackets());
            }else if(c == '/'){
                tokens.add(comment());
            }else if((c == '\"' || c == '\'') && last != '\\'){
                tokens.add(string(c));
            }else{
                tokens.add(other());
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
        System.out.println("COMMENT");
        char c1 = 'x';
        char c2 = 'x';
        c1 = (char) reader.read();
        boolean inlineComment = (c1 == '/'); //A second '/' means inline, else big comment
        int[] token = {(inlineComment ? 6 : 9),position,2}; //Length is 2 since we already read two char.
        position++;
        while (reader.ready()) {
            if(inlineComment)
                reader.mark(1);
            c1 = (char) reader.read();
            if(inlineComment && c1 == '\n'){//End of comment
                reader.reset();
                return token;
            }
            if(!inlineComment && c1 == '/' && c2 == '*'){//End of comment
                position++;
                token[2]++;
                return token;
            }
            position++;
            token[2]++;
            c2 = c1;
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
}
