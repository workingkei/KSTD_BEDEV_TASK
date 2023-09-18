package kstd.bedev.task.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    // dashboard
    @GetMapping({"/", ""})
    public String dashboard() {
        return "admin/dashboard";
    }

    // lectures
    @GetMapping("/lectures")
    public String lectures() {
        return "admin/lectures";
    }

}
