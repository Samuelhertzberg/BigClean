import java.io.File;
import java.io.IOException;
public class Main{
    public static void main(String[] args) {
        File toBeCleaned = new File(args[0]);
        try{
            Compactor compactor = new Compactor(toBeCleaned);
        }catch (IOException e) {
            System.out.println("FILE NOT FOUND!");
            System.out.println(e);
        }
    }
}
