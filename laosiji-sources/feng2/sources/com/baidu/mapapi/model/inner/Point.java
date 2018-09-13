package com.baidu.mapapi.model.inner;

import java.io.Serializable;

public class Point implements Serializable {
    public int x;
    public int y;

    public Point(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return this.x != point.x ? false : this.y == point.y;
    }

    public int getmPtx() {
        return this.x;
    }

    public int getmPty() {
        return this.y;
    }

    public int hashCode() {
        return ((this.x + 31) * 31) + this.y;
    }

    public void setmPtx(int i) {
        this.x = i;
    }

    public void setmPty(int i) {
        this.y = i;
    }

    public String toString() {
        return "Point [x=" + this.x + ", y=" + this.y + "]";
    }
}
