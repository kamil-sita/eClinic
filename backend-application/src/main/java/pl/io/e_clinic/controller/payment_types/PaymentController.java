package pl.io.e_clinic.controller.payment_types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.visit.model.PaymentStatus;
import pl.io.e_clinic.services.FilteringService;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment_types")
public class PaymentController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<PaymentStatus> getPaymentStatusList() {
        List<PaymentStatus> paymentStatusList = Arrays.asList(PaymentStatus.values());
        return paymentStatusList;
    }
}
