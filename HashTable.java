
/**
 * HashTable created by Weihang Guo on MacBook in p3b
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

import java.util.LinkedList;

/**
 * HashTable - A hash table that allocates a place to store data. All the key, value pairs are 
 * stored in an array of linkedList. The index of an given element in the table is obtained by 
 * hashCode % tableSize and convert it to its absolute value.
 * @author Weihang Guo
 * 
 * @param <K> A Comparable type to be used as a key to an associated value.
 * @param <V> A value associated with the given key.
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	
	private Object[] table;//the array containing linked lists of nodes
	private int size;//the current capacity of hash table
	private int items;//the current number of items in the hash table
	private double loadFactorThreshold;//the load factor that causes a resize and rehash
	
	/**
	 * Node - the node in a hash table
	 * @author Weihang Guo
	 * 
	 */
	private class Node {
		
		private K key;//the key of the node
		private V value;//the value of the node
		
		/**
		 * Constructor of Element
		 * @param key key of the node
		 * @param value value of the node
		 */
		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Get key of the node.
		 * @return key of the node.
		 */
		private K getKey() {
			return key;
		}

		/**
		 * Get value of the node.
		 * @return value of the node.
		 */
		private V getValue() {
			return value;
		}
		
		/**
		 * Set value of the node.
		 * @param value value of the node
		 */
		private void setValue(V value) {
			this.value = value;
		}
		
	}

	/**
	 * A default no-arg constructor.
	 */
	public HashTable() {
		size = 11;		
		table = new Object[size];
		//initialize each element in the array to be a linked list
		for (int i = 0; i < size; i ++) {
			table[i] = new LinkedList<Node>();
		}
		items = 0;
		loadFactorThreshold = 0.75;

	}

	/**
	 * A constructor that accepts initial capacity and load factor threshold
	 * @param initialCapacity the initial capacity of the hashtable
	 * @param loadFactorThreshold the load factor that causes a resize and rehash
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		size = initialCapacity;
		table = new Object[size];
		//initialize each element in the array to be a linked list
		for (int i = 0; i < size; i ++) {
			table[i] = new LinkedList<Node>();
		}
		items = 0;
		this.loadFactorThreshold = loadFactorThreshold;
	}

	/**
	 * Insert a key, value pair into the hashtable.
	 * @param key the key of the element
	 * @param value the value of the element
	 * @throw IllegalNullKeyException if the key is null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException {
		if (key == null) //throw IllegalNullKeyException if the key is null
			throw new IllegalNullKeyException();
		Node node = new Node(key, value);//create a new node with the given key and value
		int index = Math.abs(key.hashCode())%size;//find the bucket to which the node will be added
		boolean duplicate = false;//variable that determines whether there is a duplicate key
		//traverse the whole table to find if there is a same key
		for (int i = 0; i < ((LinkedList<Node>)table[index]).size(); i ++) {
			if (((LinkedList<Node>) table[index]).get(i).getKey().equals(key)) {
				//if there is a duplicate key, replace the old value with the new one
				((LinkedList<Node>) table[index]).get(i).setValue(value);
				duplicate = true;
			}
		}
		//there is no duplicate key, simply add the node
		if (duplicate == false) {
			((LinkedList<Node>) table[index]).add(node);
			items ++;
		}
		//resize and rehash when load factor threshold is reached or the table is full
		if (getLoadFactor() > loadFactorThreshold || getLoadFactor() == loadFactorThreshold
				|| items == size) {
			reHash();
		}
		
	}
	
	
	/**
	 * Resize the array and rehash every elements.
	 * @throws IllegalNullKeyException if the key is null
	 */
	@SuppressWarnings("unchecked")
	private void reHash() throws IllegalNullKeyException {
		Object[] tempTable = table;//a temporary table to store the old table's data
		int preSize = size;//the previous table size
		size = size * 2 + 1;//new table size
		table = new Object[size];//new table
		//initialize the elements in the array
		for (int i = 0; i < size; i ++) {
			table[i] = new LinkedList<Node>();
		}
		//insert every node into the new table
		for (int i = 0; i < preSize; i ++) {
			for (int j = 0; j < ((LinkedList<Node>)tempTable[i]).size(); j ++) {
				K key = ((LinkedList<Node>)tempTable[i]).get(j).getKey();
				int index = Math.abs(key.hashCode())%size;
				//find the bucket to which the node will be added
				((LinkedList<Node>) table[index]).add(((LinkedList<Node>)tempTable[i]).get(j));
			}
		}
	}

		
	/**
	 * If key is found, remove the key,value pair from the data structure and decrease number of keys.
	 * @param key the key of the node to be removed
	 * @return true if the node is successfully removed, false if the key is not found
	 * @throw IllegalNullKeyException if key is null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(K key) throws IllegalNullKeyException {
		if (key == null) 
			throw new IllegalNullKeyException();
		int index = Math.abs(key.hashCode())%size;//get the linkedList which the node is located
		//traverse the linkedList to find the node
		for (int i = 0; i < ((LinkedList<Node>) table[index]).size(); i ++) {
			if (((LinkedList<Node>) table[index]).get(i).getKey().equals(key)) {
				((LinkedList<Node>) table[index]).remove(i);
				items --;//decrease the number of items
				return true;
			}
		}
		//key not found
		return false;
	}


	/**
	 * Returns the value associated with the specified key. Does not remove key or decrease number 
	 * of keys.
	 * @param the key of the node to be get
	 * @return the value associated with the specified key
	 * @throw IllegalNullKeyException if key is null
	 * @throw KeyNotFoundException if key is not found
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if (key == null)
			throw new IllegalNullKeyException();
		int index = Math.abs(key.hashCode())%size;//find the bucket which the key is located
		//traverse the linkedList to find the node
		for (int i = 0; i < ((LinkedList<Node>) table[index]).size(); i ++) {
			if (((LinkedList<Node>) table[index]).get(i).getKey().equals(key)) {
				return ((LinkedList<Node>) table[index]).get(i).getValue();
			}
		}
		//key not found
		throw new KeyNotFoundException();
	}
	
	
	/**
	 * Returns the number of key,value pairs in the data structure
	 * @return the number of key,value pairs in the data structure
	 */
	@Override
	public int numKeys() {
		return items;
	}


	/**
	 * Returns the load factor threshold that was passed into the constructor when creating the 
	 * instance of the HashTable.
	 * @return the load factor threshold
	 */
	@Override
	public double getLoadFactorThreshold() {
		return loadFactorThreshold;
	}

	
	/**
	 * Returns the current load factor for this hash table.
	 * @return the current load factor
	 */
	@Override
	public double getLoadFactor() {
		return (double)items/(double)size;
	}


	/**
	 * Return the current Capacity (table size) of the hash table array.
	 * @return the current Capacity
	 */
	@Override
	public int getCapacity() {
		return size;
	}


	/**
	 * Returns the collision resolution scheme used for this hash table.
	 * 1 OPEN ADDRESSING: linear probe
	 * 2 OPEN ADDRESSING: quadratic probe
	 * 3 OPEN ADDRESSING: double hashing
	 * 4 CHAINED BUCKET: array of arrays
	 * 5 CHAINED BUCKET: array of linked nodes
	 * 6 CHAINED BUCKET: array of search trees
	 * 7 CHAINED BUCKET: linked nodes of arrays
	 * 8 CHAINED BUCKET: linked nodes of linked node
	 * 9 CHAINED BUCKET: linked nodes of search trees
	 */
	@Override
	public int getCollisionResolution() {
		return 5;//chained bucket: array of linked nodes
	}


		
}
