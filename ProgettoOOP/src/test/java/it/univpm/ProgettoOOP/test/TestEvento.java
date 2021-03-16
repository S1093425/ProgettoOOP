package it.univpm.ProgettoOOP.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.JsonObject;
import it.univpm.ProgettoOOP.*;
import it.univpm.ProgettoOOP.controller.Controller;
import it.univpm.ProgettoOOP.exception.StateNotFound;
import it.univpm.ProgettoOOP.model.*;


public class TestEvento {
    private ArrayList<Evento> eventi = new ArrayList<Evento>();    
    private Evento e1,e2, e3, e4;
    private Stato s;
    
    @BeforeEach
    void setUp() throws Exception {
        e1 = new Evento("Jo Koy","Arts & Theatre",new Date(),"Alaska");
        e2 = new Evento("Rodney Carrington","Arts & Theatre",new Date(),"Alaska");
        e3 = new Evento("Luke Bryan","Music",new Date(),"Alabama");
        e4 = new Evento("Reba McEntire","Music",new Date(),"Alabama");
       eventi.add(e1);
       eventi.add(e2);
       eventi.add(e3);
       eventi.add(e4);
    }
    
    @AfterEach
    void tearDown() throws Exception {
    }
    
    /*
    @Test
    void Test1() {
        assertEquals(1019.0,s.getValues()[0][0]);
        assertEquals(72.0,s.getValues(city)[1][0]);
        assertEquals(3.79,s.getValues(city)[2][0]);
        assertEquals(995.0,s.getValues(city)[0][1]);
        assertEquals(36.0,s.getValues(city)[1][1]);
        assertEquals(7.41,s.getValues(city)[2][1]);
    }
    */
    @Test
    void Test2() {
        JsonObject body = new JsonObject();
        body.addProperty("stato","IT");
        assertThrows(StateNotFound.class, ()->{Controller.getEvento(body);});
    }
}
