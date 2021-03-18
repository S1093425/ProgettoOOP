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
import it.univpm.ProgettoOOP.statistiche.Statistiche;


public class testEventi {
    private ArrayList<Evento> eventi = new ArrayList<Evento>();    
    private Evento e1,e2, e3, e4, e5;
    private Stato s;
    private Statistiche stats = new Statistiche();
    private ArrayList<String> sourcename = new ArrayList<>();
    private ArrayList<String> sourcename2 = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
    	sourcename.add("Ticketmaster");
    	sourcename2.add("Universe");
        e1 = new Evento("Jo Koy","Arts & Theatre",new Date(),"Alaska", sourcename);
        e2 = new Evento("Rodney Carrington","Arts & Theatre",new Date(),"Alaska", sourcename);
        e3 = new Evento("Luke Bryan","Music",new Date(),"Alaska", sourcename);
        e4 = new Evento("Jo Koy","Arts & Theatre",new Date(),"Alaska", sourcename2);
        e5 = new Evento("Reba McEntire","Music",new Date(),"Alabama", sourcename2);
       eventi.add(e1);
       eventi.add(e2);
       eventi.add(e3);
       eventi.add(e4);
       eventi.add(e5);
       
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void Test1() {
    	s= stats.getStatsStato(eventi, "Alaska");
        assertEquals(4,s.getEventi_Totali());
        assertEquals(1,s.getGeneri()[0]);
        assertEquals(3,s.getGeneri()[2]);
        assertEquals(3,s.getSource()[0]);
        assertEquals(1,s.getSource()[1]);
    }
  
    @Test
    void Test2() {
        JsonObject body = new JsonObject();
        body.addProperty("stato","IT");
        assertThrows(StateNotFound.class, ()->{Controller.getEvento(body);});
    }
}