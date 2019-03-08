import java.io.File;
import java.io.IOException;
public class Compact{
    public static void main(String[] args) {
        Handler h = new Handler(new File(args[0]));
    }
}
