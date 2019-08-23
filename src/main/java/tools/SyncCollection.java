package tools;

import java.util.*;

/**
 * 将线程不安全的ArrayList和HashMap转换为安全的
 */
public class SyncCollection {
    private Collection<String> a;

    public Collection getSyncCollection(){
       return Collections.synchronizedCollection(a);
    }

    public List getSyncList(){
        return Collections.synchronizedList(new ArrayList<>());
    }
    public Map getSyncMap(){
        return Collections.synchronizedMap(new HashMap<>());
    }

    public Vector<Long> vectorUsing(){
        Vector v =  new Vector<>();
        v.add(new HashMap<>());
        return  v;
    }
}
