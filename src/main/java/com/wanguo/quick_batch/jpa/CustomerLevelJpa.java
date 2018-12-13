package com.wanguo.quick_batch.jpa;

import com.wanguo.quick_batch.pojo.CustomerLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface CustomerLevelJpa extends JpaRepository<CustomerLevel, Integer>, JpaSpecificationExecutor<CustomerLevel> {

}
