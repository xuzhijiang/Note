package com.java.datastructure.other;

public class QuickUnionUF {
    private int[] nodes;

    public QuickUnionUF(int N) {
        nodes = new int[N];
        for (int i = 0; i < N; i++) {
            // 表示它的老大就是他自己,这个也是并查集的初始状态.
            nodes[i] = i;
        }
    }

    private int findNode(int i) {
        int element = i;

        while (element != nodes[element])
            element = nodes[element];

        while (i != nodes[i]) {
            int tmp = nodes[i];
            nodes[i] = element;
            i = tmp;
        }

        return element;
    }

    public void union(int p, int q) {
        int qnode = findNode(q);
        int pnode = findNode(p);
        nodes[pnode] = qnode;
    }
}
