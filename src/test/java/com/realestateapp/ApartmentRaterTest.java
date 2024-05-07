package com.realestateapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentRaterTest {

    @Nested
    class RateApartmentTests{
        @RepeatedTest(10)
        void should_ReturnNegativeOne_When_AreaZero() {

            // given
            BigDecimal randomPrice = new BigDecimal(Math.random());
            Apartment apartment = new Apartment(0.0, randomPrice);

            // when
            double actual = ApartmentRater.rateApartment(apartment);

            // then
            assertEquals(-1.0, actual);
        }

        @ParameterizedTest(name = "area = {0}, price = {1}")
        @CsvSource({
                "1000, 6000.99",
                "40, 82388.8924",
                "40000, 5239572.998924",
                "2560, 7399.65",
                "333333.33, 212121999.89333333333"
        })
        void should_ReturnZero_when_BestPriceToAreaRating(double areaInput, String priceInput) {
            double apartmentArea = areaInput;
            BigDecimal apartmentPrice = new BigDecimal(priceInput);
            Apartment apartment = new Apartment(apartmentArea, apartmentPrice);

            // when
            int actual = ApartmentRater.rateApartment(apartment);

            // then
            assertEquals(0, actual);
        }

        @ParameterizedTest(name = "area = {0}, price = {1}")
        @CsvSource({
                "100, 600000.00",
                "133.3333, 1000000",
                "40909.8347, 250000000",
                "0.0040909, 25",
                "577.33, 4612289.37"
        })
        void should_ReturnOne_when_CheapThresholdPriceToAreaRating(double areaInput, String priceInput) {
            double apartmentArea = areaInput;
            BigDecimal apartmentPrice = new BigDecimal(priceInput);
            Apartment apartment = new Apartment(apartmentArea, apartmentPrice);

            // when
            int actual = ApartmentRater.rateApartment(apartment);

            // then
            assertEquals(1, actual);
        }

        @ParameterizedTest(name = "area = {0}, price = {1}")
        @CsvSource({
                "100, 6000000000.99",
                "4099.88, 81255558888888.8924",
                "4, 52395720000000000000.998924",
                "600.88, 739998744.65869",
                "33.33, 121999000.89333333333"
        })
        void should_ReturnTwo_when_ModerateThresholdPriceToAreaRating(double areaInput, String priceInput) {
            double apartmentArea = areaInput;
            BigDecimal apartmentPrice = new BigDecimal(priceInput);
            Apartment apartment = new Apartment(apartmentArea, apartmentPrice);

            // when
            int actual = ApartmentRater.rateApartment(apartment);

            // then
            assertEquals(2, actual);
        }
    }

    @Nested
    class CalculateAverageRatingTests {
        @Test
        void should_ThrowArithmeticException_When_CalculateFromEmptyApartmentList() {
            // given
            List<Apartment> apartments = new ArrayList<>();

            // when
            Executable executable = () -> ApartmentRater.calculateAverageRating(apartments);

            // then
            assertThrows(RuntimeException.class, executable);
        }

        @Test
        void should_CalculateAverageRating_WhenGivenApartmentList(){
            // given
            List<Apartment> apartments = new ArrayList<>();
            apartments.add(new Apartment(100, new BigDecimal(600000.00)));
            apartments.add(new Apartment(40, new BigDecimal(82388.8924)));
            apartments.add(new Apartment(4, new BigDecimal(52395720000000000000.998924)));
            apartments.add(new Apartment(600.88, new BigDecimal(739998744.65869)));

            // when
            double actualAverage = ApartmentRater.calculateAverageRating(apartments);

            // then
            double expected = 1.25;
            assertEquals(expected, actualAverage);
        }
    }
}