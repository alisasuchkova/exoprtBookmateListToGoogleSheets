import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.junit.BeforeClass;
import org.junit.Test;
import sheets.SheetsServiceUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleSheetsIntegrationTest {
    private static Sheets sheetsService;
    private static Map<String, String> collectedBookList = new HashMap<>();
    private static final String SPREADSHEET_ID = "1yDYr9G882_0DodwYnVddEnru0FM_B3bCT5Cs52d6BwA";

    @BeforeClass
    public static void setup() throws GeneralSecurityException, IOException {
        collectedBookList = BookmateListPage.collectBooks();
        sheetsService = SheetsServiceUtil.getSheetsService();
    }

    @Test
    public void writeCollectedBooksToSheet() throws IOException {
        List<List<Object>> listedBooks = Arrays.asList(Arrays.asList("Author", "Book"));
        List<List<Object>> listedBooksAlso = new ArrayList<>(listedBooks);

        collectedBookList.forEach((key, value) -> listedBooksAlso.add(Arrays.asList(key, value)));

        ValueRange body = new ValueRange().setValues(listedBooksAlso);
        sheetsService.spreadsheets().values().update(SPREADSHEET_ID, "A1", body).setValueInputOption("RAW").execute();

        // assert
    }
}
