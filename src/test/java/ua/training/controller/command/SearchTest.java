package ua.training.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.training.model.service.BookService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;

public class SearchTest {
    private BookService mockedBookService;
    private HttpServletRequest mockedRequest;
    private Search signupCommand;

    @Before
    public void setUp() {
        mockedBookService = mock(BookService.class);
        mockedRequest = mock(HttpServletRequest.class);
        signupCommand = new Search(mockedBookService);
    }

    @Test
    public void getSearchFirstPage() {
    }
}