package org.sumeet.tests.vendorPortal;

import org.sumeet.AbtractTest;
import org.sumeet.pages.vendorPortal.DashboardPage;
import org.sumeet.pages.vendorPortal.LoginPage;
import org.sumeet.tests.vendorPortal.model.VendorPortalTestData;
import org.sumeet.utils.Config;
import org.sumeet.utils.Constants;
import org.sumeet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VendorPortalTests extends AbtractTest {


    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VendorPortalTestData testData;


    @BeforeTest
    @Parameters({"testDataPath"})
    public void setPageObjects(String testDataPath){

        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        this.testData = JsonUtils.getTestData(testDataPath, VendorPortalTestData.class);

    }

    @Test
    public void loginTest(){

        loginPage.goTo(Config.get(Constants.VENDOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isAt());
        loginPage.login(testData.username(), testData.password());

    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest(){

        Assert.assertTrue(dashboardPage.isAt());

        // finance metrics
        Assert.assertEquals(dashboardPage.getMonthlyEarning(), testData.monthlyEarning());
        Assert.assertEquals(dashboardPage.getAnnualEarning(), testData.annualEarning());
        Assert.assertEquals(dashboardPage.getProfitMargin(), testData.profitMargin());
        Assert.assertEquals(dashboardPage.getAvailableInventory(), testData.availableInventory());

        // order history search
        dashboardPage.searchOrderHistoryBy(testData.searchKeyword());
        Assert.assertEquals(dashboardPage.getSearchResultsCount(), testData.searchResultsCount());
    }

    @Test(dependsOnMethods = "dashboardTest")
    public void logoutTest(){
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isAt());
    }


    @AfterTest
    public void close(){
        driver.quit();
    }
}
