package com.wanguo.quick_batch.jpa;

import com.wanguo.quick_batch.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface CustomerJpa extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

    Customer getByOpenid(String openId);

}
