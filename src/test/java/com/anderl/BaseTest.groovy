package com.anderl

import org.junit.Test

import java.util.stream.Collectors

/**
 * Created by dasanderl on 09.12.14.
 */
class BaseTest {

    public class TestClazz {

        List<String> list = new ArrayList<>();


        {
            System.out.println("init block");
            testMethod();
        }

        public TestClazz() {
            System.out.println("constr");
        }

        public void testMethod() {
            System.out.println(list);
        }
    }

    @Test
    public void testSth() {

        new TestClazz();

        def listOfLists = [[1, 2, 3], [4, 5, 6], [7, 8]]


        println listOfLists.stream().flatMap(listOfLists: listOfLists.stream()).collect(Collectors.toList())
    }
}
