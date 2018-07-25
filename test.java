/** Class SparseVector **/
public class test {
	public static void main(String[] args) throws Exception {

		BitSet bs = new BitSet(65);
        // assign values to bs2
	    bs.set(4);
	    bs.set(6);
	    bs.set(5);
	    bs.set(1);
	    bs.set(2);
	    bs.set(4);
	    
	    try {
	        bs.set(-1);
	    } catch (IndexOutOfBoundsException e) {
	    	System.err.println("caught trying to set -1");
	    };
	    
	    try {
            bs.set(256);
        } catch (IndexOutOfBoundsException e) {
    	    System.err.println("caught trying to set 256");
        };
        
        System.out.println("\ntesting clear, should be true/false\n");
        System.out.println(bs.get(4));
        bs.clear(4);
        System.out.println(bs.get(4));
        
	    System.out.println(bs.words.length);
	    
		BloomFilter bloom = new BloomFilter(30, 0.1);

 		String str1 = "neil";
		String str2 = "thom";
		String str3 = "jonny";
		String str4 = "colin";
		String str5 = "phil";
		String str6 = "ed";
		String str7 = "marzi";

		byte[] b1 = str1.getBytes();
		byte[] b2 = str2.getBytes();
		byte[] b3 = str3.getBytes();
		byte[] b4 = str4.getBytes();
		byte[] b5 = str5.getBytes();
		byte[] b6 = str6.getBytes();
		byte[] b7 = str7.getBytes();

        System.out.print("\n--adding 1-2:\n");
				
		bloom.add(b1);
		bloom.add(b2);
		
		System.out.print(bloom.contains(b1) + "\n");
		System.out.print(bloom.contains(b2) + "\n");
        System.out.print(bloom.contains(b3) + "\n");
        System.out.print(bloom.contains(b4) + "\n");
		System.out.print(bloom.contains(b5) + "\n");
        System.out.print(bloom.contains(b6) + "\n");
		System.out.print(bloom.contains(b7) + "\n");

		System.out.println("\n\n size, fpp:\n");
		System.out.println(bloom.size());
		System.out.printf("%.20f", bloom.fpp());
		
        System.out.print("\n--adding 3-6:\n");
        
        bloom.add(b3);
		bloom.add(b4);
		bloom.add(b5);
		bloom.add(b6);
		
		System.out.print(bloom.contains(b1) + "\n");
		System.out.print(bloom.contains(b2) + "\n");
        System.out.print(bloom.contains(b3) + "\n");
		System.out.print(bloom.contains(b4) + "\n");
		System.out.print(bloom.contains(b5) + "\n");
        System.out.print(bloom.contains(b6) + "\n");
		System.out.print(bloom.contains(b7) + "\n");
        
		System.out.println("\n\n size, fpp:\n");
		System.out.println(bloom.size());
		System.out.printf("%.20f\n", bloom.fpp());
		
    }
}
