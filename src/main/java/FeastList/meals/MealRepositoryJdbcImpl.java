package FeastList.meals;
import FeastList.menuItems.MenuItem;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.*;

public class MealRepositoryJdbcImpl implements MealRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MealRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public int save(Meal meal) {
        var query= """
                INSERT INTO meals(name,price,user_id,meal_type,avatar_url)
                VALUES (:name,:price,:userId,:mealType,:avatarUrl)
                """;
        var params=new MapSqlParameterSource()
                .addValue("name",meal.getName())
                .addValue("price",meal.getPrice())
                .addValue("userId",meal.getUserId())
                .addValue(":mealType",meal.getMealType().toString())
                .addValue(":avatarUrl",meal.getAvatarUrl());

        var keyHolder =new GeneratedKeyHolder();
        jdbcTemplate.update(query,params,keyHolder);
        var mealId= Objects.requireNonNull(keyHolder.getKey()).intValue();
        if(meal.getMealType().equals(MealType.A_LA_CARTE))
        {
            var query2= """
                    INSERT INTO A_LA_CARTE_meal_items(meal_id, menu_item_id,amount)
                    VALUES (:mealId,:menuItem,:amount)
                    """;
            batchInsertClientMealItems(query2,meal.getMealItems(),mealId );
        }
        return mealId;
    }

    @Override
    public Optional<Meal> getMealById(Long mealId) {
        String query= """
                SELECT id,name,price,date_added,meal_type,avatar_url,user_id,menu_item_id,amount
                FROM meals
                LEFT JOIN
                A_LA_CARTE_meal_items
                ON id=meal_id
                WHERE id=:mealId
                """;
        var param =new MapSqlParameterSource().addValue(":mealId",mealId);
        var mealCollection= jdbcTemplate.query(query,param,mealExtractor());
        return Optional.of(mealCollection.get(0));
    }

    @Override
    public Meal deleteMealById(Long mealId) {
        return null;
    }

    @Override
    public List<Meal> getMealByRestaurants(String restaurantId) {
        String query= """
                SELECT id,name,price,date_added,meal_type,avatar_url,user_id,menu_item_id,amount
                FROM meals
                LEFT JOIN
                A_LA_CARTE_meal_items
                ON id=meal_id
                WHERE user_id=:restaurantId
                ORDER by date_added
                """;
        var param =new MapSqlParameterSource().addValue(":restaurantId",restaurantId);
        return jdbcTemplate.query(query,param,mealExtractor());
    }

    @Override
    public List<Meal> getMeals() {
        return null;
    }

    private void batchInsertClientMealItems(String SQLStatement,Map<Integer,Integer> mealItems,int mealId) {
        var batchParams=mealItems.entrySet()
                .stream()
                .map((entry)-> {
                    return new MapSqlParameterSource()
                            .addValue("mealId",mealId)
                            .addValue("menuItem", entry.getKey())
                            .addValue("amount", entry.getValue());
                })
                .toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(SQLStatement,batchParams);
    }
    private ResultSetExtractor<List<Meal>> mealExtractor(){
        return (resultSet)->{

            var objectMap=new HashMap<Integer,Meal>();

            Meal meal=null;
            while (resultSet.next()){
                var id=resultSet.getInt("id");

                if(objectMap.get(id)==null){

                    meal=new Meal(
                            id,
                            resultSet.getString("name"),
                            resultSet.getDouble("price"),
                            resultSet.getTimestamp("date_added"),
                            MealType.valueOf(resultSet.getString("meal_type")),
                            resultSet.getString("avatar_url"),
                            resultSet.getString("user_id"),
                            null
                    );
                    objectMap.put(id,meal);
                };

                if (is_A_LA_CARTE(meal)) {
                    HashMap<Integer,Integer> mealItems;
                    if (meal.getMealItems() == null){
                        mealItems=new HashMap<>();
                    }else {
                        mealItems=new HashMap<>( meal.getMealItems());
                        meal.getMealItems().clear();
                    };
                    mealItems.put(resultSet.getInt("meal_id"),resultSet.getInt(""));
                    meal.setMealItems(mealItems);
                }
            }
            return new ArrayList<Meal>(objectMap.values());
        };
    }

    private boolean is_A_LA_CARTE(Meal meal){
        return meal.getMealType().equals(MealType.A_LA_CARTE);
    };
}
