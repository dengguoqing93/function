package org.guoqing.java_functional.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-13
 */
public class MyTest {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        List<Integer> list2 = new ArrayList<>(list1);
        list1.set(3,38);
        list1.forEach(t-> System.out.println("list1:"+t));
        list2.forEach(t-> System.out.println("list1:"+t));
    }
}
