package SeleniumCucumberGoogleTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AreThereMoreThanEightResults {
    static String areThereMoreThanEightResults(int resultCount) {
        return resultCount > 8 ? "yes" : "no, there are less";
    }
}

public class StepDefinitions {

    private WebDriver driver;
    private String actualAnswer;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.google.de/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    @Given("I search for Selenium in Google")
    public void goToGoogleAndSearchForSelenium() {
        // Verify we are on Google
        Assertions.assertEquals("Google", driver.getTitle());

        // Depending on browser configuration and/or location you could be instructed to
        // confirm usage of cookies
        WebElement cookieConfirmation = driver.findElement(By.id("L2AGLb"));
        if (cookieConfirmation.isDisplayed()) {
            cookieConfirmation.click();
        }

        WebElement searchBox = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchBox.sendKeys("Selenium");
        searchButton.submit();
    }

    @When("I ask whether there are more than 8 results")
    public void askWhetherThereAreMoreThanEightResults() {
        List<WebElement> foundElementsOnFirstPage = driver.findElements(By.className("g"));
        int numberOfResults = foundElementsOnFirstPage.size();
        actualAnswer = AreThereMoreThanEightResults.areThereMoreThanEightResults(numberOfResults);

    }

    @Then("I should be told {string}")
    public void iShouldBeTold(String expectedAnswer) {
        assertEquals(expectedAnswer, actualAnswer);
    }

    @After
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}