package org.glitter.links;


class SimpleValueTest {

    void get() {

    }

    public static void main(String[] args) {
        SimpleValue value = new SimpleValue("asd");
        System.out.println(value);
        value.update("vvv");
        System.out.println(value);
        System.out.println(value.get());

    }
}