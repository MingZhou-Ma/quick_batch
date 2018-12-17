package com.wanguo.quick_batch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 描述：
 *
 * @author Badguy
 */
@Entity
/*@Data*/
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Integer id;

    private String openid;

    @JsonIgnore
    private String sessionKey;

    private String nickname;

    //private String nicknameUtf8;

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

//    public String getNickname() {
//        //UTF-8解码后的字符
//        //return URLDecoder.decode(nickname, "utf-8");
//        return new String(Base64.decodeBase64(nickname), StandardCharsets.UTF_8);
//    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getNickname() {
        return new String(Base64.decodeBase64(nickname), StandardCharsets.UTF_8);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Integer getLotteryOpportunity() {
        return lotteryOpportunity;
    }

    public void setLotteryOpportunity(Integer lotteryOpportunity) {
        this.lotteryOpportunity = lotteryOpportunity;
    }

    public Integer getUsedLotteryOpportunity() {
        return usedLotteryOpportunity;
    }

    public void setUsedLotteryOpportunity(Integer usedLotteryOpportunity) {
        this.usedLotteryOpportunity = usedLotteryOpportunity;
    }

    public Boolean getWhetherWinning() {
        return whetherWinning;
    }

    public void setWhetherWinning(Boolean whetherWinning) {
        this.whetherWinning = whetherWinning;
    }

    public Boolean getWhetherAuthInfo() {
        return whetherAuthInfo;
    }

    public void setWhetherAuthInfo(Boolean whetherAuthInfo) {
        this.whetherAuthInfo = whetherAuthInfo;
    }

    public Boolean getWhetherFillDeliveryInfo() {
        return whetherFillDeliveryInfo;
    }

    public void setWhetherFillDeliveryInfo(Boolean whetherFillDeliveryInfo) {
        this.whetherFillDeliveryInfo = whetherFillDeliveryInfo;
    }
}
