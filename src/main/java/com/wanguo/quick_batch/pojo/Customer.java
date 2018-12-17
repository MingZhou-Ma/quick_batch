package com.wanguo.quick_batch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    private String nicknameUtf8;

    private String avatar;

    private String phone;

    private Date createTime;

    private String receiver;

    private String contactNumber;

    private String shippingAddress;

    private Integer lotteryOpportunity;

    private Integer usedLotteryOpportunity;

    private Boolean whetherWinning;

    private Boolean whetherAuthInfo;

    private Boolean whetherFillDeliveryInfo;

    /*public String getNicknameUtf8() {
        //UTF-8解码后的字符
        //return URLDecoder.decode(nickname, "utf-8");
        return new String(Base64.decodeBase64(nickname), StandardCharsets.UTF_8);
    }*/

}
