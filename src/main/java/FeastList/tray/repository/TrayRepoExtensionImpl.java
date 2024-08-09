package FeastList.tray.repository;

import FeastList.tray.dto.out.TrayItemDtoOut;
import FeastList.tray.dto.out.VendorTrayItemsDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class TrayRepoExtensionImpl implements TrayRepoExtension {

    private final EntityManager entityManager;
    public TrayRepoExtensionImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    };

    @Override
    public List<VendorTrayItemsDto> getClientTray(String clientId) {
       var query="""
        SELECT u.avatar_url,v.vendor_name,pmm.id,pmm.meal_name,pmm.price,pmm.avatar_url,t.amount FROM
        tray t
        LEFT JOIN
        pre_made_meal pmm
        ON t.meal_id=pmm.id
        LEFT JOIN meals m
        ON pmm.id=m.id
        LEFT JOIN
        vendors v
        ON m.vendor_name=v.vendor_name
        LEFT JOIN users u
        ON v.vendor_name=u.user_id
        WHERE  t.client_id =:clientId
        ORDER BY t.added_at , v.vendor_name;
        """;
       List<Object[]> resultSet= entityManager.createNativeQuery(query).setParameter("clientId",clientId).getResultList();


       var result=new ArrayList<VendorTrayItemsDto>();

       for(Object[] row :resultSet){
           var vendorAvatar=(String)row[0];
           var vendorName=  (String) row[1];
           var mealId=(UUID)row[2];
           var mealName=(String)row[3];
           var price=(BigDecimal)row[4];
           var mealAvatar=(String)row[5];
           var amount=(int)row[6];


           var trayItemDto=TrayItemDtoOut.builder()
                   .itemId(mealId)
                   .price(price)
                   .mealName(mealName)
                   .mealAvatar(mealAvatar)
                   .price(price)
                   .amount(amount)
                   .build();
           if(result.isEmpty()||!Objects.equals(result.get(result.size() - 1).vendorName(), vendorName) ){
               var trayItems=new ArrayList<TrayItemDtoOut>();
               trayItems.add(trayItemDto);
               var vendorTrayItems= VendorTrayItemsDto
                       .builder()
                       .vendorAvatar(vendorAvatar)
                       .vendorName(vendorName)
                       .trayItems(trayItems)
                       .build();
               result.add(vendorTrayItems);
           }else{
               result.get(result.size()-1).trayItems().add(trayItemDto);
           }
       }
       return result;
    }
}
