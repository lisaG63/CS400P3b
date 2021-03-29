import static org.junit.jupiter.api.Assertions.fail;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;




/**
 * HashTableTest - test the mothods in HashTable
 * @author Weihang Guo
 * 
 */
public class HashTableTest{

	protected HashTableADT<Integer,String> ht;
    
	
    /**
     * the code runs before every test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	ht = new HashTable<Integer, String>(10, 1.5);
    }

    // TODO: add code that runs after each test method
    @After
    public void tearDown() throws Exception {

    }
    
    /** 
     * Tests that a HashTable returns an integer code
     * indicating which collision resolution strategy 
     * is used.
     * REFER TO HashTableADT for valid collision scheme codes.
     */
    @Test
    public void test000_collision_scheme() {
        HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer,String>();
        int scheme = htIntegerKey.getCollisionResolution();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }
        
    /** 
     * Tests that insert(null,null) throws IllegalNullKeyException
     */
    @Test
    public void test001_IllegalNullKey() {
        try {
            HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer,String>();
            htIntegerKey.insert(null, null);
            fail("should not be able to insert null key");
        } 
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }
    
    /** 
     * Tests that insert without rehash or collision is working well
     */
    @Test
    public void test002_insertWithoutRehashOrCollision() {
        try {
            ht.insert(1, "1");
            //1's hashCode is 1, and 1%20 = 1, so it would be put at index 1
            Assert.assertEquals(ht.get(1), "1");
            ht.insert(2, "2");
            //1's hashCode is 2, and 2%20 = 2, so it would be put at index 2
            Assert.assertEquals(ht.get(2), "2");
            ht.insert(3, "3");
            //1's hashCode is 3, and 3%20 = 3, so it would be put at index 3
            Assert.assertEquals(ht.get(3), "3");
            Assert.assertEquals(ht.numKeys(), 3);
            Assert.assertEquals(ht.getCapacity(), 10);
        } catch (Exception e) {
            fail("insert valid keys should not throw exception "+e.getClass().getName());
        }
    }
    
    /** 
     * Tests that insert with rehash is working well. When load factor threshold is reached,
     * resize and rehash happen.
     */
    @Test
    public void test003_insertWithRehash_reachLoadFactorThreshold() {
        try {
        	for (int i = 0; i < 5; i ++) {
        		ht.insert(i, new String(i + ""));
        		Assert.assertEquals(ht.get(i), new String(i + ""));
        		ht.insert(i + 10, new String((i + 10) + ""));
        		Assert.assertEquals(ht.get(i + 10), new String((i + 10) + ""));
        		ht.insert(i + 20, new String((i + 20) + ""));
        		Assert.assertEquals(ht.get(i + 20), new String((i + 20) + ""));
        	}
            //should cause rehash
            Assert.assertEquals(ht.numKeys(), 15);
            Assert.assertEquals(ht.getCapacity(), 21);
            //10 * 2 + 1 = 21
        } catch (Exception e) {
            fail("insert valid keys should not throw exception "+e.getClass().getName());
        }
    }
    
    /** 
     * Tests that insert with rehash is working well. When the table is full,
     * the code causes resize and rehash.
     */
    @Test
    public void test004_insertWithRehash_full() {
        try {
        	for (int i = 0; i < 10; i ++) {
        		ht.insert(i, new String(i + ""));
        		Assert.assertEquals(ht.get(i), new String(i + ""));
        	}
            //should cause rehash
            Assert.assertEquals(ht.numKeys(), 10);
            Assert.assertEquals(ht.getCapacity(), 21);
            //10 * 2 + 1 = 21
        } catch (Exception e) {
            fail("insert valid keys should not throw exception "+e.getClass().getName());
        }
    }
    
    
    /** 
     * Tests if inserting duplicate keys causes the old value be replace by the new value
     */
    @Test
    public void test005_insertDuplicateKeys() {
        try {
            ht.insert(2, "2");
            Assert.assertEquals(ht.get(2), "2");
            ht.insert(2, "3");
            Assert.assertEquals(ht.get(2), "3");
            Assert.assertEquals(ht.numKeys(), 1);
            Assert.assertEquals(ht.getCapacity(), 10);
        } catch (Exception e) {
            fail("insert valid keys should not throw exception "+e.getClass().getName());
        }
    }
    
    
    /** 
     * tests if removing a null key throws IllegalNullKeyException
     */
    @Test
    public void test006_removeNullKey() {
        try {
        	ht.remove(null);
        }
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
        	fail("remove null key should not throw exception "+e.getClass().getName());
        }
    }
    
    /** 
     * Tests that remove a nonexistent key returns false and the number of items is not decreased
     */
    @Test
    public void test007_removeNonexistentKey() {
        try {
        	for (int i = 0; i < 10; i ++) {
        		ht.insert(i, new String(i + ""));
        		Assert.assertEquals(ht.get(i), new String(i + ""));
        	}
            //should cause rehash
            Assert.assertEquals(ht.numKeys(), 10);
            Assert.assertEquals(ht.getCapacity(), 21);
            //10 * 2 + 1 = 21
        	Assert.assertFalse(ht.remove(14));
        	Assert.assertEquals(ht.numKeys(), 10);
        	Assert.assertEquals(ht.getCapacity(), 21);
        } catch (Exception e) {
        	fail("remove nonexistent key should not throw exception "+e.getClass().getName());
        }
    }
    
    
    /** 
     * Tests if remove a existing key actually removes the exact key and decrease 
     * the number of items.
     */
    @Test
    public void test008_removeExistingKey() {
        try {
        	for (int i = 0; i < 10; i ++) {
        		ht.insert(i, new String(i + ""));
        		Assert.assertEquals(ht.get(i), new String(i + ""));
        	}
            //should cause rehash
            Assert.assertEquals(ht.numKeys(), 10);
            Assert.assertEquals(ht.getCapacity(), 21);
            //10 * 2 + 1 = 21
            Assert.assertTrue(ht.remove(4));
            Assert.assertEquals(ht.numKeys(), 9);
        	Assert.assertEquals(ht.getCapacity(), 21);
            ht.get(4);
            fail("4 has been removed. Should throw KeyNotFoundException.");
        } 
        catch (KeyNotFoundException e) { /* expected */ } 
        catch (Exception e) {
            fail("get a previously removed key should not throw exception "+e.getClass().getName());
        }
    }
    
    
    
}
