package wikipediaLinkScraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.Driver;

import java.util.HashSet;
import java.util.Set;

public class WikiLinkScraperTest {

    private WebDriver driver;
    private Set<String> visitedLinks;
    private Set<String> uniqueLinks;

    @BeforeTest
    public void setUp() {
        driver = Driver.getDriver(); // Or Driver.getSafariDriver() / Driver.getFirefoxDriver()
        visitedLinks = new HashSet<>();
        uniqueLinks = new HashSet<>();
    }

    @Test
    public void testWikipediaLinkScraper() {
        String wikipediaUrl = "https://en.wikipedia.org/wiki/Main_Page";
        int n = 3;


        uniqueLinks.add(wikipediaUrl);

        for (int cycle = 0; cycle < n; cycle++) {
            Set<String> newLinks = new HashSet<>();

            for (String link : uniqueLinks) {
                if (!visitedLinks.contains(link)) {
                    driver.get(link);

                    WebElement bodyContent = driver.findElement(By.tagName("body"));
                    String pageSource = bodyContent.getAttribute("innerHTML");

                    String[] links = pageSource.split("<a href=\"");
                    for (int i = 1; i < links.length; i++) {
                        String href = links[i].split("\"")[0];
                        if (!href.contains(":")) {
                            newLinks.add("https://en.wikipedia.org" + href);
                        }
                    }

                }
            }

            uniqueLinks.addAll(newLinks);
            visitedLinks.addAll(uniqueLinks);
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
        System.out.println("All found links:");
        for (String link : visitedLinks) {
            System.out.println(link);
        }
        System.out.println("Total count: " + visitedLinks.size());
        System.out.println("Unique count: " + uniqueLinks.size());
    }
}

