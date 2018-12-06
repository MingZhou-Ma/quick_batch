package com.wanguo.quick_batch.jpa;

import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.pojo.FriendBoost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface FriendBoostJpa extends JpaRepository<FriendBoost, Integer>, JpaSpecificationExecutor<FriendBoost> {

    FriendBoost findByInitiatorAndBooster(Customer initiator, Customer booster);

    List<FriendBoost> findAllByInitiator(Customer initiator);

}
