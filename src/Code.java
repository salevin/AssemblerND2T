import java.util.Hashtable;

/**
 * Created by sam on 10/26/15.
 */
public class Code {
    private Hashtable<String, String> compTable = new Hashtable<>();

    public Code(){
        compTable.put("0", "0101010");
        compTable.put("1", "0111111");
        compTable.put("-1", "0111010");
        compTable.put("D", "0001100");
        compTable.put("A", "0110000");
        compTable.put("!D", "0001101");
        compTable.put("!A", "0110001");
        compTable.put("-D", "0001111");
        compTable.put("-A", "0110011");
        compTable.put("D+1", "0011111");
        compTable.put("A+1", "0110111");
        compTable.put("D-1", "0001110");
        compTable.put("A-1", "0110010");
        compTable.put("D+A", "0000010");
        compTable.put("D-A", "0010011");
        compTable.put("A-D", "0000111");
        compTable.put("D&A", "0000000");
        compTable.put("D|A", "0010101");
        compTable.put("M", "1110000");
        compTable.put("!M", "1110001");
        compTable.put("-M", "1110011");
        compTable.put("M+1", "1110111");
        compTable.put("M-1", "1110010");
        compTable.put("D+M", "1000010");
        compTable.put("D-M", "1010011");
        compTable.put("M-D", "1000111");
        compTable.put("D&M", "1000000");
        compTable.put("D|M", "1010101");
    }

    public String dest(String mnem){
        switch (mnem) {
            case "M":
                return "001";
            case "D":
                return "010";
            case "MD":
                return "011";
            case "A":
                return "100";
            case "AM":
                return "101";
            case "AD":
                return "110";
            case "AMD":
                return "111";
            default:
                return "000";
        }
    }
    public String comp(String mnem){
        return compTable.get(mnem);
    }
    public String jump(String mnem){
        switch (mnem) {
            case "JGT":
                return "001";
            case "JEQ":
                return "010";
            case "JGE":
                return "011";
            case "JLT":
                return "100";
            case "JNE":
                return "101";
            case "JLE":
                return "110";
            case "JMP":
                return "111";
            default:
                return "000";
        }
    }
}
