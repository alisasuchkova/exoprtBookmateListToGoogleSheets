
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;

public class BookmateListPage {

    private static SelenideElement showMoreButton = $(".more-button.more-button_centered");
    private static SelenideElement loginButton = $("#login-button");
    private static SelenideElement authWithEmailButton = $("#auth-with-email");
    private static SelenideElement email = $("input[name='email']");
    private static SelenideElement password = $("input[name='password']");
    private static SelenideElement submitButton = $(".button.button_submit");
    private static final String BOOKS_COLLECTION = "//*[@class='card__body']";
    private static final String AUTHOR = ".authors-list__author";
    private static final String BOOK_TITLE = ".book__title";
    private static final String BOOKMATE_LOGIN_PAGE = "https://bookmate.com/";
    private static final String BOOKMATE_LIST_URL = "https://bookmate.com/bookshelves/BMJqibdZ";

    static Map<String, String> collectBooks() {
        loginToBookmate();
        open(BOOKMATE_LIST_URL);

        while (showMoreButton.isDisplayed()) {
            showMoreButton.click();
        }

        ElementsCollection booksCollection = $$x(BOOKS_COLLECTION);

        Map<String, String> collectedBooks = IntStream.range(0, booksCollection.size()).boxed()
                .collect(Collectors.toMap(i -> booksCollection.get(i).$(AUTHOR).getText(),
                        i -> booksCollection.get(i).$(BOOK_TITLE).getText(), (a, b) -> b));
        close();
        return collectedBooks;
    }

    private static void loginToBookmate() {
        open(BOOKMATE_LOGIN_PAGE);
        loginButton.click();
        authWithEmailButton.click();
        email.setValue("test@gmail.com");
        password.setValue("test");
        if (submitButton.isEnabled()) {
            submitButton.click();
        }
    }
}
