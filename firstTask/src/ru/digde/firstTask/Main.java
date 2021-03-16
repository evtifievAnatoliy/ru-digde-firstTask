/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.digde.firstTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Анатолий
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static final Map<Character, Character> MAP = new HashMap<>();
    
    public static void main(String[] args) {
        
        
        FirsrTask firsrTask = new FirsrTask("3[xyz][xy]z");
        System.out.println("Input sring: " + firsrTask.getStr());
        System.out.println("Unpacked sring: " + firsrTask.getUnpackedStr());
        
    }
    
    
}
