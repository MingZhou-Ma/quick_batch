package com.wanguo.quick_batch.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 描述：抽奖机会记录
 *
 * @author Badguy
 */
@Entity
@Data
public class LotteryOpportunityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Integer id;

    private String boostInterval;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
