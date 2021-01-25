package com.kaiyu;

import org.junit.Test;

import java.util.HashMap;

public class Solution {
    @Test
    public void CC(){
        int i = 999;
        System.out.println(i);
        i |= i >>> 1;
        System.out.println(i);
        i |= i >>> 2;
        System.out.println(i);
        i |= i >>> 4;
        System.out.println(i);
        i |= i >>> 8;
        System.out.println(i);
        HashMap<String, String> map = new HashMap<>();
    }
}
