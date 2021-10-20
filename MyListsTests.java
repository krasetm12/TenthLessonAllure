package tests;
import factories.ArticlePageObjectFactory;
import factories.MyListsPageObjectFactory;
import factories.NavigationUIFactory;
import factories.SearchPageObjectFactory;
import io.qameta.allure.*;
import lib.CoreTestCase;
import lib.Platform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.*;

@Epic("Tests adding articles to my list")
public class MyListsTests extends CoreTestCase {

    private static final String folder_name = "Learning programming";
    private static final String
            LOGIN = "Vitorpg1992",
            PASSWORD = "Vit19922411";

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"),
            @Feature(value = "Navigation"), @Feature(value = "My lists")})
    @DisplayName("Save article to my list")
    @Description("Open article with search input. Save article to my list. Open my lists and remove article from it")
    @Step("Starting test testSaveFirstArticleToMyList")
    @Severity(value = SeverityLevel.MINOR)
    public void testSaveFirstArticleToMyList() throws InterruptedException {

        String article_title = "Appium";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(article_title);
        searchPageObject.clickByArticleWithTitle(article_title);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            articlePage.addArticleToMyListForTheFirstTime(folder_name);
        } else if (Platform.getInstance().isIOS()){
            articlePage.addArticlesToMySavedForTheFirstTime();
        } else {
            articlePage.addArticlesToMySaved();
            Thread.sleep(1000);
            AuthorizationPageObject authorizationPage = new AuthorizationPageObject(driver);
            authorizationPage.clickAuthorizationButton();
            authorizationPage.enterLoginData(LOGIN, PASSWORD);
            authorizationPage.submitForm();

            
            articlePage.addArticlesToMySaved();
        }
        articlePage.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        Thread.sleep(2000);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folder_name);
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"),
            @Feature(value = "Navigation"), @Feature(value = "My lists")})
    @DisplayName("Save two articles to my list")
    @Description("Open article with search input. Save article to my list. Open second article with search input and " +
            "save it to my list. Open my lists and remove one article from it. Check existing of the second article in my list")
    @Step("Starting test testSaveTwoArticlesToMyList")
    @Severity(value = SeverityLevel.MINOR)
    public void testSaveTwoArticlesToMyList() throws InterruptedException {

        String folder_name = "Learning programming";
        String first_title = "Java (programming language)";
        String second_title = "Python (programming language)";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithTitle(first_title);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            articlePage.addArticleToMyListForTheFirstTime(folder_name);
        } else if (Platform.getInstance().isIOS()){
            articlePage.addArticlesToMySavedForTheFirstTime();
        } else {
            articlePage.addArticlesToMySaved();
            Thread.sleep(1000);
            AuthorizationPageObject authorizationPage = new AuthorizationPageObject(driver);
            authorizationPage.clickAuthorizationButton();
            authorizationPage.enterLoginData(LOGIN, PASSWORD);
            authorizationPage.submitForm();
            Assertions.assertEquals(
                    first_title,
                    articlePage.getArticleTitle(),
                    "We are not on the same page after login."
            );
            articlePage.addArticlesToMySaved();
        }
        articlePage.closeArticle();
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Python");
        searchPageObject.clickByArticleWithTitle(second_title);
        if (Platform.getInstance().isAndroid()) {
            articlePage.addArticleIntoExistingMyList(folder_name);
        } else {
            articlePage.addArticlesToMySaved();
        }
        articlePage.closeArticle();
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folder_name);
        }
        myListsPageObject.swipeByArticleToDelete(first_title);
        myListsPageObject.waitForArticleToAppearByTitle(second_title);
    }
}