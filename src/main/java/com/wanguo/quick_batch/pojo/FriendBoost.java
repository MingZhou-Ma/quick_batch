package com.wanguo.quick_batch.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述：好友助力
 *
 * @author Badguy
 */
@Entity
@Data
public class FriendBoost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Integer id;

    private Date createTime;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "activity_id")
//    private Activity activity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "initiator_id")
    private Customer initiator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booster_id")
    private Customer booster;
}
