//FileParser class is responsible unpacking a string of instructions into fileds
/**
 * FileParser class is responsible unpacking a string of instructions into fileds
 * step 1: create variable (all are private) to store the instructions (jump, dest, comp, and addr)
 * step 2: create a constructor to initialize jump and destination to null
 * step 3: create a method to parse the instruction
 * step 3.1: check if the line is not empty
 * step 3.2: check if the line is an "a" instruction
 * if it is then siply store the address in the addr variable
 * step 3.3: check if the line is a "c" instruction
 * step 3.3.1: check if the line contains a "=". if it does, it has a destination. The destination is the part before the "="
 * step 3.3.2: check if the line contains a ";". if it does, it has a jump. The jump is the part after the ";"
 * step 3.3.3: remove both comments and whitespaces
 * step 3.3.4: check if the line either contains a "+" or a "-". if it does, it is a comp instruction.
 * The result is stored in the computation variable
 * step 3.3.4.1: if this line has a ";" then it has a jump. The jump is the part after the ";"
 * step 3.3.5: check if the line contains a ";". if it does ,it contains jump field and may contain comp field
 * remove the comments and whitespaces
 * create "split_jump". It is a helper function which deals with the jump field
 * create getters for all the variables
 */

public class FileParser {
    //creating the variables for:
    //jump instruction
    //destination instruction
    //computation instruction
    //16-bit address
    private String jump; //jump instruction
    private String destination;  //destination instruction
    private String computation; //computation instruction
    private String address; //this holds the 16-bit address

    //constructor for the FileParser class
    //first there is no jump nor a destination instruction
    public FileParser() {
        jump = "null";
        destination = "null";
    } //end of constructor

    //Parses a line of assembly instructions into difference fields
    public boolean parse_command(String line) {
        line = line.trim(); // remove leading and trailing whitespace

        if (!line.isEmpty()) {
            if (line.charAt(0) != '/') {
                if (line.contains("@")) { //it is an A-instruction. Its address is stored in the address variable
                    address = line.split("@")[1].trim(); //  it could be a label, variable or number representing an address
                } else { // it is a C-instruction
                    if (line.contains("=")) { // contains dest and comp fields; may contain a jump field
                        final String[] fields = line.split("=");
                        destination = fields[0];
                        if (fields[1].contains(";")) { // checking for jump field
                            splitJump(fields[1]);
                        } else {
                            computation = fields[1].split("/")[0].trim(); // remove comments and whitespace
                        }
                    } else if (line.contains("+") || line.contains("-")) { // contains comp field; may contain a jump
                        // field
                        if (line.contains(";")) { // checking for jump field
                            splitJump(line);
                        } else {
                            computation = line.split("/")[0].trim(); // remove comments and whitespace
                        }
                    } else if (line.contains(";")) { // contains jump field; may contain comp field
                        splitJump(line);
                    } else {
                        jump = line.split("/")[0].trim(); // remove comments and whitespace
                    }
                }
                return true; // successfully parsed
            }
        }
        return false; // parsing unsuccessful
    } //end of parse_command method
    private void splitJump(final String str) {
        final String[] parts = str.split(";");
        computation = parts[0].trim();
        jump = parts[1].split("/")[0].trim();
    }//end of splitJump method

    //getters for the variables
    //return the destination field
    public String destination() {
        return destination;
    } //end of Destination

    //return the computation field
    public String computation() {
        return computation;
    } //end of Computation

    //return the jump field
    public String jump() {
        return jump;
    } //end of Jump

    //return the address field
    public String address() {
        return address;
    } //end of Address
}//end of FileParser

