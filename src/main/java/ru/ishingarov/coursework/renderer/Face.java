package ru.ishingarov.coursework.renderer;

import java.util.Arrays;

public class Face {
    private int[] vi;
    private int[] vni;

    public Face(int[] vertIndexes, int[] normalIndexes) {
        vi = vertIndexes;
        vni = normalIndexes;
    }

    public int[] getVi() {
        return vi;
    }

    public int[] getVni() {
        return vni;
    }

    @Override
    public String toString() {
        return "Face{" +
                "vi=" + Arrays.toString(vi) +
                ", vni=" + Arrays.toString(vni) +
                '}';
    }
}
