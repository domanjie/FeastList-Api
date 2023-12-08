package FeastList.tray;

import FeastList.meals.Meal;
import FeastList.meals.MealItem;
import FeastList.meals.MealType;
import FeastList.menuItem.MenuItem;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrayRepositoryJdbcImpl implements TrayRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public  TrayRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public void emptyUserTray(String userId ) {
        var query= """
                DELETE FROM tray
                WHERE id=:userId
                """;
        var param=new MapSqlParameterSource()
                .addValue("userId",userId);

        jdbcTemplate.update(query,param);
    }

    @Override
    public void addToTray(TrayItemDto trayItemDto,String client) {
        var query= """
                INSERT INTO tray(meal_id,client_id,amount)
                VALUES (:)
                """;
        var  params=new MapSqlParameterSource()
                .addValue("mealId",trayItemDto.meal().getId())
                .addValue("clientId",client)
                .addValue("amount",trayItemDto.amount());
        jdbcTemplate.update(query,params);
    }

    @Override
    public Tray getTray(String clientId) {
        var query= """
                SELECT z.amount AS tray_items_amount,z.client_id,
                a.id,a.name,a.price,a.avatar_url,
                a.user_id,a.meal_type,a.date_added,
                b.amount,
                c.id AS menu_item_id ,c.name AS menu_item_name,c.price_per_portion AS menu_item_ppp,c.vendor_id,
                c.avatar_url AS menu_item_avatar_url ,c.date_added AS menu_item_date_added
                FROM tray_items AS z
                JOIN
                meals AS a
                ON z.meal_id=a.id
                LEFT JOIN
                A_LA_CARTE_meal_items AS b
                ON a.id=b.meal_id
                LEFT JOIN menu_items AS c
                ON b.menu_item_id=c.id
                WHERE client_id=:clientId
                """;
        var trayItems=jdbcTemplate.query(query,trayItemsExtractor());
        return new Tray(trayItems,clientId);
    }

    @Override
    public void deleteFromTray(Long mealId,String clientId) {
        var query= """
             DELETE FROM tray_items WHERE
             meal_id=:mealId
             AND
             client_id=:clientId
             """;
        var params =new MapSqlParameterSource()
                .addValue("mealId",mealId)
                .addValue("clientId",clientId);
        jdbcTemplate.update(query, params);
    }
    private ResultSetExtractor<List<TrayItemDto>> trayItemsExtractor(){
        return (resultSet)->{

            var objectMap=new HashMap<Integer,TrayItemDto>();

            Meal meal=null;

            while (resultSet.next()){

                var id=resultSet.getInt("id");

                if(objectMap.get(id)==null){
                var trayItemDto=new TrayItemDto(
                        meal=new Meal(
                                id,
                                resultSet.getString("name"),
                                resultSet.getDouble("price"),
                                resultSet.getTimestamp("date_added"),
                                MealType.valueOf(resultSet.getString("meal_type")),
                                resultSet.getString("avatar_url"),
                                resultSet.getString("user_id"),
                                null
                        ),resultSet.getInt("tray_items_amount")
                );

                    objectMap.put(id,trayItemDto);
                };

                if (meal.getMealType().equals(MealType.A_LA_CARTE)) {
                    Set<MealItem> mealItems;
                    if (meal.getMealItems() == null){
                        mealItems=new HashSet<>();
                    }else {
                        mealItems=new HashSet<>(meal.getMealItems());
                        meal.getMealItems().clear();
                    };
                    var mealItem= new MealItem(
                            new MenuItem(
                                    resultSet.getInt("menu_item_id"),
                                    resultSet.getString("menu_item_name"),
                                    resultSet.getDouble("menu_item_ppp"),
                                    resultSet.getString("vendorId"),
                                    resultSet.getString("menu_item_avatar_url"),
                                    resultSet.getTimestamp("menu_item_date_added")
                            ),
                            resultSet.getInt("amount")
                    );
                    mealItems.add(mealItem);
                    meal.setMealItems(mealItems);
                }
            }
            return new ArrayList<TrayItemDto>(objectMap.values());
        };
    }

}
