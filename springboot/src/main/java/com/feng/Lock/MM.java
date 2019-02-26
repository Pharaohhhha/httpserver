package com.feng.Lock;

/**
 * Created by TanChun on 2018/12/13.
 */
public class MM {
    public static void main(String[] args) {
        int[] a={3,6,9,4,2,8,1,5,7};
        int te;
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a.length-i-1;j++){
                if(a[j]<a[j+1]){
                    te=a[j];
                    a[j]=a[j+1];
                    a[j+1]=te;
                }
            }
        }
        for (int zz:a) {
            System.out.println(zz);
        }
    }
}
