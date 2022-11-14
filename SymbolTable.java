import java.util.*;
//symbol table
//the SymbolTable is a table that stores symbols: predefined variables user-defined variables and labels

/**
 * SymbolTable
 * the SymbolTable is a table that stores symbols: predefined variables user-defined variables and labels
 * step 1: create a symbol table
 * step 2: create constructor
 * step 3: add predefined variables to the SymbolTable (R1 - R15)
 * step 4: add predefined symbols (SP, LCL, ARG, THIS, THAT, SCREEN, KBD)
 * step 5: add user-defined variables to the SymbolTable (register starts from 16)
 * step 6: create "put" interface which is used to add a new key-value pairs to SymbolTable
 * step 7: create "containsThat" interface which is used to check if the SymbolTable contains a specific key
 * step 8: create "get" interface which is used to get the value of a specific key in the SymbolTable.
 * It is a getter and the last step too
 */
public class SymbolTable {
    private int currentRegister; //stores the register address to which the next variable is assigned
    private final Hashtable<String, Integer> symbolTable; //stores the symbol table

    //constructor of the SymbolTable
    public SymbolTable() {
        currentRegister = 16; //the first 15 registers are reserved for the predefined variables.
        // The first user-defined variable is assigned to register 16
        symbolTable = new Hashtable<String, Integer>(25); //initializes the symbol table
        //adds the predefined variables to the symbol table
        //These are R1 R2 R3 R4 R5 R6 R7 R8 R9 R10 R11 R12 R13 R14 R15
        for (int i = 0; i <= 15; i++) {
            symbolTable.put("R" + i, i);
        }
        //adds the predefined symbols to the symbol table
        symbolTable.put("SCREEN", 16384); //SCREEN is at address 16384
        symbolTable.put("KBD", 24576); //KBD is at address 24576 (KBD = keyboard)
        symbolTable.put("SP", 0); //SP is at address 0
        symbolTable.put("LCL", 1); //LCL is at address 1
        symbolTable.put("ARG", 2); //ARG is at address 2
        symbolTable.put("THIS", 3); //THIS is at address 3
        symbolTable.put("THAT", 4); //THAT is at address 4
    }   //constructor ends

    //Adds a variable defined by 'symbol' if not present in the SymbolTable
    //New variables get the value of 'current_register' starting from 16.
   public boolean addVariable(final String symbol) {
        //checks if the symbol is already present in the symbol table
        if (symbolTable.containsKey(symbol)) {
            return false; //returns false if the symbol is already present in the symbol table
        } else {
            symbolTable.put(symbol, currentRegister); //adds the symbol to the symbol table
            currentRegister++; //increments the current register
            return true; //returns true if the symbol is added to the symbol table
        }
    }

    //put interface is used to add a new key-value pairs to SymbolTable
    public void put(final String symbol, final int value) {
        symbolTable.put(symbol, value);
    }


    //this interface is used to check if the symbol is present in the symbol table
    public boolean containsThat(final String symbol) {
        return symbolTable.containsKey(symbol);

    }

    //this interface is used to get the value of the symbol from the symbol table
    //it is basically a getter function
    public int get(final String symbol) {
        return symbolTable.get(symbol);
    }
}


