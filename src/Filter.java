import java.util.ArrayList;
public class Filter{
    ArrayList<int[]> tokens = new ArrayList<int[]>();
    public Filter(ArrayList<int[]> tokens){
        this.tokens = tokens;
    }

    public void filterTokens(){
        trimSpaces();
        filterStackedSpaces();
        filterUneccessarySpaces();
    }

    public void filterUneccessarySpaces(){
        for (int i = 0 ; i < tokens.size() - 2 ;) {
            int[] last = tokens.get(i);
            int[] middle = tokens.get(i+1);
            int[] head = tokens.get(i+2);
            if(isWhitespace(middle)){
                if(!(isWordLike(last) && isWordLike(head))){ //They are not both words
                    tokens.remove(i+1);
                    continue;
                }
            }
            i++;
        }
    }

    public void filterStackedSpaces(){
        int[] token1;
        int[] token2;
        for (int i = 0 ; i <tokens.size() ; i++) {
            if(isSpace(i)){ //Token on pos i is space
                while(tokens.size() > i+1 && isSpace(i+1))//Remove spaces trailing the space on pos i
                    tokens.remove(i+1);
                if(tokens.size() > i+1 && isNewline(i+1))//Space not neccessary before newline
                    tokens.remove(i);
            }else if(isNewline(i)){
                while(tokens.size() > i+1 && isWhitespace(i+1))//Remove spaces or newlines trailing the newline on pos i
                    tokens.remove(i+1);
            }
        }
    }

    public void trimSpaces(){
        while(tokens.size() > 0 && isSpace(0)) //Initial spaces
            tokens.remove(0);
        while(tokens.size() > 0){ //Trailing whitespaces
            int last = tokens.size()-1;
            if(isSpace(last) || isNewline(last)){
                tokens.remove(last);
            }else{
                break;
            }
        }
    }

    public ArrayList<int[]> getTokens(){
        return tokens;
    }


    private boolean isSpace(int[] token){
        return token[0] == 0;
    }

    private boolean isSpace(int i){
        return tokens.get(i)[0] == 0;
    }

    private boolean isNewline(int[] token){
        return token[0] == 1;
    }

    private boolean isNewline(int i){
        return tokens.get(i)[0] == 1;
    }

    private boolean isWhitespace(int[] token){
        return token[0] == 0 || token[0] == 1;
    }

    private boolean isWhitespace(int i){
        return tokens.get(i)[0] == 0 || tokens.get(i)[0] == 1;
    }

    private boolean isWordLike(int[] token){
        return token[0] == 2 || token[0] == 6 || token[0] == 7;
    }

    private boolean isWordLike(int i){
        int[] token = tokens.get(i);
        return token[0] == 2 || token[0] == 6 || token[0] == 7;
    }

    @Override
    public String toString(){
        String retur = "";
        for (int[] t : tokens) {
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
}
