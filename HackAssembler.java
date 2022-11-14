import java.io.*;
import java.util.Scanner;


/**
 * @author cheeseman1997
 */
public class HackAssembler {
    private final SymbolTable symbolTable;  // stores pre-defined and user-defined symbols and labels
    private int currentline;           // keeps track of the current valid line during file I/O
    private FileParser parser;              // parses Hack Assembly commands into individual parts

    // constructor
    public HackAssembler() {
        symbolTable = new SymbolTable();
        currentline = 0;
    } //end of constructor

        //Performing the first pass on the file specified by filename. Adding labels to the SymbolTable when first appears
    private void firstPass(final String filename){
        //trying to read the file with bufferedReader
        try{
          final BufferedReader br = new BufferedReader(new FileReader(filename));
          boolean successParse;
          String line;
          while ((line = br.readLine()) != null){
              parser = new FileParser();
              successParse = parser.parse_command(line);
                if (successParse){ //only do anything if the parse is successfull
                 if(line.trim().charAt(0) == '('){} //check if it is a label. A label looks like this: (LOOP)
                 //basically it checks if the first character is a "("
                 // extract the label's text into a string
                    final String labelText = line.trim().substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
                 //it extracts the characters between the "(" and the ")" and stores it in the labelText variable
                 //add labelText to the SymbolTable, if it is not added yet
                    if (!symbolTable.containsThat(labelText)){
                        symbolTable.put(labelText, currentline);

                        currentline--; //label declarations DO NOT COUNT!
                    }
                 else{ //it is not a label
                     currentline++; //increment the current line
                 }
          }
                br.close(); //always close the reader
        }
        } catch (final IOException e) {
            System.out.println("Something went wrong while reading the file");
    }
    }
    //end of firstPass method
    //translating a Hack Assembly file (.asm file) into machine code (.hack file)
    // according to the Hack Machine Language specifications, after the first pass.
    private void translate(final String filename) {
        try {
            final String outputFilename  = filename.substring(0, filename.indexOf(".")) + ".hack";
            //changing the file extension from .asm to .hack
            final BufferedReader br = new BufferedReader(new FileReader(filename));
            final PrintWriter pw = new PrintWriter(outputFilename );

            currentline = 0; // reset counter for current line
            boolean successParse; // flag for parsing error
            String line;
            //read the file line by line
            while ((line = br.readLine()) != null) {
                parser = new FileParser();
                successParse = parser.parse_command(line);

                if (successParse && line.trim().charAt(0) != '(') { // label declarations don't count
                    if (parser.address() == null) { // parsing a C-instruction
                        final String comp = HelpTables.getComputationCode(parser.computation());
                        final String dest = HelpTables.getDestinationCode(parser.destination());
                        final String jump = HelpTables.getJumpCode(parser.jump());
                        pw.printf("111%s%s%s\n", comp, dest, jump);
                    } else { // parsing an A-instruction
                        final String Astruction = parser.address();

                        final Scanner sc = new Scanner(Astruction);
                        if (sc.hasNextInt()) { // check if var is an integer
                            final String binaryAddress = Integer.toBinaryString(Integer.parseInt(Astruction)); // convert to
                            // binary
                            pw.println(padsBinary(binaryAddress)); // write 16-bit binary to output
                        } else {
                            symbolTable.addVariable(Astruction);
                            final String addr_binary = Integer.toBinaryString(symbolTable.get(Astruction));
                            pw.println(padsBinary(addr_binary));
                        }
                        sc.close();
                    }
                    currentline++;
                }
            }
            br.close();
            pw.close();
        } catch (final IOException ioe) {
            System.out.println("Something went wrong while translating the file");
            return;
        }
    } // end translate

    //Pads a binary String with zeros to ensure 16-bit binary format
    //parameter: unpaddedBinary: The binary String without leading zeros
    //returns A 16-bit binary String with leading zeros where necessary
    private String padsBinary(final String unpaddedBinary) {
      String paddedBinary = "";
      final int zeroNums = 16 - unpaddedBinary.length();
      for (int i = 0; i < zeroNums; i++) {
        paddedBinary += "0";
      }
      return paddedBinary + unpaddedBinary;
    } //end padding
        public static void main(final String[] args) {
            final String filename = args[0];
            final HackAssembler assembly = new HackAssembler();
            assembly.firstPass(filename);
            assembly.translate(filename);
        } // end main
    } // end HackAssembler class