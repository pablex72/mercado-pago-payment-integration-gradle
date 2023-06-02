package com.mercadopago;

import com.google.gson.GsonBuilder;
import com.mercadopago.exceptions.MPException;

import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;

import com.mercadopago.resources.datastructures.preference.Phone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mercadopago")
public class MercadoPagoController {

    @GetMapping("/pay")
    public String createAndRecirect(HttpServletRequest req) throws MPException {

        String FailureUrl = URLLocation.getBaseUrl(req) + "/mercadopago/failure";
        String PendingUrl = URLLocation.getBaseUrl(req) + "/mercadopago/pending";
        String SuccessUrl = URLLocation.getBaseUrl(req) + "/mercadopago/success";

        Preference preference = new Preference();

        preference.setBackUrls(
                new BackUrls().setFailure(FailureUrl)
                        .setPending(PendingUrl)
                        .setSuccess(SuccessUrl)
        );

        Item item = new Item();
        item.setTitle("Radio Sony");
        item.setQuantity(2);
        item.setUnitPrice(55.0f);
        item.setCurrencyId("ARS");
        preference.appendItem(item);

        Preference result = preference.save();

        System.out.println(result.getSandboxInitPoint());
        return "redirect:" + result.getSandboxInitPoint();
    }

    @GetMapping("/success")
    public ResponseEntity<String> success(@RequestParam("collection_id") String collectionId) throws MPException {

        Payment payment = Payment.findById(collectionId);

        String response = "Payment ID: " + payment.getId() +
                ", Date Created: " + payment.getDateCreated() +
                ", Status: " + payment.getStatus() +
                ", Status Detail: " + payment.getStatusDetail() +
                ", PaymentMethod: " + payment.getPaymentMethodId() +
                ", TotalPaid: " + payment.getTransactionAmount() +
                ", Currency: " + payment.getCurrencyId() ;

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(response));
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/failure")
    public ResponseEntity<?> failure(@RequestParam("collection_id") String collectionId) throws MPException {

        Payment payment = Payment.findById(collectionId);

        String response = "Payment ID: " + payment.getId() +
                ", Date Created: " + payment.getDateCreated() +
                ", Status: " + payment.getStatus() +
                ", Status Detail: " + payment.getStatusDetail() +
                ", PaymentMethod: " + payment.getPaymentMethodId() +
                ", TotalPaid: " + payment.getTransactionAmount() +
                ", Currency: " + payment.getCurrencyId() ;

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(response));
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pending")
    public ResponseEntity<String> pending(@RequestParam("collection_id") String collectionId) throws MPException {

        Payment payment = Payment.findById(collectionId);

        String response = "Payment ID: " + payment.getId() +
                ", Date Created: " + payment.getDateCreated() +
                ", Status: " + payment.getStatus() +
                ", Status Detail: " + payment.getStatusDetail() +
                ", PaymentMethod: " + payment.getPaymentMethodId() +
                ", TotalPaid: " + payment.getTransactionAmount() +
                ", Currency: " + payment.getCurrencyId() ;

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(response));
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
