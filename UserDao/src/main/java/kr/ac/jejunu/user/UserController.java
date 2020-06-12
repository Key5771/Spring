package kr.ac.jejunu.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDao;

    @RequestMapping(path = "/user")
    // return 값이 String이면 기본적으로 view의 이름이다.
    // forward, redirect 방법이 있다.
    public String getUser(@ModelAttribute User user) {
        Integer id = user.getId();
        User user2 = userDao.get(id);
        user.setName(user2.getName());
        return "redirect:/upload";
    }

    @RequestMapping("/exception")
    public void exception() {
        throw new RuntimeException("어이쿠!");
    }

    @GetMapping("/upload")
    public void upload() {

    }

    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws IOException {
        File path = new File(request.getServletContext().getRealPath("/") + "/WEB-INF/static/" + file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", "/images/" + file.getOriginalFilename());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView error(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("e", e);
        return modelAndView;
    }
}
