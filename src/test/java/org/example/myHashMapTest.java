package org.example;

import org.junit.Test;

/**
 * @author Yushun Shao
 * @date 2023/4/13 10:47
 * @description: test
 */
public class myHashMapTest {
    @Test
    public void test01(){
        myHashMap map = new myHashMap();
        map.put("刘华强1","哥们，你这瓜保熟吗？");
        map.put("刘华强1","你这瓜熟我肯定要啊！");
        System.out.println(map.get("刘华强1"));
    }
}
