package FeastList.menuItem;

import org.flywaydb.core.Flyway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

@JdbcTest
@ContextConfiguration(classes = MenuItemRepositoryJdbcImpl.class)
class MenuItemRepositoryJdbcImplTest {
    @Autowired
    private  Flyway flyway;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void init(){
    }

//arrange act asset
    @Test
    void saveMenuItem() {

        MenuItem menuItem = MenuItem.builder()
                .name("eba")
                .pricePerPortion(100)
                .avatarUrl("http://eba")
                .vendorId("lucyomaz@gmail.com")
                .build();
        menuItemRepository.saveMenuItem(menuItem);
    }

    @Test
    void getMenuItemById() {
    }

    @Test
    void getByVendor() {
    }

    @Test
    void updateMenuItem() {
    }

    @Test
    void deleteMenuItem() {
    }
}