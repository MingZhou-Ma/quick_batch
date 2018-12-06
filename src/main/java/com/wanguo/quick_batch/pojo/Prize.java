package com.wanguo.quick_batch.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 描述：
 *
 * @author Badguy
 */
@Entity
@Data
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Integer id;

    private String name;

    private String pic;

    private Integer stock;

}
