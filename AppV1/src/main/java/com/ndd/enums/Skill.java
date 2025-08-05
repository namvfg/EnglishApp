/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ndd.enums;

/**
 *
 * @author Admin
 */
public enum Skill {
    listening("Listening"),
    reading("Reading"),
    speaking("Speaking"),
    writing("Writing");

    private final String label;

    Skill(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
