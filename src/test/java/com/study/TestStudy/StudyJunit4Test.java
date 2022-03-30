package com.study.TestStudy;

import org.junit.Before;
import org.junit.Test;

public class StudyJunit4Test {

    @Before
    public void before() {
        System.out.println("before");
    }

    @Test
    public void creatTest() {
        System.out.println("test");
    }

    @Test
    public void creatTest2() {
        System.out.println("test2");
    }
}
