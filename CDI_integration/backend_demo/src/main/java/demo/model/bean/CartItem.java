package demo.model.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ian Y.T Tsai(zanyking)
 * 
 * This class provides a representation of a {@code CartItem}
 * 
 */
@Entity
@Table(name="cartitems")
public class CartItem {

	private int id;
	private Product product;
	private int amount;
	private Long userId;
	private Date createDate = new Date();

	public CartItem(){}
	/**
	 * 
	 * @param userId
	 * @param product
	 */
	public CartItem(Long userId, Product product) {
		super();
		this.product = product;
		this.userId = userId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	public Product getProduct() {
		return product;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getAmount() {
		return amount;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void add(int amount) {
		this.amount += amount;
	}
	public float getSubTotal() {
		return product.getPrice() * amount;
	}
}
