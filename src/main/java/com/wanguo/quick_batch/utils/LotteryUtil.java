package com.wanguo.quick_batch.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
public class LotteryUtil {

    public static int drawGift(List<String> probList) {

        if (null != probList && !probList.isEmpty()) {
            List<Double> orgProbList = new ArrayList<>(probList.size());
            for (String lottery : probList) {
                //按顺序将概率添加到集合中
                orgProbList.add(Double.valueOf(lottery));
            }
            return draw(orgProbList);
        }
        return -1;
    }

    private static int draw(List<Double> lotteryProbList) {

        List<Double> sortRateList = new ArrayList<>();

        // 计算概率总和
        Double sumRate = 0D;
        for (Double prob : lotteryProbList) {
            sumRate += prob;
        }
        if (sumRate != 0) {
            double rate = 0D;   //概率所占比例
            for (Double prob : lotteryProbList) {
                rate += prob;
                //rate = prob;
                // 构建一个比例区段组成的集合(避免概率和不为1)
                //System.out.println("rate:" + rate);
                //System.out.println("rate / sumRate:" + rate / sumRate);
                sortRateList.add(rate / sumRate);
            }

            // 随机生成一个随机数，并排序
            double random = Math.random();
            sortRateList.add(random);
            Collections.sort(sortRateList);

            for (double a : sortRateList
            ) {
                System.out.println(a);
            }

            // 返回该随机数在比例集合中的索引
            return sortRateList.indexOf(random);
        }

        return -1;
    }

}
