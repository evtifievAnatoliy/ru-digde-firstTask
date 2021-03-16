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
        
        MAP.put('[', ']');
        
        //String str = "3[xyz]4[xy]z";
        String str = "2[3[x]y]";
        
        System.out.println("Input sring: " + str);
        System.out.println("Unpacked sring: " + unpackedString(str));
    }
    
    private static String unpackedString (String str){
        
        String unpackedString;
        unpackedString = splitStrings(str);
        
        return unpackedString;
    }
    
    private static String splitStrings (String str){
        String returnStr ="";
        Map<Integer, Integer> bracketsMap = new HashMap<>();
        
        //находим расположение ковычек
        int countOpenBracket=0;
        int firstIndexOpenBracket=0;
        for (int i=0; i<str.length(); i++){

            if (str.charAt(i) == '['){
                 if (countOpenBracket == 0){
                    firstIndexOpenBracket = i;
                 }
                 countOpenBracket++;
             }

            if (str.charAt(i) == MAP.get('[')){
                 countOpenBracket--;
                 if (countOpenBracket == 0)
                     bracketsMap.put(firstIndexOpenBracket, i);
                 }
             }
        //---------------------------
        
        //склеиваем строчку результата
        int lastIndex = 0;
        int firstIndex = 0;
        boolean isFirst = true;
        for (Map.Entry<Integer, Integer> entry : bracketsMap.entrySet()) {
            //парсим коэфициент умножения
            int numberCounts = 1;
            if (entry.getKey() > 0 && Character.isDigit(str.charAt(entry.getKey()-1))){
                String numberStr = leftStickNumberCounts (str, entry.getKey()-1);
                numberCounts = Integer.parseInt(numberStr);
            }
            String substringStr = splitStrings(str.substring(entry.getKey()+1, entry.getValue()));
            
            //склеиваем середину между блоками ковычек
            if (entry.getKey()>lastIndex && !isFirst){
                //если последний символ число
                if (Character.isDigit(str.charAt(entry.getKey()-1)))
                { 
                    String numberStr = leftStickNumberCounts (str, str.length()-1);
                    if(lastIndex < str.length() - 1 - numberStr.length())
                        returnStr = returnStr + str.substring(lastIndex + 1, entry.getKey()- numberStr.length());
                }
                else
                    returnStr = returnStr + str.substring(lastIndex+1, entry.getKey());
            }
            //----------------------------------------
            
            for (int i=0; i< numberCounts; i++)
                returnStr = returnStr + substringStr;
            lastIndex = entry.getValue();
            if (isFirst){
                if (numberCounts == 0)
                    firstIndex = entry.getKey();
                else
                    firstIndex = entry.getKey() - String.valueOf(numberCounts).length();
                isFirst = false;
            }
            
        }
                
        //если ковычек нет
        if(bracketsMap.size() == 0)
            returnStr = returnStr + str;
        else{

            //склеиваем начало строки
            if (firstIndex > 0)
                if (Character.isDigit(str.charAt(firstIndex)))
                { 
                    String numberStr = leftStickNumberCounts (str, firstIndex);
                    if(firstIndex - numberStr.length() + 1 > 0)
                        returnStr = str.substring(0, firstIndex +1 - numberStr.length()) + returnStr;
                }
                else
                    returnStr = str.substring(0, firstIndex +1) + returnStr;

            //склеиваем конец строки
            if (lastIndex +1 < str.length()){
                //если последний символ число
                if (Character.isDigit(str.charAt(str.length()-1)))
                { 
                    String numberStr = leftStickNumberCounts (str, str.length()-1);
                    if(lastIndex < str.length() - 1 - numberStr.length())
                        returnStr = returnStr + str.substring(lastIndex + 1, str.length()- numberStr.length());
                }
                else
                    returnStr = returnStr + str.substring(lastIndex + 1, str.length());

            }
        }
        
        //последним шагом умножаем буквы без ковычек перед которыми цифры
        String tempStr = returnStr;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<tempStr.length(); i++){
            if(Character.isDigit(tempStr.charAt(i))){
                String numberStr = rightStickNumberCounts(tempStr, i);
                for (int j=0; j<Integer.parseInt(numberStr); j++)
                    sb.append(String.valueOf(tempStr.charAt(i+numberStr.length())));
                i = i + numberStr.length();    
            }
            else
                sb.append(tempStr.charAt(i));
        }
        return sb.toString();
        
    }
    
    private static String leftStickNumberCounts (String str, int index){
        if (index > 0)
            if (Character.isDigit(str.charAt(index-1)))
                return leftStickNumberCounts(str, index-1) + str.substring(index, index+1);
        
        return str.substring(index, index+1);
    }
    
    private static String rightStickNumberCounts (String str, int index){
        if (index < str.length() - 1)
            if (Character.isDigit(str.charAt(index+1)))
                return str.substring(index, index+1) + rightStickNumberCounts(str, index + 1);
        
        return str.substring(index, index+1);
    }
    
}
