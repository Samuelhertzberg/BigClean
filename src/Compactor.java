import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;

/**
This class compacts java source files. It does this via a simple rule set.
To compact a file type "java main <Path to file to be compacted>"
*/
public class Compactor{
    FileReader fr;
    FileWriter fw;
    BufferedReader reader;
    BufferedWriter writer;
    public Compactor(File file) throws IOException{
        // String newPath = appendToPath("Compact", file);
        String newPath = addInDir("Compact", file);
        if (newPath != null) {
            fr = new FileReader(file);
            reader = new BufferedReader(fr);
            fw = new FileWriter(new File(newPath));
            writer = new BufferedWriter(fw);
            compact(reader, writer);
            writer.flush();
        }
    }

    /**
    Compacts a java source file to a single line with simple rules.
    if all adjecent tokens that both starts with a letter should be separated by one whitespace.
    All other combinations of tokes need not be separated. There are surely a plethora of edgecases.
    For example string declarations.
    @param reader, the reader from the un-compacted file
    @param writer, the writer to the compacted file
    */
    private void compact(BufferedReader reader, BufferedWriter writer){
        Boolean fstTokenEqWord = false;
        Character[] sndToken;
        Character c;
        try {
            c = (char) reader.read();
            while(reader.ready()){ //Main loop
                fstTokenEqWord = Character.isLetter(c);
                while(!Character.isWhitespace(c) && reader.ready()){ //Loop through token
                    if(fstTokenEqWord && !(Character.isLetter(c) || Character.isDigit(c))) //If fst token is word and we enter a not-word with no preceeding space
                        fstTokenEqWord = false;
                    else if(!fstTokenEqWord && Character.isLetter(c)) //If fst token is not a word and we enter a word with no preceeding space
                        fstTokenEqWord = true;
                    writer.write(c);
                    if(c == '\"'){
                        skipStringDec(reader, writer);
                    }
                    c = (char) reader.read();
                }
                if(reader.ready() && Character.isWhitespace(c)){ //Reached a token separator
                    while(Character.isWhitespace(c) && reader.ready()){ //Loop through adjacent whitespaces.
                        c = (char) reader.read();
                    }
                    if(Character.isLetter(c)){//Second token is a word
                        if(fstTokenEqWord)
                            writer.write(' '); //If first AND second token is a word, we need the separator
                    }//Second token is not a word. Thus we do not need to write the whitespaces.
                }
            }
        }catch (IOException e) {
            System.out.println("Hmm");
        }
    }

    /**
    Skips the declaration of a string. Keeps reading until there is a closing quotation mark.
    @param reader, the reader from the un-compacted file
    @param writer, the writer to the compacted file
    */
    public void skipStringDec(BufferedReader reader, BufferedWriter writer) throws IOException{
        char c1 = 'a'; //Anything but '\'
        char c2;
        while(reader.ready()){
            c2 = (char)reader.read();
            writer.write(c2);
            if(c2 == '\"' && c1 != '\\') //Char is non-escaped closing quotation mark
                return;
            c1 = c2;
        }
        return;
    }


    /**
    Appends a subfix to a path BEFORE the last file ending.
    @param addition the subfix
    @param file the file whos path should be altered.
    */
    private String appendToPath(String addition, File file){
        if(file.exists() && file.isFile()){
            String path = file.getPath();
            int i = file.getPath().lastIndexOf(".");
            if(i >= 0){
                String fileEnding = path.substring(i, path.length());
                String filePathNoEnding = path.substring(0, i);
                return filePathNoEnding + addition + fileEnding;
            }else{
                return path + addition;
            }
        }
        return null;
    }

    /**
    Created a new path were the files liew within a new directory of a given name.
    @param dirName the directory dirName
    @param file the child file.
    */
    private String addInDir(String dirName, File file){
        if(file.exists() && file.isFile()){
            String path = file.getPath();
            int i = file.getPath().lastIndexOf("/");
            if(i >= 0){
                String fileName = path.substring(i, path.length());
                String headlessPath = path.substring(0, i);
                File parent = new File(headlessPath + "/" + dirName);
                parent.mkdirs();
                return parent.getPath() + fileName;
            }else{
                return dirName + "/" + path;
            }
        }
        return null;
    }
}
