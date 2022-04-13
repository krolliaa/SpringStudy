package com.zwm;

import com.zwm.exception.GoodsNotEnoughException;
import com.zwm.exception.GoodsNullPointerException;
import com.zwm.pojo.Goods;
import com.zwm.service.GoodsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App23 {
    public static void main(String[] args) throws GoodsNotEnoughException, GoodsNullPointerException {
        String springConfig = "applicationContext21.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        GoodsService goodsService = (GoodsService) applicationContext.getBean("goodsService");
        List<Goods> goodsList = goodsService.selectAllGoods();
        for (Goods goods : goodsList) System.out.println(goods.toString());
        goodsService.buy(1001, 3);
    }
}
