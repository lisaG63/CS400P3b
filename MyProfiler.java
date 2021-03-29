
/**
 * MyProfiler created by Weihang Guo on MacBook in p3b
 * 
 * Author: Weihang Guo(wguo63@wisc.edu)
 * Date:   @3.27
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 002
 * 
 * IDE: Eclipse IDE for Java Developers
 * Version: 2019-12(4.14.0)
 * Build id: 20191212-1212
 * 
 * Device: LisaG's MACBOOK
 * OS: macOS Mojave
 * Version: 10.14.4
 * OS Build: 1.8 GHz Intel Core i5
 * 
 * List Collaborators: None
 * 
 * Other Credits: None
 * 
 * Known Bugs: None
 */

import java.util.TreeMap;

/**
 * MyProfiler - test the performance of my hash table against Tree Map
 * @author Weihang Guo
 * 
 * @param <K> A Comparable type to be used as a key to an associated value.
 * @param <V> A value associated with the given key.
 */
public class MyProfiler<K extends Comparable<K>, V> {

	HashTableADT<K, V> hashtable;
	TreeMap<K, V> treemap;

	/**
	 * the constructor of this profiler
	 */
	public MyProfiler() {
		hashtable = new HashTable<K, V>(317, 1.5);
		treemap = new TreeMap<K, V>();
	}

	/**
	 * insert key, value paris into hashtable and treemap
	 * @param key the key to be inserted
	 * @param value the value to be inserted
	 * @throws IllegalNullKeyException if the key is null
	 */
	public void insert(K key, V value) throws IllegalNullKeyException {
		hashtable.insert(key, value);
		treemap.put(key, value);
	}

	/**
	 * retrieve the value with the given key
	 * @param key the key associated with the value
	 * @throws IllegalNullKeyException if the key is null
	 * @throws KeyNotFoundException if the key is not found
	 */
	public void retrieve(K key) throws IllegalNullKeyException, KeyNotFoundException {
		hashtable.get(key);
		treemap.get(key);
	}

	/**
	 * Main method of the program
	 * 
	 * @param args the string arguments from the command line
	 */
	public static void main(String[] args) {
		try {
			int numElements = Integer.parseInt(args[0]);
			MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();
			for (int i = 0; i < numElements; i ++) {
				profile.insert(i, i);
				
			}
			for (int i = 0; i < numElements; i ++) {
				profile.retrieve(i);
				
			}

			String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
			System.out.println(msg);
		} catch (Exception e) {
			System.out.println("Usage: java MyProfiler <number_of_elements>");
			System.exit(1);
		}
	}
}
