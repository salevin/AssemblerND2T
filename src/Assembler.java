import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Assembler {

    public static void main(String[] args) {
        // If there's a command line argument, assume it's an ASM file and
        // assemble it.
        if (args.length != 0) {
            new Assembler(args[0]);
            return;
        }

        // Otherwise, pop-up a JFileChooser to allow the user to use a GUI
        // to select the ASM file.

        //Schedule a job for the event dispatch thread:
        //creating and showing the assembler's JFileChooser.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                JFileChooser fc = new JFileChooser();
                // Only allow the selection of files with an extension of .asm.
                fc.setFileFilter(new FileNameExtensionFilter("ASM files", "asm"));
                // Uncomment the following to allow the selection of files and
                // directories.  The default behavior is to allow only the selection
                // of files.
                //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    new Assembler(fc.getSelectedFile().getPath());
                else
                    System.out.println("No file selected; terminating.");
            }
        });
    }

    private Assembler(String asmFile) {
        // ASM file name without the .asm extension.
        String basename = new String(asmFile.substring(0, asmFile.indexOf('.')));
        // SymbolTable class.
        SymbolTable symbolTable = new SymbolTable();
        Parser parser = new Parser(asmFile);
        // Code class.
        Code code = new Code();
        // Output file
        FileWriter hackFile;
        // Memory location of next variable
        int nextVariableLoc = 16;
        // Memory location of next assembly command.
        int nextCommandLoc = 0;

        // First pass.
        while (parser.hasMoreCommands()) {
            if (parser.commandType() == CommandType.L_COMMAND) {
                symbolTable.addEntry(parser.symbol(), nextCommandLoc);
            } else {
                nextCommandLoc++;
            }
            parser.advance();
        }
        parser.reset();

        try {
            hackFile = new FileWriter(basename + ".hack");

            // Second pass.
            String binary;
            while (parser.hasMoreCommands()) {
                binary = null;
                if (parser.commandType() == CommandType.C_COMMAND) {
                    binary = "111" + code.comp(parser.comp());
                    binary += code.dest(parser.dest());
                    binary += code.jump(parser.jump());
                }
                else if (parser.commandType() == CommandType.A_COMMAND){
                    String sym = parser.symbol();
                    if (Character.isDigit(sym.charAt(0))) {
                        binary = toImmediate(Integer.parseInt(sym));
                    }
                    else {
                        if (symbolTable.contains(sym)){
                            binary = toImmediate(symbolTable.GetAddress(sym));
                        }
                        else {
                            symbolTable.addEntry(sym,nextVariableLoc);
                            binary = toImmediate(nextVariableLoc);
                            nextVariableLoc++;
                        }
                    }
                }
                if (binary != null) {
                    hackFile.write(binary);
                }
                parser.advance();
            }

            // Confirm that the HACK file is being written

            hackFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    // You might find this useful.
    // Convert the decimal value val to a 15 bit binary string.  Assume that
    // val's value lies between 0 and 32,767, inclusive.  In other words, assume
    // that val is non-negative and will fit into a 15 bit number.
    private String toImmediate(int val) {
        String field = "";

        for (int i = 0; i < 15; i++) {
            field = (val % 2) + field;
            val /= 2;
        }
        return field;
    }
}
