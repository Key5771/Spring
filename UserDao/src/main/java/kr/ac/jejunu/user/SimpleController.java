package kr.ac.jejunu.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor // final로 정의된 것만 생성자로 만들어줌.
public class SimpleController implements  Controller {
    private final UserDao userDao;
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = userDao.get(Integer.valueOf(request.getParameter("id")));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}