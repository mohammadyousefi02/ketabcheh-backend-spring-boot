package com.mohammadyousefi.ketabcheh.paymentGateway;

import com.mohammadyousefi.ketabcheh.captcha.CaptchaProccessor;
import com.mohammadyousefi.ketabcheh.order.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentGatewayController {
    private final CaptchaProccessor captchaProccessor;
//    private final OrderService orderService;

    public PaymentGatewayController(CaptchaProccessor captchaProccessor) {
        this.captchaProccessor = captchaProccessor;
//        this.orderService = orderService;
    }

    @PostMapping("/payment-gateway")
    public String getView1() {
        return "redirect:payment-gateway-view";
    }

    @GetMapping("/payment-gateway-view")
    public String getView() {
        return "paymentGateway";
    }

    @PostMapping("/checking-payment")
    public String pay(@ModelAttribute PayDto payDto, HttpServletRequest request) {
        String response = request.getParameter("g-recaptcha-response");
//        Boolean captchaResponse = captchaProccessor.proccessResponse(response);
        return "paymentGateway";
    }

    @GetMapping("/cancelpp")
    @ResponseBody
    public String cancelp() {
        return "test";
    }
}
