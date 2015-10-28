import java.util.Hashtable;

/**
 * Created by sam on 10/26/15.
 */
public class SymbolTable {
    private Hashtable<String, Integer> symTable = new Hashtable<>();
    public SymbolTable() {
        symTable.put("SP",0);
        symTable.put("LCL",1);
        symTable.put("ARG",2);
        symTable.put("THIS",3);
        symTable.put("THAT",4);
        for (int i = 0; i<16; i++){
            String sym = "R" + String.valueOf(i);
            symTable.put(sym,i);
        }
        symTable.put("SCREEN",16384);
        symTable.put("KBD",24576);
    }

    public void addEntry(String sym, Integer addr){
        symTable.put(sym,addr);
    }

    public Boolean contains(String sym){
        return symTable.contains(sym);
    }

    public int GetAddress(String sym){
        return symTable.get(sym);
    }
}
