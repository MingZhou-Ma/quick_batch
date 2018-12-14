package com.wanguo.quick_batch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Integer id;

    private String openid;

    @JsonIgnore
    private String sessionKey;

    private String nickname;

    private String phone;

    private Date createTime;

    private String receiver;

    private String contactNumber;

    private String shippingAddress;

    private Integer lotteryOpportunity;

    private Integer usedLotteryOpportunity;

    private Boolean whetherWinning;

    private Boolean whetherAuthInfo;
}
