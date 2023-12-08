package FeastList.menuItem;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Repository
public class MenuItemRepositoryJdbcImpl implements  MenuItemRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public MenuItemRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public int saveMenuItem(MenuItem menuItem) {
        var query= """
                INSERT INTO  menu_items(name,price_per_portion,vendor_id,avatar_url)
                VALUES (:name,:pricePerPortion,:vendorId,:avatarUrl)
                """;
        var keyHolder=new GeneratedKeyHolder();

        var params= new MapSqlParameterSource()
                .addValue("name",menuItem.getName())
                .addValue("pricePerPortion",menuItem.getPricePerPortion())
                .addValue("vendorId",menuItem.getVendorId())
                .addValue("avatarUrl",menuItem.getAvatarUrl());
        jdbcTemplate.update(query,params,keyHolder);
        return  Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public Optional<MenuItem> getMenuItemById(int itemId) {
        var query= """
                SELECT * FROM menu_items
                WHERE id=:itemId
                """;
        var param=new MapSqlParameterSource().addValue("itemId",itemId);
        return Optional.of(jdbcTemplate.queryForObject(query,param,rowMapper()));
    }

    @Override
    public List<MenuItem> getByVendor(String vendorId) {
        var query= """
                SELECT * FROM menu_items
                WHERE vendor_id=:vendorId
                ORDER BY date_added DESC
                LIMIT 10;
                """;
        var params=new MapSqlParameterSource()
                .addValue("vendorId",vendorId);
        return jdbcTemplate.query(query,params,rowMapper());
    }

    @Override
    public void updateMenuItem(MenuItem menuItem, long id) {
        var query= """
                UPDATE  menu_items
                SET name=:name,
                price_per_portion=:price,
                vendor_id=:vendorId,
                avatar_url=:avatarUrl
                WHERE id=:id
                """;
        var params=new MapSqlParameterSource()
                .addValue("name",menuItem.getId())
                .addValue("price",menuItem.getPricePerPortion())
                .addValue("vendorId",menuItem.getVendorId())
                .addValue("avatarUrl",menuItem.getAvatarUrl())
                .addValue("id",id);
        jdbcTemplate.update(query,params);

    }

    @Override
    public void deleteMenuItem(long itemId) {
        var query= """
                DELETE FROM menu_items
                WHERE id=:itemId
                """;
        var param=new MapSqlParameterSource()
                .addValue("id",itemId);
        jdbcTemplate.update(query,param);
    }

    private RowMapper<MenuItem> rowMapper(){
        return (rs, rowNum) ->new MenuItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getString("vendor_id"),
                rs.getTimestamp("date_added"),
                rs.getString("avatar_url")
            );
    }
}
