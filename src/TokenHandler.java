import java.util.ArrayList;
public class TokenHandler{
    ArrayList<int[]> tokens = new ArrayList<int[]>();
    public void add(int[] token){
        tokens.add(token);
    }

    public void remove(int i){
        tokens.remove(i);
    }

    public int size(){
        return tokens.size();
    }

    public int type(int i){
        return tokens.get(i)[0];
    }

    public int pos(int i){
        return tokens.get(i)[1];
    }

    public int length(int i){
        return tokens.get(i)[2];
    }

    public void setType(int i, int type){
        tokens.get(i)[0] = type;
    }

    public void setPos(int i, int pos){
        tokens.get(i)[1] = pos;
    }

    public void setLength(int i, int length){
        tokens.get(i)[2] = length;
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
                case 9: retur += "<BIG COMMENT>";
                    break;
            }
        }
        return retur;
    }

    public void fullPrint(){
        for (int[] t: tokens) {
            System.out.println(t[0] + ", " + t[1] + ", " + t[2]);
        }
    }

    public boolean isSpace(int i){
        return tokens.get(i)[0] == 0;
    }

    public boolean isNewline(int i){
        return tokens.get(i)[0] == 1;
    }

    public boolean isWhitespace(int i){
        return tokens.get(i)[0] == 0 || tokens.get(i)[0] == 1;
    }

    public boolean isWordLike(int i){
        int[] token = tokens.get(i);
        return token[0] == 2 || token[0] == 7 || token[0] == 9;
    }

    public boolean isComment(int i){
        int[] token = tokens.get(i);
        return token[0] == 6;
    }

    public boolean isBigComment(int i){
        int[] token = tokens.get(i);
        return token[0] == 9;
    }
}
