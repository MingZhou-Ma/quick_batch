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
public class CustomerLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Integer id;

    private String phone;

    private String level;

}
