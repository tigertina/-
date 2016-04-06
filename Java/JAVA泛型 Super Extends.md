<font face="微软雅黑">
< super T>   表示包括T在内的任何T的父类  
< extends T>表示包括T在内的任何T的子类。

###extends
	List< extends Number> foo3的通配符声明，意味着以下的赋值是合法的：
	 
	// Number "extends" Number (in this context) 	 
	List< extends Number> foo3 = new ArrayList< extends Number>(); 
	 
	// Integer extends Number 	 
	List< extends Number> foo3 = new ArrayList< extends Integer>(); 
	 
	// Double extends Number 	 
	List< extends Number> foo3 = new ArrayList< extends Double>(); 

通过以上的赋值语句，**从foo3中可以读到Number,因为以上要么包含 Number元素，要么包含Number的子类元素**。你不能保证读取到Integer，因为foo3可能指向的是List<Double>。你 不能保证读取到Double，因为foo3可能指向的是List<Integer>。

写时，你不能插入一个Integer元素，因为foo3可能指向 List<Double>。你不能插入一个Double元素，因为foo3可能指向List<Integer>。你不能插入一个 Number元素，因为foo3可能指向List<Integer>。**你不能往List< extends T>中插入任何类型的对象，因为你不能保证列表实际指向的类型是什么，你并不能保证列表中实际存储什么类型的对象。**唯一可以保证的是，你可以从中读 取到T或者T的子类。

###super

	List< super Integer> foo3的通配符声明，意味着以下赋值是合法的： 
	 
	// Integer is a "superclass" of Integer (in this context) 	 
	List< super Integer> foo3 = new ArrayList<Integer>(); 
	 
	// Number is a superclass of Integer 	 
	List< super Integer> foo3 = new ArrayList<Number>(); 
	 
	// Object is a superclass of Integer 	 
	List< super Integer> foo3 = new ArrayList<Object>(); 


读时，你一定能从foo3列表中读取到的元素的类型是什么呢？你不能保证读取到Integer，因为foo3可能指向 List<Number>或者List<Object>。你不能保证读取到Number，因为foo3可能指向 List<Object>。唯一可以保证的是，你可以读取到Object或者Object子类的对象（你并不知道具体的子类是什么）。

写入操作通过以上给定的赋值语句，你能把一个什么类型的元素合法地插入到foo3中呢？**你可以插入Integer对象**，因为上述声明的列表都支持 Integer。你可以插入Integer的子类的对象，因为Integer的子类同时也是Integer，原因同上。你不能插入Double对象，因为 foo3可能指向ArrayList<Integer>。你不能插入Number对象，因为foo3可能指向 ArrayList<Integer>。你不能插入Object对象，因为foo3可能指向 ArrayList<Integer>。


###PECS原则

如果要从集合中读取类型T的数据，并且不能写入，可以使用 ? extends 通配符；(Producer Extends)
如果要从集合中写入类型T的数据，并且不需要读取，可以使用 ? super 通配符；(Consumer Super)
如果既要存又要取，那么就不要使用任何通配符。

因为对生产者来说，只是为了让消费者读，所以这个类只能被读，也即是被消费者读。反之亦然。

java.util.Collections里的copy方法

    /**
     * Copies all of the elements from one list into another.  After the
     * operation, the index of each copied element in the destination list
     * will be identical to its index in the source list.  The destination
     * list must be at least as long as the source list.  If it is longer, the
     * remaining elements in the destination list are unaffected. <p>
     *
     * This method runs in linear time.
     *
     * @param  dest The destination list.
     * @param  src The source list.
     * @throws IndexOutOfBoundsException if the destination list is too small
     *         to contain the entire source List.
     * @throws UnsupportedOperationException if the destination list's
     *         list-iterator does not support the <tt>set</tt> operation.
     */
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        int srcSize = src.size();
        if (srcSize > dest.size())
            throw new IndexOutOfBoundsException("Source does not fit in dest");

        if (srcSize < COPY_THRESHOLD ||
            (src instanceof RandomAccess && dest instanceof RandomAccess)) {
            for (int i=0; i<srcSize; i++)
                dest.set(i, src.get(i));
        } else {
            ListIterator<? super T> di=dest.listIterator();
            ListIterator<? extends T> si=src.listIterator();
            for (int i=0; i<srcSize; i++) {
                di.next();
                di.set(si.next());
            }
        }
    }