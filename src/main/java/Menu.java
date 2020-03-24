import javax.persistence.*;

@Entity
@Table (name = "menu")
@NamedQueries({
        @NamedQuery(name = "menu.findAll", query = "SELECT m FROM Menu m"),
        @NamedQuery(name = "menu.discount", query = "SELECT m FROM Menu m WHERE m.discount > 0"),
        @NamedQuery(name = "menu.price", query = "SELECT m FROM Menu m WHERE m.price BETWEEN :from AND :to"),
        @NamedQuery(name = "menu.weight", query = "SELECT m FROM Menu m WHERE m.weight <= 1"),
})
public class Menu {
    @Id
    private String name;

    @Column (nullable = false)
    private double price;

    @Column (nullable = false)
    private double weight;

    @Column
    private double discount;

    public Menu(String name, double price, double weight, double discount) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public Menu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return  name +
                ", price='" + price + '\'' +
                ", weight='" + weight + '\'' +
                ", discount='" + discount +
                "'";
    }
}
