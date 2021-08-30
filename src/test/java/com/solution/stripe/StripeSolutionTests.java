package com.solution.stripe;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StripeSolutionTests {

    StripeSolutionInterface solutionInterface = new StripeSolution();

//    @Test
//    void test_badFormatClientHeader() {
//        assertTrue(solutionInterface.parsedLanguageHeaders("qwheflqkwjebfklqwjebnfl----** aksjdnajsd,  asdklsk",
//                new ArrayList<>()).isEmpty());
//    }

    @Test
    void test_noneSupported() {
        assertTrue(solutionInterface.parsedLanguageHeaders("en-US, fr-CA, fr-FR", new ArrayList<>()).isEmpty());
    }

    @Test
    void test_clientSendsNothing() {
        List<String> supported = new ArrayList<>();
        supported.add("fr-CA");
        supported.add("fr-FR");
        supported.add("en-US");
        assertTrue(solutionInterface.parsedLanguageHeaders("", supported).isEmpty());
    }


    @Test
    void test_preferredOrderPreserved() {
        List<String> supported = new ArrayList<>();
        supported.add("fr-CA");
        supported.add("fr-FR");
        supported.add("en-US");
        List<String> results = solutionInterface.parsedLanguageHeaders("fr-CA, fr-FR, en-US, es-SP", supported);
        assertEquals("fr-CA", results.get(0));
        assertEquals("fr-FR", results.get(1));
        assertEquals("en-US", results.get(2));
        assertEquals(3, results.size());
    }

    @Test
    void test_duplicates() {
        List<String> supported = new ArrayList<>();
        supported.add("fr-CA");
        supported.add("fr-FR");
        supported.add("en-US");
        List<String> results = solutionInterface.parsedLanguageHeaders("fr-CA, fr-CA", supported);
        assertEquals("fr-CA", results.get(0));
        assertEquals(1, results.size());
    }


    @Test
    void test_onlySupportedAreReturned() {
        List<String> supported = new ArrayList<>();
        supported.add("fr-CA");
        List<String> results = solutionInterface.parsedLanguageHeaders("en-US, es-SP, fr-CA, fr-FR", supported);
        assertEquals("fr-CA", results.get(0));
        assertEquals(1, results.size());
    }

    @Test
    void test_NoRegionInHeader() {
        List<String> supported = new ArrayList<>();
        supported.add("fr-CA");
        supported.add("fr-FR");
        supported.add("en-US");
        supported.add("en-SP");
        List<String> results = solutionInterface.parsedLanguageHeaders("en, fr", supported);
        assertEquals("en-US", results.get(0));
        assertEquals("en-SP", results.get(1));
        assertEquals("fr-CA", results.get(2));
        assertEquals("fr-FR", results.get(3));
        assertEquals(4, results.size());
    }

    @Test
    void test_preferredOrderPreservedNoRegion() {
        List<String> supported = new ArrayList<>();
        supported.add("fr-CA");
        supported.add("fr-FR");
        supported.add("en-US");
        List<String> results = solutionInterface.parsedLanguageHeaders("fr-CA, fr, en-US, es-SP", supported);
        assertEquals("fr-CA", results.get(0));
        assertEquals("fr-FR", results.get(1));
        assertEquals("en-US", results.get(2));
        assertEquals(3, results.size());
    }


    @Test
    void test_wildCardWithPreservedOrderFromClient() {
        List<String> supported = new ArrayList<>();
        supported.add("fr-CA");
        supported.add("fr-FR");
        supported.add("en-US");
        supported.add("as-PO");
        List<String> results = solutionInterface.parsedLanguageHeaders("fr-CA, fr, en-US, *", supported);
        assertEquals("fr-CA", results.get(0));
        assertEquals("fr-FR", results.get(1));
        assertEquals("en-US", results.get(2));
        assertEquals("as-PO", results.get(3));
        assertEquals(4, results.size());
    }

}
