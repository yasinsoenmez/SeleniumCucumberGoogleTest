package SeleniumCucumberGoogleTest;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

    private WebDriver driver;
    private String actualAnswer;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(co);
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
        actualAnswer = numberOfResults > 8 ? "yes" : "no, there are less";

    }

    @Then("I should be told {string}")
    public void iShouldBeTold(String expectedAnswer) {
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Then("Execute Accessibility Tests")
    public void executeAccessibilityTests() throws FileNotFoundException {
        AxeBuilder axeBuilder = new AxeBuilder()
                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag2aaa", "wcag21a", "wcag21aa", "wcag22aa"));

        Results results = axeBuilder.analyze(driver);

        AxeReporter.getReadableAxeResults(
                "Test", driver, results.getViolations());
        try (Writer writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new FileOutputStream("target/accessibility" + ".txt"), StandardCharsets.UTF_8))) {
            writer.write(AxeReporter.getAxeResultString());
        } catch (IOException ignored) {
        }


        String reportFile = "target/a11y";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        AxeReporter.writeResultsToJsonFile(reportFile, results);
        JsonElement jsonElement = JsonParser.parseReader(new FileReader(reportFile + ".json"));
        String prettyJson = gson.toJson(jsonElement);
        AxeReporter.writeResultsToTextFile(reportFile, prettyJson);

    }

    @After
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}