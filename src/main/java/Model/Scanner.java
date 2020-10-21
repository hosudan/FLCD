package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

public class Scanner {
    public static final List<String> separators = Collections.unmodifiableList(Arrays.asList("(",")","[","]","{","}",";",","," ","\n","\t"));
    public static final List<String> operators = Collections.unmodifiableList(Arrays.asList("&&","||","+","-","*","/","=","<","<=","==","!=",">=",">","!"));
    public static final List<String> reservedWords = Collections.unmodifiableList(Arrays.asList("array","int","char","real","read","print","if","else","while"));

    private String fileName;
    private String fileTable;

    private HHashTable<String,Integer> codificationTable;
    private HHashTable<String,Integer> PIF;
    private SymbolTable ST;
    private boolean error;

    public Scanner(String fileName, String fileTable){
        this.fileName = fileName;
        this.fileTable = fileTable;
        this.codificationTable = readCodificationTable();
        this.PIF = new HHashTable<>();
        this.ST = new SymbolTable();
        this.error = false;
    }

    public HHashTable<String, Integer> readCodificationTable(){
        HHashTable<String, Integer> table = new HHashTable<>();
        try{
            File file = new File("/Users/krayst/IdeaProjects/FLCD/src/resources" + this.fileTable);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            String[] list;
            int  i = 0;
            while ((str = br.readLine()) != null) {
                list = str.split(" ");
                table.put(list[0], Integer.parseInt(list[1]));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception while reading from table file!");
        }
        return table;
    }

    public String parseFile(){
        String content = "";
        try{
            File file = new File("/Users/krayst/IdeaProjects/FLCD/src/resources" + this.fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                content += str;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception while reading from file!");
        }
        return content;
    }

    public boolean isSeparator(String ch){
        return separators.contains(ch);
    }

    public boolean isOperator(String ch){
        return operators.contains(ch);
    }

    public boolean isReservedWord(String ch){
        return reservedWords.contains(ch);
    }

    public boolean isIdentifier(String ch){
        Pattern pattern = Pattern.compile("[a-zA-Z]{1,250}");
        if (ch.length() > 250) {
            return false;
        }
        return (pattern.matcher(ch).matches());
    }

    public boolean isConstant(String ch){
        return (isCharacter(ch) || isInteger(ch));
    }

    public boolean isCharacter(String ch){
        Pattern pattern = Pattern.compile("^'[a-zA-Z0-9]'$");
        return (pattern.matcher(ch).matches());
    }

    public boolean isInteger(String ch){
        Pattern pattern = Pattern.compile("[-]?\\d+");
        return (pattern.matcher(ch).matches());
    }


    public List<String> detectTokens(){
        String content = parseFile();
        String tokenChar = "";
        String tokenOp = "";
        List<String> tokens = new ArrayList<String>();
        int i = 0;
        while(i < content.length()){
            String ch = Character.toString(content.charAt(i));
            while (!isSeparator(ch) && !isOperator(ch)){
                tokenChar += ch;
                i++;
                if (i < content.length()) {
                    ch = Character.toString(content.charAt(i));
                }
                else{
                    break;
                }
            }
            if (tokenChar != ""){
                tokens.add(tokenChar);
                tokenChar = "";
            }
            while(isSeparator(ch)){
                tokens.add(ch);
                i++;
                if (i < content.length()) {
                    ch = Character.toString(content.charAt(i));
                }
                else{
                    break;
                }
            }
            int ok = 0;
            while(isOperator(ch)){
                tokenOp += ch;
                //TODO
            }
            if (tokenOp != ""){
                tokens.add(tokenOp);
                tokenOp = "";
            }
        }
        tokens = removeWhitespaces(tokens);
        return tokens;
    }

    public List<String> removeWhitespaces(List<String> str){
        List<String> output  = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\s+");
        for (int i = 0; i < str.size(); i++){
            if (!pattern.matcher(str.get(i)).matches()){
                output.add(str.get(i));
            }
        }
        return output;
    }

    public void addToST(){   //TODO
        List<String> tokens = detectTokens();
        for (String token : tokens){
            if (isReservedWord(token) || isOperator(token) || isSeparator(token)){
            }
            else if (isIdentifier(token)){
            }
            else if (isConstant(token)){
            }
        }
    }

    public void addToPIF(){ //TODO
        List<String> tokens = detectTokens();
        for (String token : tokens){

        }
    }
}
