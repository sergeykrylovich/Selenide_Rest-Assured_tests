package db.entities;


import lombok.*;

import javax.persistence.*;

@Entity(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //само генерирует айди
    private Integer Id;
    @Version
    private Integer version;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;

}
