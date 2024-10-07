package FeastList.delivery;

import com.electronwill.nightconfig.core.conversion.Path;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/delivery")
public class DeliveryController {
    public DeliveryController(){
    }

    @GetMapping(path = "/cost")
    public double getDeliveryCost(@RequestParam(value = "address")String userAddress,@RequestParam(value = "vendorName")String vendorName){
        System.err.println(userAddress);
        System.err.println(vendorName);
        return 50.00;

    }

}
