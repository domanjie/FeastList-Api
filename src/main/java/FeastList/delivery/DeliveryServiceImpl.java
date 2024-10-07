package FeastList.delivery;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeliveryServiceImpl implements  DeliveryService {

    @Override
    public double determineDeliveryCost(Object ClientLocation, Object VendorLocation) {
        return 0;
    }
}
