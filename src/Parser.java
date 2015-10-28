import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    // Index of the current assembly command in the commands ArrayList.  It is
    // initialized via reset() to -1 to match the Parser's description in the
    // textbook.  Namely, that advance() be called on the Parser _before_ performing
    // any actual parsing.
    private int index;
    private ArrayList<String> commands;

    public Parser(String asmFile) {
	commands = new ArrayList<String>();
	reset();
	fill(asmFile);
    }

    // Reset the parser.  Used prior to the second pass.    
    public void reset() {
	index = 0;
    }

    // Fill the commands list with assembly commands.    
    private void fill(String src) {
	Scanner scanner;
	String line;
	int index;

	try {
	    scanner = new Scanner(new File(src));

	    while (scanner.hasNextLine()) {
		line = scanner.nextLine();
		// Remove all whitespace characters
		line = line.replaceAll("\\s", "");
		// Remove comments
		index = line.indexOf("//");
		if (index != -1)
		    line = line.substring(0, index);
		// At this point, the line might be empty.
		// If it isn't empty, add it to the commands list.
		if (line.length() > 0) {
		    commands.add(line);
		    // Uncomment the following Java statement to see the
		    // assembly lines being stored in the commands ArrayList.
		    //System.out.println(line);
		}
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.exit(1);
	}

    }

	public boolean hasMoreCommands(){
		return (index < commands.size());
	}

	public void advance(){
		if (hasMoreCommands()){index++;}
	}

	public CommandType commandType(){
		String currentCommand = commands.get(index);
		if (currentCommand.startsWith("(")) { return CommandType.L_COMMAND; }
		if (currentCommand.startsWith("@")){ return CommandType.A_COMMAND; }
		else { return CommandType.C_COMMAND; }
	}

	public String symbol(){
		String sym = commands.get(index);
		sym = sym.replace("@","").replace("(","").replace(")","");
		return sym;
	}

	public String dest(){
		String currentCommand = commands.get(index);
		if (currentCommand.contains("=")){
			int eq = currentCommand.indexOf("=");
			return currentCommand.substring(0,eq);
		}
		else {return "";}
	}

	public String comp() {
		String currentCommand = commands.get(index);
		if (currentCommand.contains(";") && currentCommand.contains("=")) {
			int semi = currentCommand.indexOf(";");
			int eq = currentCommand.indexOf("=");
			return currentCommand.substring(eq, semi);
		}
		else if (currentCommand.contains(";")){
			int semi = currentCommand.indexOf(";");
			return currentCommand.substring(0, semi);
		}
		else if (currentCommand.contains("=")){
			int eq = currentCommand.indexOf("=");
			return currentCommand.substring(eq+1);
		}
		else {return currentCommand;}
	}

	public String jump() {
		String currentCommand = commands.get(index);
		if (currentCommand.contains(";")){
			int semi = currentCommand.indexOf(";");
			return currentCommand.substring(semi+1);
		}
		else {return "";}
	}
}
