package com.feng.domain;

import javafx.beans.InvalidationListener;

import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by wangxl6 on 2018/8/24.
 */
public class Version extends Observable {
    private AtomicInteger version ;

    public Version(int version){
       this.version = new AtomicInteger(version);
    }
    public int incrementAndGet(){
        setVersion(version.incrementAndGet());
        return version.get();
    }
    public void setVersion(int version){
        this.version.set(version);
        setChanged();
        notifyObservers(this.version.get());
    }


}
