package kstd.bedev.task.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/front")
public class FrontController {

    // dashboard
    @GetMapping({"/", ""})
    public String index() {
        return "front/index";
    }

    // myReservations
    @GetMapping("/reservations")
    public String reservations() {
        return "front/reservations";
    }

    @GetMapping("/lectures/hot")
    public String lecturesHot() {
        return "front/popular";
    }

}
