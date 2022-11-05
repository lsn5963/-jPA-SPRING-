package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int qunantity){    //재고 증가 //데이터를 가지고 있는 쪽에 비즈니스메서드가 있는게 좋다 그래야 응집력이 좋다.
        this.stockQuantity += qunantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if (restStock<0){
            throw new NotEnoughStockException("need more Stock");
        }
        this.stockQuantity = restStock;
    }
}
