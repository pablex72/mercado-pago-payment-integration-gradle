package com.mercadopago;
import com.google.gson.GsonBuilder;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.MerchantOrder;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "**")
@RestController
@RequestMapping("/meli")
public class MercadoPagoController {

    @PostMapping("/createAndRedirect")
    public String createAndRecirect(HttpServletRequest req, @RequestBody ItemDto itemDto) throws MPException {

        String FailureUrl = URLLocation.getBaseUrl(req) + "meli/mercadopago/payment/failure";
        String PendingUrl = URLLocation.getBaseUrl(req) + "meli/mercadopago/payment/pending";
        String SuccessUrl = URLLocation.getBaseUrl(req) + "meli/mercadopago/payment/success";

        Preference preference = new Preference();

        preference.setBackUrls(
                new BackUrls().setFailure(FailureUrl)
                        .setPending(PendingUrl)
                        .setSuccess(SuccessUrl)
        );

        Item item = new Item();
        item.setTitle(itemDto.getTitle())
                .setQuantity(itemDto.getQuantity())
                .setUnitPrice(itemDto.getPrice());

        preference.appendItem(item);

        Preference result = preference.save();

        System.out.println(result.getSandboxInitPoint());
        return "redirect:" + result.getSandboxInitPoint();
    }

    @GetMapping("/success")
    public String success(HttpServletRequest request,
                          @RequestParam("collection_id") String collectionId,
                          Model model
    ) throws MPException {
        var payment = com.mercadopago.resources.Payment.findById(collectionId);

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));

        model.addAttribute("payment", payment);
        return "ok";
    }

    @GetMapping("/failure")
    public String failure(HttpServletRequest request,
                          @RequestParam("collection_id") String collectionId,
                          @RequestParam("merchant_order_id") String merchantOrderId,
                          @RequestParam("preference_id") String preferenceId,
                          Model model
    ) throws MPException {
        model.addAttribute("preference_id", preferenceId);

        var preference = Preference.findById(preferenceId);
        var order = MerchantOrder.findById(merchantOrderId);
        var payment = com.mercadopago.resources.Payment.findById(collectionId);

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(order));
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));

        model.addAttribute("items", preference.getItems());
        model.addAttribute("payment", payment);
        return "failure";
    }

    @GetMapping("/pending")
    public String pending(HttpServletRequest request) throws MPException {
        return "pending";
    }
}
