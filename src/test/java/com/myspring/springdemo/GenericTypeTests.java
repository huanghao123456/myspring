package com.myspring.springdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型的协变与逆边
 * <p>当泛型作为生产者提供数据时，采用协变（? extends）使得子类更宽松
 * <p>当泛型作为消费者，即需调用传入泛型的参数方法时，则使用逆变（? super）
 */
public class GenericTypeTests {

    @Test
    public void test1() {
        List<Float> myList = new ArrayList<>();
        myList.add(30f);
        myList.add(10f);
        myList.add(2f);
        System.out.println(sum(myList));
    }

    @Test
    public void test3() {
        List<Double> myList = new ArrayList<>();
        myList.add(-1.2);
        myList.add(1.6);
        myList.add(0.0);
        List<Double> list = removeIf(myList, new Filter<Double>() {
            @Override
            public boolean isValid(Double aDouble) {
                return aDouble > 0;
            }
        });
        System.out.println(list);
    }

    /**
     * 此处isValid方法的入参抽象程度就很高
     */
    @Test
    public void test4() {
        List<Double> myList = new ArrayList<>();
        myList.add(-1.2);
        myList.add(1.6);
        myList.add(0.0);
        removeIfV2(myList, new Filter<Number>() {
            @Override
            public boolean isValid(Number e) {
                return e.doubleValue() > 0;
            }
        });
        System.out.println(myList);
    }

    /**
     * 泛型的协变，List<? extends Number>是正确写法，List<Number>会编译报错
     */
    public static double sum(List<? extends Number> list) {
        double sum = 0;
        for (Number number : list) {
            sum += number.doubleValue();
        }
        return sum;
    }

    interface Filter<E> {
        boolean isValid(E e);
    }

    /**
     * 按某种规则过滤掉集合里不想要的值
     */
    public static <E> List<E> removeIf(List<E> list, Filter<E> filter) {
        List<E> removeList = new ArrayList<>();
        for (E e : list) {
            if (!filter.isValid(e)) {
                removeList.add(e);
            }
        }
        list.removeAll(removeList);
        return list;
    }

    /**
     * 上面的removeIf不够优雅，myList的数据类型Double换成Long，Integer，Short时，Filter的泛型也要跟着换，逆变可解决这一问题
     */
    public static <E> List<E> removeIfV2(List<E> list, Filter<? super E> filter) {
        List<E> removeList = new ArrayList<>();
        for (E e : list) {
            if (!filter.isValid(e)) {
                removeList.add(e);
            }
        }
        list.removeAll(removeList);
        return list;
    }
}
