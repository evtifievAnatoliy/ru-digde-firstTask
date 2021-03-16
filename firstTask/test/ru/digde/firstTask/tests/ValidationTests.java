/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.digde.firstTask.tests;

import ru.digde.firstTask.FirstTask;
import org.junit.Test;
import org.junit.Assert;
/**
 *
 * @author Анатолий
 */
public class ValidationTests {
    
   private FirstTask firstTask;
    
    @Test
    public void checkCaractersValidation() {
        
        firstTask = new FirstTask("Hello");
        Assert.assertEquals(firstTask.isIsValidated(), true);
    
        firstTask = new FirstTask("He[]llo");
        Assert.assertEquals(firstTask.isIsValidated(), true);
    
        firstTask = new FirstTask("");
        Assert.assertEquals(firstTask.isIsValidated(), true);
    
        firstTask = new FirstTask("[]");
        Assert.assertEquals(firstTask.isIsValidated(), true);
    
        firstTask = new FirstTask("He[[]]l[l]o");
        Assert.assertEquals(firstTask.isIsValidated(), true);
    
        firstTask = new FirstTask("[[]]He[]llo[]");
        Assert.assertEquals(firstTask.isIsValidated(), true);
    
        firstTask = new FirstTask("He[[]llo");
        Assert.assertEquals(firstTask.isIsValidated(), false);
    
        firstTask = new FirstTask("He[[llo");
        Assert.assertEquals(firstTask.isIsValidated(), false);
    
        firstTask = new FirstTask("][");
        Assert.assertEquals(firstTask.isIsValidated(), false);
    
        firstTask = new FirstTask("He[[]llo][");
        Assert.assertEquals(firstTask.isIsValidated(), false);
    
        firstTask = new FirstTask(null);
        Assert.assertEquals(firstTask.isIsValidated(), false);
    
    }
    
    @Test
    public void checkParsingValidation() {
        
        firstTask = new FirstTask("");
        Assert.assertEquals(firstTask.getUnpackedStr(), "");
    
        firstTask = new FirstTask("[]");
        Assert.assertEquals(firstTask.getUnpackedStr(), "");
    
        firstTask = new FirstTask("2x");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xx");
    
        firstTask = new FirstTask("x2x");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xxx");
    
        firstTask = new FirstTask("2x2x2");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xxxx");
        
        firstTask = new FirstTask("3[xyz]4[xy]z");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xyzxyzxyzxyxyxyxyz");
    
        firstTask = new FirstTask("3[xyz]4[xy]z2");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xyzxyzxyzxyxyxyxyz");
    
        firstTask = new FirstTask("x3[xyz]4[xy]z");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xxyzxyzxyzxyxyxyxyz");
    
        firstTask = new FirstTask("3x3[xyz]4[xy]z");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xxxxyzxyzxyzxyxyxyxyz");
    
        firstTask = new FirstTask("3[xyz]4z4[xy]z");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xyzxyzxyzzzzzxyxyxyxyz");
    
        firstTask = new FirstTask("3[xyz]4z[xy]z");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xyzxyzxyzzzzzxyz");
    
        firstTask = new FirstTask("2[3[x]2y]");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xxxyyxxxyy");
    
        firstTask = new FirstTask("2[3[x2[z]]2y]");
        Assert.assertEquals(firstTask.getUnpackedStr(), "xzzxzzxzzyyxzzxzzxzzyy");
    
    }
}
