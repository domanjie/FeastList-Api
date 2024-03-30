package FeastList.meal;

import lombok.*;

import java.sql.Timestamp;


@EqualsAndHashCode
@ToString
@Getter
@Setter
public abstract class  Meal  {
	private final int id;

	private final String name;

	private final String vendorId;

	private final  Timestamp  dateAdded;

	double price;
	public Meal(int id, String name, String vendorId,Timestamp dateAdded){
		this.id=id;
		this.name=name;
		this.vendorId=vendorId;
		this.dateAdded=dateAdded;
	}
	public abstract double getPrice();
	private void validateOwner(){

	}
}
