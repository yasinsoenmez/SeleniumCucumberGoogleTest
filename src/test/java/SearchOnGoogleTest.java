/*

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class SearchOnGoogleTest {

    // 1. Open the web browser
    // 2. Navigate to the web application
    // http:// www.google.de
    // Find elements: Locate the element, Determine the action, Pass any parameters
    // 3. Enter search term
    // 4. Click Search
    // 5. Confirm result
    // 6. Close the browser

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void test() {


        driver.get("https://www.google.de/");
        String title = driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        // Verify
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

        // Count the number of elements with class name "g" which is used for search results
        // Assert 12 results based on search term "Selenium". Other search terms may have different
        // number of result. e.g. "heise" has 11.
        // Also Firefox doesn't seem to work finding only 3-4 results.
        // This doesn't seem to be a good test since google search is very interactive
        List<WebElement> foundElementsOnFirstPage = driver.findElements(By.className("g"));
        Assertions.assertEquals(13, foundElementsOnFirstPage.size());
    }
}*/