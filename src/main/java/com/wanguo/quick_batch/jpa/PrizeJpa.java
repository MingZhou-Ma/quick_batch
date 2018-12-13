package com.wanguo.quick_batch.jpa;

import com.wanguo.quick_batch.pojo.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface PrizeJpa extends JpaRepository<Prize, Integer>, JpaSpecificationExecutor<Prize> {

    Prize findPrizeById(Integer id);

}
