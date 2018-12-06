package com.wanguo.quick_batch.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述：中奖记录
 *
 * @author Badguy
 */
@Entity
@Data
public class WinningRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Integer id;

    private String code;

    private Date createTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prize_id")
    private Prize prize;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
