package com.company;

import java.awt.*;

public class Point {
    int x;
    int y;
    Color cluster;


    public Point(int x,int y){
        this.x = x;
        this.y = y;
        this.cluster = Color.black;
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    void assignToCluster(Color newCluster) {
        this.cluster = newCluster;
    }
}
