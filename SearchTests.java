package tests;

import factories.SearchPageObjectFactory;
import io.qameta.allure.*;
import lib.CoreTestCase;
import lib.Platform;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.SearchPageObject;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Search tests")
public class SearchTests extends CoreTestCase {

    @Test
    @Feature(value = "Search")
    @DisplayName("Wait for search result")
    @Description("Wait for search result by search line 'Java'")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Cancel search")
    @Description("Cancel search by clicking cancel button")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCancelSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify entry field text")
    @Description("Init search input and check that entry field contains text 'Search'")
    @Step("Starting test testEntryFieldContainsText")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testEntryFieldContainsText() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.verifyTextInSearchLine("Search");
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Wait for search result and cancel search")
    @Description("Wait for search result by search line 'Java' and cancel search")
    @Step("Starting test testVerifyAndCancelSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testVerifyAndCancelSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        searchPageObject.assertSearchResultsPresent();
        searchPageObject.clickCancelSearch();
        searchPageObject.assertSearchResultsNotPresent();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check correctness of search results")
    @Description("Wait for search result by search line 'Java' and checking the results for the present of the entered line")
    @Step("Starting test testCheckingWordsInSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCheckingWordsInSearch() {

        String search_text = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_text);
        searchPageObject.checkCorrectnessOfSearchResults(search_text);
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Not empty search results")
    @Description("Wait for search result by search line 'Linkin Park Discography' and verify for not empty search results")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testAmountOfNotEmptySearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();
        assertTrue(
                amount_of_search_results > 0,
                "We found too few results"
        );
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Empty search results")
    @Description("Wait for search result by search line 'ngdfgsdg' and verify for empty search results")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testAmountOfEmptySearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "ngdfgsdg";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify first three search results")
    @Description("Wait for search result by search line 'Java' and verify that first three results contains search line")
    @Step("Starting test testVerifyFirstThreeSearchResults")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testVerifyFirstThreeSearchResults() {

        String search_text = "Java";
        String first_result_title = "Java";
        String second_result_title = "JavaScript";
        String third_result_title = "Java (programming language)";
        String first_result_description;
        String second_result_description;
        if (!Platform.getInstance().isMobileWeb()) {
            first_result_description = "Island of Indonesia";
            second_result_description = "Programming language";
        } else {
            first_result_description = "Indonesian island";
            second_result_description = "High-level programming language";
        }
        String third_result_description = "Object-oriented programming language";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_text);
        searchPageObject.waitForElementByTitleAndDescription(first_result_title, first_result_description);
        searchPageObject.waitForElementByTitleAndDescription(second_result_title, second_result_description);
        searchPageObject.waitForElementByTitleAndDescription(third_result_title, third_result_description);
    }
}