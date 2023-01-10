package servlets;

import com.google.gson.Gson;
import models.UserProfile;
import services.AccountService;
import services.exceptions.AccountServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private static String OK_MESSAGE = "Authorized: ";
    private static String UNAUTHORIZED_MESSAGE = "Unauthorized";
    private static String FORBIDDEN_MESSAGE = "Bad request";

    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();

        resp.setContentType("text/html;charset=utf-8");

        UserProfile profile = accountService.getUserProfileBySessionId(sessionId);
        if (profile == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(profile);
            resp.getWriter().println(json);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionId = req.getSession().getId();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        if (isNotCorrectParameter(login) && isNotCorrectParameter(password)) {
            setWriter(HttpServletResponse.SC_BAD_REQUEST, FORBIDDEN_MESSAGE, resp);
        } else {
            UserProfile profile = null;
            try {
                profile = accountService.getUser(login);
            } catch (AccountServiceException e) {
                e.printStackTrace();
            }
            if (profile != null && profile.getPassword().equals(password)) {
                accountService.addSession(sessionId, profile);
                setWriter(HttpServletResponse.SC_OK, OK_MESSAGE + login, resp);
            } else {
                setWriter(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_MESSAGE, resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserProfileBySessionId(sessionId);
        resp.setContentType("text/html;charset=utf-8");
        if (profile == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            resp.getWriter().println("Goodbye!");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private boolean isNotCorrectParameter(String parameter) {
        return parameter == null || parameter.isEmpty();
    }

    private void setWriter(int status, String message, HttpServletResponse resp) throws IOException {
        resp.setStatus(status);
        resp.getWriter().println(message);
    }
}