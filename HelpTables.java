import java.util.*;

//HelpTables class: contains the dest table, comp table and jump table. Both initial and  getter ones

/** Helper tables: contains the dest table, comp table and jump table. Also contains the getters
 * Step 1: declare 3 hashtables: destTable, compTable and jumpTable
 * Step 2: initiaize the initial hashtables: initialJumpTable, initialCompTable and initialDestTable.
 * Fill them with the data like this: https://drive.google.com/file/d/1nEptWuRpFF9zmqlKYq6s1UfDB_dd16vx/view slide 33
 * Step 3: create getters for the 3 hashtables: getDestTable, getCompTable and getJumpTable. All of them returns a String
 */
public class HelpTables {
    private static Hashtable <String, String> destTable = new Hashtable <String, String>(8);
    private static Hashtable <String, String> compTable = new Hashtable <String, String>(28);
    private static Hashtable <String, String> jumpTable = new Hashtable <String, String>(8);

    //initalizing the tables
    //table for destination codes.
    private static void initialDestTable(){
        destTable.put("null", "000");
        destTable.put("M", "001"); //Memory (RAM)
        destTable.put("D", "010"); //D resgister
        destTable.put("MD", "011");
        destTable.put("A", "100"); //A register
        destTable.put("AM", "101");
        destTable.put("AD", "110");
        destTable.put("AMD", "111");
    } //end of destTable method

    //table for jump codes.
    private static void initialJumpTable(){
        jumpTable.put("null", "000"); //no jump
        jumpTable.put("JGT", "001"); //only jump if it is GREATER than zero
        jumpTable.put("JEQ", "010"); //only jump if it is EQUAL to zero
        jumpTable.put("JGE", "011"); //jump if it is GREATER than or EQUAL to zero
        jumpTable.put("JLT", "100"); //only jump if it is LESS than zero
        jumpTable.put("JNE", "101"); //only jump if it is NOT EQUAL to zero
        jumpTable.put("JLE", "110"); //jump if it is LESS than or EQUAL to zero
        jumpTable.put("JMP", "111"); //unconditional jump
    } //end of jumpTable method

    //table for computation codes.
    private static void initialCompTable(){
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
    } //end of compTable method

    //getters for the tables
    //getter for comp Table
    //Gets the binary code representing the computation bits given by the symbolic key
    //Returns a 7-bit binary String representing the computation instruction
    public static String getComputationCode (final String key){
        initialCompTable();
        return compTable.get(key);
    } //end of getComputationCode method

    //getter for dest Table
    //Gets the binary code representing the destination instruction given by the symbolic key
    //Returns a 3-bit binary String representing the destination instruction
    public static String getDestinationCode (final String key){
        initialDestTable();
        return destTable.get(key);
    } //end of getDestinationCode method

    //getter for jump Table
    //Gets Retrieves the binary code representing the jump instruction given by the symbolic key
    //Returns a 3-bit binary String representing the jump instruction
    public static String getJumpCode (final String key){
        initialJumpTable();
        return jumpTable.get(key);
    }

}
