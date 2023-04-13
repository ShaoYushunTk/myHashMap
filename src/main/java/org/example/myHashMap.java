package org.example;

/**
 * @author Yushun Shao
 * @date 2023/4/13 10:23
 * @description: my hash map
 */
public class myHashMap <K,V>{
    class Node<K,V>{
        private K key;
        private V value;
        private Node<K,V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    //默认容量
    final int DEFAULT_CAPACITY = 16;
    //负载因子
    final float LOAD_FACTOR = 0.75f;
    //HashMap大小
    private int size;
    //桶数组
    Node<K,V>[] buckets;

    public myHashMap() {
        buckets = new Node[DEFAULT_CAPACITY];
        this.size = 0;
    }
    public myHashMap(int capacity) {
        buckets = new Node[capacity];
        this.size = 0;
    }

    //hash函数
    private int getIndex(K key, int length){
        int hashCode = key.hashCode();
        int index = hashCode % length;
        return Math.abs(index);
    }

    public void put(K key, V value){
        if(size >= buckets.length * LOAD_FACTOR)    resize();
        putVal(key, value, buckets);
    }

    private void putVal(K key, V value, Node<K,V>[] table){
        int index = getIndex(key,table.length);
        Node node = table[index];
        //插入位置为空，直接插入
        if(node == null){
            table[index] = new Node<>(key,value);
            size++;
            return;
        }
        //插入位置不为空，发生冲突，采用链地址法遍历链表找到空的位置
        while (node != null){
            //key相同则覆盖
            //"=="操作符用来比较两个对象的引用是否相同
            //"equals"方法用来比较两个对象的内容是否相同
            //同一个桶中可能存在多个节点，这些节点的key的hashCode可能相同，但是它们的key的内容可能不同
            if((node.key.hashCode() == key.hashCode())
                && (node.key == key || node.key.equals(key))){
                node.value = value;
                return;
            }
            node = node.next;
        }
        //当前key不在链表，插入链表头部
        Node newNode = new Node<>(key, value, table[index]);
        table[index] = newNode;
        size++;
    }

    //扩容
    private void resize(){
        Node<K,V>[] newBuckets = new Node[buckets.length * 2];
        rehash(newBuckets);
        buckets = newBuckets;
    }

    //重新散列
    private void rehash(Node<K,V>[] newBuckets){
        size = 0;
        for(int i = 0; i < buckets.length; i++){
            if (buckets[i] == null) continue;
            Node<K,V> node = buckets[i];
            while(node != null){
                putVal(node.key, node.value, newBuckets);
                node = node.next;
            }
        }
    }

    //获取元素
    public V get(K key){
        int index = getIndex(key, buckets.length);
        if (buckets[index] == null) return null;
        Node<K,V> node = buckets[index];
        while(node != null){
            if((node.key.hashCode() == key.hashCode())
                && (node.key == key || node.key.equals(key))){
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    //返回大小
    public int getSize() {
        return size;
    }
}
