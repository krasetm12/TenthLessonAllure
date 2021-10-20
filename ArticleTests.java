package tests;

import factories.ArticlePageObjectFactory;
import factories.SearchPageObjectFactory;
import io.qameta.allure.*;
import lib.CoreTestCase;
import lib.Platform;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ArticlePageObject;
import pages.SearchPageObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We open 'Java Object-oriented programming language' article and make sure the title is expected by assert")
    @Step("Starting test testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() {

        if (Platform.getInstance().isIOS()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
       
        searchPageObject.clickByArticleWithDescription("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = articlePageObject.getArticleTitle();

//        articlePageObject.takeScreenshot("article_page");

        assertEquals(
                "Java (programming language)",
                article_title,
                "We see unexpected title"
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Swipe article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {

        String search_input = "Appium";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_input);
        searchPageObject.clickByArticleWithTitle(search_input);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(search_input);
        articlePageObject.swipeToFooter();
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Check article title is an expected")
    @Description("We open 'Appium' article and make sure the title is expected by method")
    @Step("Starting test testOpenArticleAndCheckTitle")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testOpenArticleAndCheckTitle() {

        String search_input = "Appium";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_input);
        searchPageObject.clickByArticleWithTitle(search_input);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.checkArticleTitlePresent(search_input);
    }
}