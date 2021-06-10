package ua.training.controller.command.admin;

import org.junit.Before;
import org.junit.Test;
import ua.training.controller.command.reader.OrderBook;
import ua.training.model.entity.Book;
import ua.training.model.entity.User;
import ua.training.model.service.BookService;
import ua.training.model.service.OrderService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminHomeTest {
    private UserService mockedUserService;
    private BookService mockedBookService;
    private HttpServletRequest mockedRequest;
    private AdminHome adminHome;

    @Before
    public void setUp() {
        mockedUserService = mock(UserService.class);
        mockedBookService = mock(BookService.class);
        mockedRequest = mock(HttpServletRequest.class);
        adminHome = new AdminHome(mockedUserService, mockedBookService);
    }

    @Test
    public void execute() {
        when(mockedUserService.findAll()).thenReturn(Collections.emptyList());
        when(mockedBookService.findAll()).thenReturn(Collections.emptyList());

        String expected = "/user/admin/home.jsp";
        String actual = adminHome.execute(mockedRequest);

        assertEquals(expected, actual);
    }
}
