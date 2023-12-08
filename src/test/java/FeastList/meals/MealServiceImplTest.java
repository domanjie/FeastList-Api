package FeastList.meals;

import FeastList.security.AuthenticationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealServiceImplTest {

    @Mock
    private MealRepository mealRepositoryMock;
    @Mock
    private AuthenticationService authenticationServiceMock;
    @Mock
    private Set<MealItem> mealItemsMock;
    @InjectMocks
    private  MealServiceImpl mealServiceImpl;

    @Test
    void  mealShouldBeSaved(){
        var meal=Meal.builder()
                .mealItems(mealItemsMock)
                .mealType(MealType.PRE_MADE)
                .price(100.00)
                .avatarUrl("shallipopiurl")
                .name("lizzy special cake")
                .build();
        var meal2=Meal.builder()
                .mealItems(mealItemsMock)
                .mealType(MealType.PRE_MADE)
                .price(100.00)
                .userId("lizzy@gmail.com")
                .avatarUrl("shallipopiurl")
                .name("lizzy special cake")
                .build();
        when(authenticationServiceMock.getAuthenticatedUserId()).thenReturn("lizzy@gmail.com");
        mealServiceImpl.saveMeal(meal);
        verify(mealRepositoryMock).save(meal2);
    };
    @Test
    void mealShouldBeRetrievedById(){
        var meal=Meal.builder()
                .mealItems(mealItemsMock)
                .mealType(MealType.PRE_MADE)
                .price(100.00)
                .avatarUrl("shallipopiurl")
                .name("lizzy special cake")
                .build();
        var id=1L;
        when(mealRepositoryMock.getMealById(id)).thenReturn(Optional.of(meal));
        mealServiceImpl.getMealById(1L);
        verify(mealRepositoryMock).getMealById(id);
    };
    @Test
    void mealShouldBeDeletedWithIdProvided(){
        mealServiceImpl.deleteMeal(1L);
        verify(mealRepositoryMock).deleteMealById(1L);
    }

    @Test
    void mealListForTheVendorIdProvidedShouldBeReturned(){
        mealServiceImpl.getMealsByRestaurant("lizzy@gmail.com");
        verify(mealRepositoryMock).getMealByRestaurants("lizzy@gmail.com");
    }
    @Test
    void mealListForAParticularClientShouldBeReturned(){
        mealServiceImpl.getMeals();
        verify(mealRepositoryMock).getMeals();
    }
}