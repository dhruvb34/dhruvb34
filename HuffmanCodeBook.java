import java.util.Objects;

/**
 * This class is defined to store pairs of char and Binary Sequence. The class is defined as a hashtable.
 */
public class HuffmanCodeBook {
    /**
     * This private class is defined as a node class
     */
    private static class HashNode {
        private char key;   //to store key
        private BinarySequence val; //to store val
        private HashNode next;  //to get the next node

        /**
         * Constructor for the HashNode
         * @param key - char
         * @param val - Binary Sequence
         * @param next - next node
         */
        private HashNode(char key, BinarySequence val, HashNode next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }

        /**
         * get node's key
         * @return - char
         */
        private char getKey() {
            return key;
        }

        /**
         * get node's value
         * @return - Binary Sequence
         */
        private BinarySequence getVal() {
            return val;
        }

        /**
         * set node's key
         * @param key - char
         */
        private void setKey(char key) {
            this.key = key;
        }

        /**
         * set node's value
         * @param val - Binary Sequence
         */
        private void setVal(BinarySequence val) {
            this.val = val;
        }

        /**
         * get node's next node
         * @return - node
         */
        private HashNode getNext() {
            return next;
        }
        /**
         * set node's next node
         * @param next - node
         */
        private void setNext(HashNode next) {
            this.next = next;
        }

    }
    private int itemCount;              // number of key/val pairs in the table
    private int bookSize;               // size of the hashtable
    private HashNode[] HashNodes;       //array of HashNodes

    /**
     * Constructor for the HuffmanCodeBook
     */
    public HuffmanCodeBook() {
        this.bookSize = 5;                          //DEFAULT SIZE
        this.itemCount = 0;                         //initial count
        this.HashNodes = new HashNode[bookSize];    //new array of HashNodes
    }

    public HuffmanCodeBook(int bookSize){
        this.bookSize = bookSize;
        this.itemCount = 0;                         //initial count
        this.HashNodes = new HashNode[bookSize];    //new array of HashNodes
    }

    /**
     * This method adds a key/val pair in the codebook by first finding the index to put the pair in the table. This
     * index is found by dividing the key's hashcode by the book size. If the item count to book size ratio is more
     * than 3 then the codebook's size is increased by calling the expand function.
     * @param key - char
     * @param val - binary sequence
     */
    public void addSequence(char key, BinarySequence val) {
        if (itemCount / bookSize > 1){  //if ratio is more than 3
            expand();
        }
        int index = Character.hashCode(key) % bookSize;  //get index
        if (HashNodes[index] == null) {         //if the first element is null, put the pair there
            HashNodes[index] = new HashNode(key, val, null);
            itemCount++;    //increment item count
            return;
        }
        HashNode hashNode = HashNodes[index];   //go to the index
        while (hashNode.getNext() != null) {
            hashNode = hashNode.getNext();  //go to the end of the hash node's array
        }
        hashNode.setNext(new HashNode(key, val, null));   //put at the end of the array
        itemCount++;
    }

        /**
     * This method is defined to check if the huffman code book contains a given letter.
     * @param key - a letter
     * @return - boolean
     */
    public boolean contains(char key) {
        int index = Character.hashCode(key) % bookSize;   //get index
        HashNode hashNode = HashNodes[index];
        while (hashNode != null) {              //loop through the HashNode array
            if (Objects.equals(hashNode.getKey(), key)) {
                return true;                //if found
            }
            hashNode = hashNode.getNext();
        }
        return false;   //not found
    }

    /**
     * This method is defined to check if all the letters in a given input string are in the codebook
     * @param letters - a string
     * @return - boolean
     */
    public boolean containsAll(String letters){
        if (letters.length() == 1){ //string is a space character
            return contains(letters.charAt(0));     // check for space character
        }
        if (letters.length() == 0){  //string is empty
            return true;            //always true
        }
        for (int i = 1; i < letters.length(); i++){
            if (contains(letters.charAt(i - 1))){
                return contains(letters.charAt(i));  //if all the letters in input string are in the codebook
            }
        }
        return false;
    }

    /**
     * This method is defined to check if a given letter is in the codebook has that letter's binary sequence.
     * If the letter is present, the binary sequence associated with the letter is returned, else null is returned.
     * @param key - a letter
     * @return - binary sequence
     */
    public BinarySequence getSequence(char key) {
        int index = Character.hashCode(key) % bookSize;   //get index
        HashNode hashNode = HashNodes[index];
        while (hashNode != null) {       //loop through the HashNode array
            if (Objects.equals(hashNode.getKey(), key)) {
                return hashNode.getVal();  //if found
            }
            hashNode = hashNode.getNext();
        }
        return null;    //else not found
    }
    /**
     * This method is defined to encode a given string to a binary sequence. This is done by creating a new empty binary
     * sequence and then appending the letter's binary sequence to the empty sequence.
     * @param s -  a string
     * @return -  binary sequence
     */
    public BinarySequence encode(String s){
        BinarySequence encodedSequence = new BinarySequence();   //a new encoded sequence
        for (char c: s.toCharArray()) {                         //all chars in the input string
            //append a given char's binarySequence to encodedSequence if present
            if (contains(c)) {
                encodedSequence.append(getSequence(c));
            }
        }
        return encodedSequence;
    }

    /**
     * This method is a helper function to get all the letters in the codebook.
     * @return - char array
     */
    public char[] getHuffmanChars(){
        int huffmanCharsLength = 0;
        char[] huffmanChars = new char[itemCount];  // an array of length - no. of pairs
        for (int i = 0; i < bookSize; i++){ //loop through the hash table
            HashNode hashNode = HashNodes[i];
            while (hashNode != null){
                huffmanChars[huffmanCharsLength] = hashNode.getKey();   //add to the char array
                hashNode = hashNode.getNext();
                huffmanCharsLength++;
            }
        }
        return huffmanChars;
    }

    /**
     * This method is defined to show the structure of the codebook.
     */
    public void showStructure() {
        System.out.println("ItemCount: " + itemCount);  //show no. of pairs in hashtable
        System.out.println("BookSize: " + bookSize);    //show the codebook's size
        System.out.println("LoadFactor: " + itemCount / bookSize);  //ratio of item count to book size

        for (int i = 0; i < bookSize; i++) {   //loop through the table
            System.out.print(" : " + i + " ");
            HashNode hashNode = HashNodes[i];
            System.out.print("[");
            while (hashNode != null) {     //loop through individual arrays
                System.out.print("(" + Character.hashCode(hashNode.getKey())
                        + " :: '" + hashNode.getKey() + "' : " + hashNode.getVal() + ") "); //print contents
                hashNode = hashNode.getNext();
            }
            System.out.print("]");
            System.out.println();
        }
    }

    /**
     * This private method is defined to get the next prime that is at least twice the current number. This is helpful
     * to determine the size of the codebook if needed to be expanded.
     * @param num - current num
     * @return - a new prime
     */

    private int nextPrime(int num) {
        for (int i = 2; i < num / 2; i++) {   //gets the integers between 2 and given number/2
            if (num % i == 0) {    //checks if it is not prime
                return nextPrime(num + 1); //calls a recursion
            }
        }
        return num; //found the next prime
    }

    /**
     * This private method is defined to expand the codebook's size and re-add all the key/value pairs again.
     */
    private void expand() {
        HuffmanCodeBook tempBook = new HuffmanCodeBook(nextPrime(2*bookSize + 1));   //create a new temporary codebook
        for (int i = 0; i < bookSize; i++){     //loop through the current codebook
            HashNode hashNode = HashNodes[i];
            HashNodes[i] = null;    //set the hashnode to null
            while (hashNode != null){
                tempBook.addSequence(hashNode.getKey(), hashNode.getVal()); //add the pair to the new codebook
                hashNode = hashNode.getNext();
            }
        }
        itemCount = 0;  //update the item count
        bookSize = tempBook.bookSize;   //update the book size
        HashNodes = new HashNode[bookSize];     //update the current hashnode arrays to the new
        for (int i = 0; i < bookSize; i++){
            HashNode hashNode = tempBook.HashNodes[i];
            while (hashNode != null){
                //add the sequence from the temp codebook to the current codebook
                addSequence(hashNode.getKey(), hashNode.getVal());
                hashNode = hashNode.getNext();
            }
        }
    }
}


