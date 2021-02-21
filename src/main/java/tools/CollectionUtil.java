package tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 集合工具类
 */
public class CollectionUtil {
    /**
     * 复制一个新的set，防止在迭代keyset过程中，添加新的元素会报错ConcurrentModificationException
     */
    public static Set cloneSet(Set set){
        Set s = new HashSet();
        for(Object obj : set){
            s.add(obj);
        }
        return s;
    }
}
