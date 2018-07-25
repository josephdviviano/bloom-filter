/**
 * @author Joseph Viviano - 20115694
 * @author Marzieh Mehdizadeh - C8478
 *
 * REFERENCES: the following are referenced throughout the code --
 * SOURCE = http://hg.openjdk.java.net/jdk7/jdk7/jdk/file/00cd9dc3c2b5/src/share/classes/java/util/BitSet.java
 * BITOPS = https://www.programiz.com/java-programming/bitwise-operators 
 * other one-time references are found throughout.
 */

public class BitSet {
    /**
     * Create a set of bits of a certain size, initialized {@code false}.
     * BitSets are packed into arrays of "words", with each word being a long. 
     * Each word contains 64 bits and requires 6 address bits (2**6 = 64).
     *
     * @param nbits initial size of the set
     */
	private static int ADDRESS_BITS_PER_WORD = 6;
	public int nbits;
	public long[] words;
	
    /** 
     * Constructor: Array of longs, each element representing 64 bits. 
     * This is all 0 as per the language spec.
     * REF: https://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.12.5
     */
	public BitSet(int nbits) {           
        if (nbits < 0)
            throw new NegativeArraySizeException("nbits must be > 0.");

        this.nbits = nbits;
        // getWordIndex uses bitwise operation version of Math.ceil(nbits / TOTAL_BITS_PER_WORD)
        this.words = new long[getWordIndex(nbits - 1) + 1]; 
    }
    
    
    /** 
     * Returns a word index for the input bitIndex.
     * REF: https://www.vojtechruzicka.com/bit-manipulation-java-bitwise-bit-shift-operations/
     * 
     * @param bitIndex the bit index
     * @return a word index for the given bitIndex.
     */
    private static int getWordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }        

    /**
     * Calculates the bitIndex for a word. Since shift on bitIndex loops from 
     * 0-63, there is no need to account for the  wordIndex when calculating 
     * the mask. REF: https://en.wikipedia.org/wiki/Mask_(computing)
     * 
     * @param bitIndex the bit index
     * @return a long representing a binary mask with exactly one bit set to 1.
     */
    private long getMask(int bitIndex) {
    	return (1L << bitIndex);
    }
    
    /**
     * Ensures bitIndex is a valid value.
     * @param bitIndex
     */
    public void checkBitIndex(int bitIndex) {
        if (bitIndex < 0)
            throw new IndexOutOfBoundsException("bitIndex must be >= 0.");
        
        if (bitIndex > this.nbits)
        	throw new IndexOutOfBoundsException("bitIndex must be < nbits used in constructor.");
    }
    
    /**
     * Returns the value of the bit the specified index by checking whether 
     * the AND (&) of the mask and word is not equal to 0 (therefore value
     * must be set to 1).
     * NB: since shift on bitIndex loops from 0-63, there is no need to account for the 
     *     word index when calculating the mask.
     * REF: line 620 of SOURCE and MASK
     *
     * @param bitIndex the bit index
     * @return the value of the bit the index specified
     */
    public boolean get(int bitIndex) {
    	checkBitIndex(bitIndex);
       	int wordIndex = getWordIndex(bitIndex);
       	long mask = getMask(bitIndex);
    	return ((this.words[wordIndex] & mask) != 0);
    }

    /**
     * Sets the bit to the index specified as {@code true}.
     * 1. find the word to manipulate in words
     * 2. use bitwise OR (|) to set bit in the given word to 1 using a mask (REF: BITOPS)
     * REF: line 441 of SOURCE and MASK
     * 
     * @param bitIndex the bit index
     */
    public void set(int bitIndex) {
    	checkBitIndex(bitIndex);
    	int wordIndex = getWordIndex(bitIndex);
    	long mask = getMask(bitIndex);
        words[wordIndex] |= mask;
    }

    /**
     * Set the bit to the index specified as {@code false}.
     * 1. find the word to manipulate in words
     * 2. use bitwise AND (&) on the negation of the mask to set the location
     *    specified by bitIndex to 0 (all other bits will remain 1 if they 
     *    were already set to 1).
     * REF: line 579 of SOURCE.
     * 
     * @param bitIndex the bit index
     */
    public void clear(int bitIndex) {
    	checkBitIndex(bitIndex);
        int wordIndex = getWordIndex(bitIndex);
        long mask = getMask(bitIndex);
        words[wordIndex] &= ~mask;
    }
}
