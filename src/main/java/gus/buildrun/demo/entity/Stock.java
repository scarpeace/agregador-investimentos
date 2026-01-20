package gus.buildrun.demo.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_stocks")
public class Stock {

    @Id
    @Column(name = "stock_id")
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "ticker")
    private String ticker;

    public Stock() {
    }

    public Stock(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
