/**
 * UnionFind/Disjoint Set data structure implementation. This code was inspired by the union find
 * implementation found in 'Algorithms Fourth Edition' by Robert Sedgewick and Kevin Wayne.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.unionfind;

public class UnionFind {
    private int size;
    private int numComponents;

    private int[] sz;
    private int[] id;

    public UnionFind(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");

        this.size = numComponents = size;

        sz = new int[size];
        id = new int[size];

        for (int i = 0; i < size; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    // find the root of p
    int find(int p) {
        int root = p;

        while (root != id[root]) root = id[root];

        // path compression
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }

        return root;
    }

    boolean connected(int p, int q) { return find(p) == find(q); }

    int componentSize(int p) {
        return sz[find(p)];
    }

    int size() {
        return size;
    }

    int components() {
        return numComponents;
    }

    public void unify(int p, int q) {
        if (connected(p, q)) return;

        int rootP = find(p);
        int rootQ = find(q);

        if (componentSize(rootP) > componentSize(rootQ)) {
            id[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
            sz[rootQ] = 0;
        } else {
            id[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
            sz[rootP] = 0;
        }

        numComponents--;
    }
}
