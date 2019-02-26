package com.feng.domain;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TanChun on 2018/12/4.
 */
public class A {

        public static void main(String[] args) throws JSONException {
            String sb="我 \n草\n付完费\n福娃\n个人股\n";
            System.out.println(sb);
            System.out.println("test");

            String body =  sb.lastIndexOf("\n") != -1 ?sb.subSequence(0,sb.lastIndexOf("\n")).toString():sb.toString();
            System.out.println(body);
            System.out.println("test");
        }


}
