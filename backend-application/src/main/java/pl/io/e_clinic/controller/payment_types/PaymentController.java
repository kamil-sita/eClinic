package pl.io.e_clinic.controller.payment_types;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.visit.model.PaymentStatus;
import java.util.*;

@RestController
@RequestMapping("/payment_types")
public class PaymentController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<PaymentStatus> getPaymentStatusList() {
        List<PaymentStatus> paymentStatusList = Arrays.asList(PaymentStatus.values());
        return paymentStatusList;
    }
}
