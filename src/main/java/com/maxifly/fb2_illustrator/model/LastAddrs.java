package com.maxifly.fb2_illustrator.model;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Maximus on 23.01.2017.
 */
public class LastAddrs implements Iterable<String>{
    private int capasity;
    private LinkedList<String> addrs = new LinkedList<>();


    public LastAddrs(int capasity) {
        this.capasity = capasity;
    }

    public void change_LastAddr(String lastAddr) {
        this.addrs.remove(lastAddr);
        this.addrs.addFirst(lastAddr);

        if (this.addrs.size() > capasity) {
            this.addrs.remove(capasity);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return addrs.iterator();
    }
}
