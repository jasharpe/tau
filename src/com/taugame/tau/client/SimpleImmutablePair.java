package com.taugame.tau.client;



public class SimpleImmutablePair<A, B> {
    private final A a;
    private final B b;

    public SimpleImmutablePair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getFirst() {
        return a;
    }

    public B getSecond() {
        return b;
    }
}
