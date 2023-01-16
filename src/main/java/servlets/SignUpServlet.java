package servlets;

import services.AccountService;
import services.exceptions.AccountServiceException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {

    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        if (isNotCorrectParameter(login) && isNotCorrectParameter(password)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                accountService.addUser(login, password);
            } catch (AccountServiceException e) {
                e.printStackTrace();
            }
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private boolean isNotCorrectParameter(String parameter) {
        return parameter == null || parameter.isEmpty();
    }
}