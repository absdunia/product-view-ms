package com.wipro.productviewms.bean;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {
	
	
    private Integer id;
    private String productName;
    private String productCode;
    private String productDesc;
    private Date productAddedOn;

}
