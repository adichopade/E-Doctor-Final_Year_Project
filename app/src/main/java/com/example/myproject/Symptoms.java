package com.example.myproject;


class Symptoms {
    String disease;

    String ab;
    String bc;
    String cde;
    String de;
    String ef;
    String fg;

    public Symptoms(String ab, String bc, String cde, String de, String ef, String fg) {
        this.ab = ab;
        this.bc = bc;
        this.cde = cde;
        this.de = de;
        this.ef = ef;
        this.fg = fg;
    }

    public String getDisease() {
        System.out.println(disease);
        return disease;
    }
}
