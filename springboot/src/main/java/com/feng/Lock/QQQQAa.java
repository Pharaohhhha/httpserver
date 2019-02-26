package com.feng.Lock;

/**
 * Created by TanChun on 2018/12/19.
 */
public class QQQQAa {
        public int[] arrayPairSum(int[] nums) {
            for (int i = nums.length / 2; i >= 0; i--) {
                HeapAdjust(nums, i, nums.length-1);
            }
            int z;
            for(int i = nums.length - 1; i > 0; i--){
                z=nums[i];
                nums[i]=nums[0];
                nums[0]=z;
                HeapAdjust(nums, 0, i);
            }
            return  nums;
        }
        public void  HeapAdjust(int[] nums,int parent,int length){
            int temp=nums[parent];
            int child=2*parent+1;
            while(length>child){
                if(child+1<length && nums[child+1]>nums[child]){
                    child++;
                }
                if(temp>nums[child]){
                    break;
                }
                nums[parent]=nums[child];
                parent=child;
                child=child*2+1;
            }
            nums[parent]=temp;
        }

    public static void main(String[] args) {
        // 初始化一个序列
        int[] array = {
                1, 3, 4, 5, 2, 6, 9, 7, 8, 0
        };
        // 调用快速排序方法
        QQQQAa qqqqAa = new QQQQAa();
        int[] ints = qqqqAa.arrayPairSum(array);
        for (int anInt : ints) {
            System.out.print(anInt+",");
        }
    }

    }