package com.feng.Lock;


import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],i);
        }
        if(map.size()==nums.length){
            return false;
        }
        return true;
    }
}