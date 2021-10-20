package tests;

import factories.ArticlePageObjectFactory;
import factories.SearchPageObjectFactory;
import io.qameta.allure.*;
import lib.CoreTestCase;
import lib.Platform;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import pages.ArticlePageObject;
import pages.SearchPageObject;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Epic("Tests on app condition")
public class ChangeAppConditionTests extends CoreTestCase {

    @Test

    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Change screen orientation on search result page")
    @Description("Open search page and enter search input. Change screen orientation, make sure that article title still " +
            "exist on the page. Change screen orientation one more time and repeat check")
    @Step("Starting test testChangeScreenOrientationOnSearchResults")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testChangeScreenOrientationOnSearchResults() {

        if (!Platform.getInstance().isAndroid()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
       
        searchPageObject.clickByArticleWithDescription("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        String title_before_rotation = articlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_rotation = articlePageObject.getArticleTitle();
        assertEquals(
                title_before_rotation,
                title_after_rotation,
                "article title have been change after screen rotation"
        );
        this.rotateScreenPortrait();
        String new_title_after_rotation = articlePageObject.getArticleTitle();
        assertEquals(
                title_before_rotation,
                new_title_after_rotation,
                "article title have been change after screen rotation"
        );
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check search result after background")
    @Description("Open search page and enter search input. Send the app to the background for a few seconds, then " +
            "make sure that article title still exist on the page")
    @Step("Starting test testCheckSearchArticleInBackground")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCheckSearchArticleInBackground() {

        if (!Platform.getInstance().isAndroid()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundUp(ofSeconds(2));
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}