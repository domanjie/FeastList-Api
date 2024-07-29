package FeastList.meal.repository;

import FeastList.infra.JpaRepositoryExtension;
import FeastList.meal.domain.Meal;
import FeastList.meal.domain.PreMadeMeal;
import FeastList.meal.dto.Out.PreMadeMealProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface JpaMealRepository extends CrudRepository<Meal, UUID>, JpaRepositoryExtension<Meal> {

    @Query(value = """
            SELECT  m.id, m.vendor_name,p.meal_name, p.avatar_url, p.price,IF(client_id =:client, TRUE,FALSE) as is_in_cart
            FROM meals as m
            INNER JOIN
            pre_made_meal as p
            ON m.id=p.id
            LEFT JOIN
            tray t
            ON 	p.id =t.meal_id
            AND t.client_id=:client
            """ ,nativeQuery = true)
    List<PreMadeMealProjection> findAllPreMadeMeal(@Param("client") String client);


//    @Query(value = """
//
//
//            """)
//    List<Meal> findAllByIdOrderByIdList(Set<UUID> uuids);
}
