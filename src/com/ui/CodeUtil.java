package com.ui;

import java.util.ArrayList;
import java.util.Random;

public class CodeUtil {
    static String getCode(){
        ArrayList<Character>list=new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }
        Random r =new Random();
        StringBuilder sb =new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(list.get(r.nextInt(list.size())));
        }
        sb.append(r.nextInt(10));
        char[] arr=sb.toString().toCharArray();
        int index=r.nextInt(arr.length);
        char temp=arr[index];
        arr[index]=arr[arr.length-1];
        arr[arr.length-1]=temp;
        return new String(arr);
    }
}
