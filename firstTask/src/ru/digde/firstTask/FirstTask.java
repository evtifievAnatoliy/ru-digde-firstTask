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
public class FirstTask {
    
    private static final Map<Character, Character> MAP = new HashMap<>();
    private String str;
    private String unpackedStr;
    private boolean isValidated;
    
    public FirstTask(String str) {
        MAP.put('[', ']');
        this.str = str;
        isValidated = checkValidation();
        if (isValidated)
            this.unpackedStr = splitStrings(this.str);
        else
            this.unpackedStr = "Строка не прошла валидацию";
    }

    public String getStr() {
        return str;
    }

    public boolean isIsValidated() {
        return isValidated;
    }
    
    public String getUnpackedStr (){
        return unpackedStr;
    }
    
    //парсинг строки
    private  String splitStrings (String str){
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
                    if (i+numberStr.length()<tempStr.length())
                        sb.append(String.valueOf(tempStr.charAt(i+numberStr.length())));
                i = i + numberStr.length();    
            }
            else
                sb.append(tempStr.charAt(i));
        }
        return sb.toString();
        
    }
    
    //склеивание числа влево
    private  String leftStickNumberCounts (String str, int index){
        if (index > 0)
            if (Character.isDigit(str.charAt(index-1)))
                return leftStickNumberCounts(str, index-1) + str.substring(index, index+1);
        
        return str.substring(index, index+1);
    }
    
    //склеивание числа вправо
    private  String rightStickNumberCounts (String str, int index){
        if (index < str.length() - 1)
            if (Character.isDigit(str.charAt(index+1)))
                return str.substring(index, index+1) + rightStickNumberCounts(str, index + 1);
        
        return str.substring(index, index+1);
    }
    
    //проверка валидации
    private boolean checkValidation(){
        
        if(this.str == null)
            return false;
        
        if (checkCaractersValidation()
                && checkBracketsValidation(this.str))
            return true;
        
        return false;
        
    }
    
    //валидация доступных символов
    private boolean checkCaractersValidation(){
        String clearStr = this.str.replaceAll("[a-zA-Z0-9\\[\\]]","");
        if (clearStr.length() == 0)
            return true;
        
        return false;
    }
    
    //валидация скобок
    private boolean checkBracketsValidation(String str){
        String clearStr = str.replaceAll("[^\\[\\]]","");
        char[] mainArray = clearStr.toCharArray();
        if (mainArray.length%2!=0 /*|| mainArray.length==0*/)
            return false;
        
        if (mainArray.length == 2 && Character.valueOf(mainArray[1]).equals(MAP.get(mainArray[0])))
            return true;
        if (mainArray.length == 2 && !Character.valueOf(mainArray[1]).equals(MAP.get(mainArray[0])))
            return false;
        
        ArrayList <char[]> arrayList= new ArrayList<char[]>();
        int firstIndex=0;
        int similarMark = 0;
        for (int i=1; i<mainArray.length; i++){
            if (Character.valueOf(mainArray[i]).equals(Character.valueOf(mainArray[firstIndex])))
                similarMark ++;
            
            if (Character.valueOf(mainArray[i]).equals(MAP.get(mainArray[firstIndex])) && similarMark == 0)
            {
                char[] charArray;
                if (i == mainArray.length - 1 && firstIndex == 0){
                    charArray = Arrays.copyOfRange(mainArray, firstIndex+1, i);
                }
                else{
                    charArray = Arrays.copyOfRange(mainArray, firstIndex, i+1); 
                }
                arrayList.add(charArray);
                
                firstIndex = i+1;
                i++;
            }
            if (i < mainArray.length-1)
                if (Character.valueOf(mainArray[i]).equals(MAP.get(mainArray[firstIndex])) && similarMark > 0)
                    similarMark --;
        }
        
        if (similarMark >0)
            return false;
        
        for (char[] item: arrayList){
            if (!checkBracketsValidation(String.valueOf(item)))
                return false;
        }
   
        return true;
    }
}
