import java.util.ArrayList;
public class Filter{
    TokenHandler tokens;
    public Filter(TokenHandler th){
        tokens = th;
    }

    public void filterTokens(){
        System.out.println("Filtering:");
        trimSpaces();
        filterStackedSpaces();
        filterUnnecessarySpaces();
    }

    public void filterUnnecessarySpaces(){
        System.out.print("Filtering unnecessary spaces: ");
        for (int i = 0 ; i < tokens.size() - 2 ;) {
            int first = i;
            int middle = i+1;
            int second = i+2;
            if(tokens.isWhitespace(middle)){ //Removal of spaces
                if(tokens.isComment(first)){//If the first token is comment we should not do anything
                }else if(tokens.isBigComment(first) || tokens.isBigComment(second)){
                }else if(tokens.isComment(second)){
                    if(tokens.isSpace(middle)){
                        //The space separating non-comment and comment (in that order) is unnecessary
                        tokens.remove(middle);
                        continue;
                    }
                }else if(!(tokens.isWordLike(first) && tokens.isWordLike(second))){ //They are not both words
                    tokens.remove(middle);
                    continue;
                }else{ //They are both words
                    tokens.setType(middle, 0); //Should always just be a space
                }
            }
            i++;
        }
        System.out.println("Done.");
    }

    public void filterStackedSpaces(){
        System.out.print("Filtering stacked spaces: ");
        int[] token1;
        int[] token2;
        for (int i = 0 ; i <tokens.size() ; i++) {
            if(tokens.isSpace(i)){ //Token on pos i is space
                while(tokens.size() > i+1 && tokens.isSpace(i+1))//Remove spaces trailing the space on pos i
                    tokens.remove(i+1);
                if(tokens.size() > i+1 && tokens.isNewline(i+1))//Space not neccessary before newline
                    tokens.remove(i);
            }else if(tokens.isNewline(i)){
                while(tokens.size() > i+1 && tokens.isWhitespace(i+1))//Remove spaces or newlines trailing the newline on pos i
                    tokens.remove(i+1);
            }
        }
        System.out.println("Done.");
    }

    public void trimSpaces(){
        System.out.print("Trimming initial and trailing spaces: ");
        while(tokens.size() > 0 && tokens.isSpace(0)) //Initial spaces
            tokens.remove(0);
        while(tokens.size() > 0){ //Trailing whitespaces
            int last = tokens.size()-1;
            if(tokens.isSpace(last) || tokens.isNewline(last)){
                tokens.remove(last);
            }else{
                break;
            }
        }
        System.out.println("Done.");
    }
}
