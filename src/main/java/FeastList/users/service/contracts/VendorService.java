package FeastList.users.service.contracts;

import FeastList.users.dto.MiniVendorProjection;
import FeastList.users.dto.VendorDto;

import java.util.List;

public interface VendorService {
    String addNewVendor(VendorDto vendorDto);

    List<MiniVendorProjection> fetchVendors(String sort);
}
