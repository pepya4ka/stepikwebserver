package servlets;

import models.UserProfile;
import services.AccountService;
import services.exceptions.AccountServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UsersServlet extends HttpServlet {

    private final AccountService accountService;

    public UsersServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String sessionId = req.getSession().getId();

        resp.setContentType("text/html;charset=utf-8");

        UserProfile userProfile = accountService.getUserProfileBySessionId(sessionId);
        if (userProfile != null && userProfile.getLogin().equals("admin")) {
            List<UserProfile> allUsers = Collections.emptyList();
            try {
                allUsers = accountService.getAllUsers();
            } catch (AccountServiceException e) {
                e.printStackTrace();
            }
            resp.getWriter().println(
                    allUsers.stream()
                            .map(UserProfile::toString)
                            .collect(Collectors.joining("\n"))
            );
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
