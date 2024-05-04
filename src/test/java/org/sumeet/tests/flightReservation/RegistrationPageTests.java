package org.sumeet.tests.flightReservation;

import org.sumeet.AbtractTest;
import org.sumeet.pages.flightreservation.*;
import org.sumeet.tests.flightReservation.model.FlightReservationTestData;
import org.sumeet.utils.Config;
import org.sumeet.utils.Constants;
import org.sumeet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RegistrationPageTests extends AbtractTest {

    private String noOfPassengers;
    private String expectedPrice;
    private RegistrationPage registrationPage;
    private RegistrationConfirmationPage confirmationPage;
    private FlightsSearchPage flightssearchPage;
    private FlightsSelectionPage flightsSelectionPage;
    private FlightConfirmationPage flightConfirmationPage;

    private FlightReservationTestData testData;

    @BeforeTest

    @Parameters({"noOfPassengers", "expectedPrice", "testDataPath"})
    public void setPageObjects(String noOfPassengers, String expectedPrice, String testDataPath){

        this.noOfPassengers = noOfPassengers;
        this.expectedPrice = expectedPrice;
        this.registrationPage= new RegistrationPage(driver);
        this.confirmationPage = new RegistrationConfirmationPage(driver);
        this.flightssearchPage = new FlightsSearchPage(driver);
        this.flightsSelectionPage = new FlightsSelectionPage(driver);
        this.flightConfirmationPage = new FlightConfirmationPage(driver);
        testData = JsonUtils.getTestData(testDataPath, FlightReservationTestData.class);


    }


    @Test
    public void userRegistrationTest(){


        this.registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        driver.manage().window().maximize();
        this.registrationPage.isAt();
        this.registrationPage.enterUserDetails(testData.firstName(), testData.lastName());
        this.registrationPage.enterUserCredentials(testData.email(), testData.password());
        this.registrationPage.enterAddress(testData.street(), testData.city(), testData.zip());
        this.registrationPage.register();

    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void userRegistrationConfirmationTest(){


        Assert.assertTrue(confirmationPage.isAt());
        this.confirmationPage.goToFlightsSearch();

    }

    @Test(dependsOnMethods = "userRegistrationConfirmationTest")
    public void userFlightSearchPageTest(){


        Assert.assertTrue(flightssearchPage.isAt());
        this.flightssearchPage.selectPassengers(noOfPassengers);
        this.flightssearchPage.searchFlights();

    }

    @Test(dependsOnMethods = "userFlightSearchPageTest")
    public void userFlightSelectionPageTest(){


        Assert.assertTrue(this.flightsSelectionPage.isAt());
        this.flightsSelectionPage.selectFlights();
        this.flightsSelectionPage.confirmFlights();

    }

    @Test(dependsOnMethods = "userFlightSelectionPageTest")
    public void userFlightReservationConfirmationPageTest(){


        Assert.assertTrue(flightConfirmationPage.isAt());
        this.flightConfirmationPage.getPrice();
        Assert.assertEquals(this.flightConfirmationPage.getPrice(), expectedPrice);


    }



}
