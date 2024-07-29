package FeastList.meal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.sql.Timestamp;
import java.util.UUID;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "meals")
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
public abstract class  Meal  {
	@Id
	@Column(name = "id" ,columnDefinition = "UUID")
	@JdbcType(VarcharJdbcType.class)
	private final UUID id;

	@Column(name = "vendor_name")
	private final String vendorName;

	@JsonIgnore
	@Column(name= "date_added")
	private final  Timestamp  dateAdded;

	public abstract double getPrice();
	public abstract boolean isAvailable();

}
