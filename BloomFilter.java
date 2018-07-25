/**
 * @author Joseph Viviano - 20115694
 * @author Marzieh Mehdizadeh - C8478
 */

public class BloomFilter {
    /**
     * Creates a Bloom filter based on the size of the bit set and the number of
     * hash functions.
     *
     * @param numBits size of the bit set
     * @param numHashes number of hash functions
     */
	
	private int counter;  // keeps track of the number of added elements
	private BitSet bits;  // the hash table
	public int numBits;   // number of bits
	public int numHashes; // number of hash functions
	public int numElems;  // number of expected inputs
	
    public BloomFilter(int numBits, int numHashes) {
    	this.numElems = 0; // undefined value
    	this.numBits = numBits;
    	this.bits = new BitSet(numBits);
    	this.numHashes = numHashes;
    }

    /**
     * Creates a Bloom filter based on the number of items expected and the
     * probability of false positives desired.
     * 
     * REF: https://github.com/google/guava/blob/master/guava/src/com/google/common/hash/BloomFilter.java
     *
     * @param numElems number of elements to insert
     * @param falsePosProb probability of false positives64
     */
    public BloomFilter(int numElems, double falsePosProb) {
    	// ensure falsePosProb is non-zero (due to underflow)
    	if (falsePosProb == 0) {
    	    falsePosProb = Double.MIN_VALUE;
        }
    	
    	this.numElems = numElems;
    	
    	// number of bits -- m = -n ln(p) / ln2^2 
    	this.numBits = (int) (-this.numElems * Math.log(falsePosProb) / (Math.log(2) * Math.log(2))); 
        this.bits = new BitSet(numBits);
        
        // number of hash functions -- k = m / n * log2, or 1 to protect against underflow
        this.numHashes = Math.max(1, (int) Math.round((double) this.numBits / this.numElems * Math.log(2)));
    }

    /**
     * Adds an element to the Bloom filter.
     *
     * @param key element to insert
     */
    public void add(byte[] key) {
    	int[] hashes = hashLCG(key);
    	
    	// insert each of the k hashes one by one
    	for (int i = 0; i < hashes.length; i++) {
    		this.bits.set(hashes[i]);
    	}
    	this.counter++; // keep track of this insertion
    }

    /**
     * Look for an element in the Bloom filter.
     *
     * @param key element to find
     * @return is the element is possibly in the filter
     */
    public boolean contains(byte[] key) {
    	int[] hashes = hashLCG(key);
    	
    	// for the k hashes, if any is false in the bloom filter, this is a new element
    	for (int i = 0; i < hashes.length; i++) {
    		if (!this.bits.get(hashes[i])) {
    			return false;
    		}
    	}
    	
    	// otherwise, this is possibly not a new element
        return true;
    }

    /**
     * Set bloom filter to be all zero.
     */
    public void reset() {
    	for (int i = 0; i < this.bits.words.length; i++ ) {
    		this.bits.words[i] = 0L;
    	}
    	this.counter = 0;
    }
    
    /**
     * Return the number of bits in the bloom filter.
     *
     * @return number of bits
     */
    public int size() {
    	return this.numBits;
    }

    /**
     * Return the number of elements inserted into the bloom filter.
     *
     * @return number of inserted elements
     */
    public int count() {
        return this.counter;
    }

    /**
     * Return the false positive rate, fpp = (1 - e^-kn/m)^k
     * REF: https://en.wikipedia.org/wiki/Bloom_filter#Probability_of_false_positives
     *  
     * @return false positive rate
     */
    public double fpp() {
    	return Math.pow(1 - Math.pow(Math.E, -this.numHashes * this.counter / (double) this.numBits), this.numHashes);
    }

    /** Linear Congruential Generators are a method for generating uniformly distributed hashes.
     *  NB: we work in 64 bits while generating our positions, but keep only the 32 least-
     *      significant bits. This is because the higher order bits have longer periods 
     *      and are therefore less uniform.
     *      
    // REF: https://github.com/Baqend/Orestes-Bloomfilter/blob/master/src/main/java/orestes/bloomfilter/HashProvider.java
    // REF: https://en.wikipedia.org/wiki/Linear_congruential_generator
     * REF: https://en.wikipedia.org/wiki/Linear_congruential_generator#Parameters_in_common_use
     *
     * @param data  the bytes to be hashed
     * @param m     hash table size
     * @param k     number of hashes to be computed
     * 
     * @return      array with k hashes
     */
    private int[] hashLCG(byte[] data) {
        long multiplier = 0x5DEECE66DL; // 25214903917 
        long increment = 11;
        long mask = (1L << 48) - 1;
        long hash = FNV1(data);
        int[] positions = new int[this.numHashes];

        /** LCG formula: x_i+1 = (multiplier * x_i + increment) mod tableSize
         *  "& mask" is equivalent to doing "mod x" assuming x is a power of 2, i.e,
         *      long mask = (1L << log_2(x)) - 1; // generates mask of x = 2^log_2(x)
         *  note this is a distinct mod than that used by the hashing function i.e., mod m
         *  REF: https://openmc.readthedocMath.abs(s.io/en/latest/methods/random_numbers.html#linear-congruential-generators
         *  REF: Knuth, The Art of Computer Programming, Vol 2, 3.2.1.
         */ 
        for (int i = 0; i < this.numHashes; i++) {
            hash = (multiplier * hash + increment) & mask; // hash is 64 bit, so is mask
            positions[i] = (int) ((hash >>> (48-32)) % this.numBits); // extract least significant 32 bits, then mod by numBits
        }
        return positions;
    }

    /**
     * A cheap, non-cryptographic way of generating hash functions using 64 bit longs.
     * REF: https://en.wikipedia.org/wiki/Fowler%E2%80%93Noll%E2%80%93Vo_hash_function#FNV-1_hash
     * 
     * @param  a, the byte array to be hashed
     * @return the 64 bit integer hash value
     */
    private static long FNV1(byte a[]) {
        long prime = 0x100000001b3L; // 1099511628211  
        long offset = 0xcbf29ce484222325L; // 14695981039346656037

        // handle missing input values
        if (a == null)
            return 0L;

        long hash = offset;
        for (byte element : a) {
            hash = (hash * prime);
            hash ^= element; // bitwise XOR
        }
        return hash;
    }
}

