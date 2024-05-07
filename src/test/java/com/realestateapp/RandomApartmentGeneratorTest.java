package com.realestateapp;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RandomApartmentGeneratorTest {

    // Tests with default constructor
    @RepeatedTest(value = 100)
    void should_GenerateCorrectApartment_When_DefaultMinAreaMinPrice(){
        // given
        RandomApartmentGenerator defaultRandomApt = new RandomApartmentGenerator();
        Apartment actualDefaultApartment = defaultRandomApt.generate();

        // then
        double actualArea = actualDefaultApartment.getArea();
        BigDecimal actualPrice = actualDefaultApartment.getPrice();

        System.out.println("Area: " + actualArea);
        System.out.println("Price: " + actualPrice);

        // when
        assertAll(
                () -> assertTrue(actualArea >= 30), // test min area
                () -> assertTrue(actualPrice.compareTo(BigDecimal.valueOf(actualArea * 3000)) >= 0), // test min price
                () -> assertTrue(actualArea <= (30 * 4)), // test max area
                () -> assertTrue(actualPrice.compareTo(BigDecimal.valueOf(actualArea * 3000 * 4)) <= 0) // test max price
        );

        }

        // Tests with custom area and price
        @ParameterizedTest (name = "area = {0}, price per square meter = {1}")
        @CsvFileSource( resources = "/correct-apt-test-input-data.csv", numLinesToSkip = 1)
        void should_GenerateCorrectApartment_When_CustomMinAreaMinPrice(double minA, BigDecimal minPPSM){
            // given
            double minArea = minA;
            BigDecimal minPricePerSquareMeter = minPPSM;

            RandomApartmentGenerator customRandomApt = new RandomApartmentGenerator(minArea, minPricePerSquareMeter);
            Apartment actualCustomApt = customRandomApt.generate();

            // then
            double actualCustomArea = actualCustomApt.getArea();
            BigDecimal actualCustomPrice = actualCustomApt.getPrice();

            System.out.println("Custom Apt Area: " + actualCustomArea);
            System.out.println("Custom Apt Price: " + actualCustomPrice);

            // when
            assertAll(
                    () -> assertTrue(actualCustomArea >= minA), // test min area
                    () -> assertTrue(actualCustomPrice.compareTo(minPPSM.multiply(BigDecimal.valueOf(actualCustomArea))) >= 0), // test min price
                    () -> assertTrue(actualCustomArea <= (minA * 4)), // test max area
                    () -> assertTrue(actualCustomPrice.compareTo(BigDecimal.valueOf(actualCustomArea * 4).multiply(minPPSM)) <= 0) // test max price
            );
        }

}

