//package FeastList.meal;
//import FeastList.meals.domain.*;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class MealRepositoryJdbcImpl implements MealRepository {
//    private final NamedParameterJdbcTemplate jdbcTemplate;
//
//    public MealRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate){
//        this.jdbcTemplate=jdbcTemplate;
//    }
//
//    @Override
//    public int save(Meal meal) {
//        if(meal instanceof PreMadeMealService)
//            return save((PreMadeMealService) meal);
//        else
//            return  save((CustomMeal) meal);
//    }
//    private int save(PreMadeMealService meal) {
//        var query= """
//                INSERT INTO meals(name,price,user_id,meal_type,avatar_url)
//                VALUES (:name,:price,:userId,:mealType,:avatarUrl)
//                """;
//        var params=new MapSqlParameterSource()
//                .addValue("name",meal.getName())
//                .addValue("price",meal.getPrice())
//                .addValue("userId",meal.getVendorId())
//                .addValue("mealType",meal.getMealType().toString())
//                .addValue("avatarUrl",meal.getAvatarUrl());
//        var keyHolder =new GeneratedKeyHolder();
//        jdbcTemplate.update(query,params,keyHolder);
//        var mealId= Objects.requireNonNull(keyHolder.getKey()).intValue();
//        return mealId;
//    }
//
//    private int save(CustomMeal meal) {
//        var query= """
//                INSERT INTO meals(name,price,user_id,meal_type)
//                VALUES (:name,:price,:userId,:mealType)
//                """;
//        var params=new MapSqlParameterSource()
//                .addValue("name",meal.getName())
//                .addValue("price",meal.getPrice())
//                .addValue("userId",meal.getVendorId())
//                .addValue("mealType",meal.getMealType().toString());
//        var query2= """
//                    INSERT INTO A_LA_CARTE_meal_items(meal_id, menu_item_id,amount)
//                    VALUES (:mealId,:menuItem,:amount)
//                    """;
//        var keyHolder =new GeneratedKeyHolder();
//        jdbcTemplate.update(query,params,keyHolder);
//        var mealId= Objects.requireNonNull(keyHolder.getKey()).intValue();
//        batchInsertClientMealItems(query2,meal.getMealItems(),mealId );
//        return mealId;
//    }
//
//    @Override
//    public Optional<Meal> getMealById(Long mealId) {
//        String query= """
//                SELECT a.id,a.name,a.price,a.avatar_url,
//                a.user_id,a.meal_type,a.date_added,
//                b.amount,
//                c.id AS menu_item_id ,c.name AS menu_item_name,c.price_per_portion AS menu_item_ppp,c.vendor_id,
//                c.avatar_url AS menu_item_avatar_url ,c.date_added AS menu_item_date_added
//                FROM meals AS a
//                LEFT JOIN
//                A_LA_CARTE_meal_items AS b
//                ON id=meal_id
//                LEFT JOIN menu_items AS c
//                ON b.menu_item_id=c.id
//                WHERE a.id=:mealId
//                """;
//        var param =new MapSqlParameterSource().addValue("mealId",mealId);
//        var mealCollection= jdbcTemplate.query(query,param,mealExtractor());
//        return Optional.of(mealCollection.get(0));
//    }
//
//    @Override
//    public Meal deleteMealById(Long mealId) {
//        return null;
//    }
//
//    @Override
//    public List<Meal> getMealByRestaurants(String restaurantId) {
//        String query= """
//                SELECT a.id,a.name,a.price,a.avatar_url,
//                a.user_id,a.meal_type,a.date_added,
//                b.amount,
//                c.id AS menu_item_id ,c.name AS menu_item_name,c.price_per_portion AS menu_item_ppp,c.vendor_id,
//                c.avatar_url AS menu_item_avatar_url ,c.date_added AS menu_item_date_added
//                FROM meals AS a
//                LEFT JOIN
//                A_LA_CARTE_meal_items AS b
//                ON id=meal_id
//                LEFT JOIN menu_items AS c
//                ON b.menu_item_id=c.id
//                WHERE user_id=:restaurantId
//                ORDER by a.date_added
//                """;
//        var param =new MapSqlParameterSource().addValue(":restaurantId",restaurantId);
//        return jdbcTemplate.query(query,param,mealExtractor());
//    }
//
//    @Override
//    public List<Meal> getMeals() {
//        return null;
//    }
//
//    private void batchInsertClientMealItems(String SQLStatement, List<MealItem> mealItems, int mealId) {
//        var batchParams=mealItems
//
//
//
//                .stream()
//                .map((entry)-> {
//                    return new MapSqlParameterSource()
//                            .addValue("mealId",mealId)
//                            .addValue("menuItem", entry.menuItem().getId())
//                            .addValue("amount", entry.noOfPortion());
//                })
//                .toArray(MapSqlParameterSource[]::new);
//
//        jdbcTemplate.batchUpdate(SQLStatement,batchParams);
//    }
//    private ResultSetExtractor<List<Meal>> mealExtractor(){
//        return (resultSet)->{
//            var objectMap=new HashMap<Integer,Meal>();
//            Meal meal=null;
//            while (resultSet.next()){
//                var id=resultSet.getInt("id");
//                if(objectMap.get(id)==null){
//
//                    var mealType= MealType.valueOf(resultSet.getString("meal_type"));
//
//                    if(mealType.equals(MealType.PRE_MADE))
//                        meal =buildPreMadeMeal(resultSet);
//                    else
//                        meal=buildALaCarteMeal(resultSet);
//
//                    objectMap.put(id,meal);
//                };
//
//                if (!isPreMade(Objects.requireNonNull(meal))) {
//
//                    var mealItem=buildMealITem(resultSet);
//                    ((CustomMeal)meal).getMealItems().add(mealItem);
//                }
//            }
//            return new ArrayList<Meal>(objectMap.values());
//        };
//    }
//
//    private boolean isPreMade(Meal meal){
//        return meal.getMealType().equals(MealType.PRE_MADE);
//    };
//    private MealItem buildMealITem(ResultSet resultSet) throws SQLException {
//        return new MealItem(
//                new MenuItem(
//                        resultSet.getInt("menu_item_id"),
//                        resultSet.getString("menu_item_name"),
//                        resultSet.getDouble("menu_item_ppp"),
//                        resultSet.getString("vendorId"),
//                        resultSet.getTimestamp("menu_item_date_added"),
//                        resultSet.getString("menu_item_avatar_url")
//                ),
//                resultSet.getInt("amount")
//        );
//    }
//    private CustomMeal buildALaCarteMeal(ResultSet resultSet) throws SQLException {
////        return new CustomMeal(
////                resultSet.getInt("id"),
////                resultSet.getString("name"),
////                resultSet.getString("user_id"),
////                resultSet.getTimestamp("date_added"),
////                new ArrayList<MealItem>()
////        );
//        return null;
//    }
//    private PreMadeMealService buildPreMadeMeal(ResultSet resultSet) throws SQLException {
//        return new PreMadeMealService(
//                resultSet.getInt("id"),
//                resultSet.getString("name"),
//                resultSet.getString("user_id"),
//                resultSet.getTimestamp("date_added"),
//                resultSet.getString("avatar_url"),
//                resultSet.getDouble("price")
//        );
//    }
//}
