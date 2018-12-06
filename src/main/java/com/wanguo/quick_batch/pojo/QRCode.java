package com.wanguo.quick_batch.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述：
 *
 * @author Badguy
 */
@Entity
@Data
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Integer id;

    private String scene;

    private String page;

    private String path;

    private Date createTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
