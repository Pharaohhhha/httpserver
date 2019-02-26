package com.feng.Lock;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TanChun on 2018/12/18.
 */
public class GGG {

        public int majorityElement(int[] nums) {
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            for(int i=0;i<nums.length;i++){
                if(map.containsKey(nums[i])){
                    map.put(nums[i],map.get(nums[i])+1);
                }else{
                    map.put(nums[i],1);
                }
            }
            Collection<Integer> count = map.values();
            int maxCount = Collections.max(count);
            int maxNumber = 0;
            for(Map.Entry<Integer,Integer> entry:map.entrySet()){

                if(entry.getValue() == maxCount)
                {
                    maxNumber = entry.getKey();
                }
            }
            System.out.println("出现次数最多的数字为：" + maxNumber);
            System.out.println("该数字一共出现" + maxCount + "次");
            return maxNumber;
        }

    public static void main(String[] args) {
        GGG ggg = new GGG();
        System.out.println(ggg.majorityElement(new int[]{1, 3, 3, 2, 4, 3, 4, 5, 6, 5}));
    }
    }
