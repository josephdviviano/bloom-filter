bloom filter
------------

For BitSet implementation, you have to think about memory. Is a boolean in Java
really represented with 1 bit? Moreover, what about the manipulation of bits in
a programming language?

- use boolean -- https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
- use XOR to add values? -- https://hackernoon.com/xor-the-magical-bit-wise-operator-24d3012ed821

  """
  In short, it means that it returns 1 only if exactly one bit is set to 1 out
  of the two bits in comparison ( Exclusive OR ).
  """

- OR to add, AND to query: https://stackoverflow.com/questions/33558226/bloom-filter-with-bitwise-operations-in-python
- delete operation: 'counting' bloom filtr: http://matthias.vallentin.net/blog/2011/06/a-garden-variety-of-bloom-filters/
- bloom filters in java: https://www.javamex.com/tutorials/collections/bloom_filter_java.shtml
- bloom filters use 44% more disk space than optimal, and calcs for union +
  intersection of sets, and FPR calculations: https://en.wikipedia.org/wiki/Bloom_filter
- java has a BitSet.java class I should look at.
- bit manipulation: https://en.wikipedia.org/wiki/Bit_manipulation
- masks (e.g., to clear filter): https://en.wikipedia.org/wiki/Mask_(computing)
- bit manipulation in java https://www.vojtechruzicka.com/bit-manipulation-java-bitwise-bit-shift-operations/
- bloom filter FPR: https://www.javamex.com/tutorials/collections/bloom_filter_false_positives.shtml
- implementation (vanilla): https://codereview.stackexchange.com/questions/41589/bloom-filter-implementation
- implementation (vanilla): https://github.com/Baqend/Orestes-Bloomfilter/blob/master/src/main/java/orestes/bloomfilter/BloomFilter.java
- implementation (counting): https://github.com/Baqend/Orestes-Bloomfilter/blob/master/src/main/java/orestes/bloomfilter/CountingBloomFilter.java
- implementation (vanilla): https://www.programcreek.com/java-api-examples/index.php?source_dir=libelula-master/LibelulaLogger/trunk/src/orestes/bloomfilter/BloomFilter.java
- implementation (vanilla): http://blog.bimarian.com/bloom-filters-implementation-in-java/
- size of array of booleans: https://stackoverflow.com/questions/8959319/is-an-array-of-one-boolean-in-java-smaller-than-a-standalone-variable
- hashing twice (or once): https://willwhim.wpengine.com/2011/09/03/producing-n-hash-functions-by-hashing-only-once/
- cheap hash fxn (FNV): https://en.wikipedia.org/wiki/Fowler%E2%80%93Noll%E2%80%93Vo_hash_function

