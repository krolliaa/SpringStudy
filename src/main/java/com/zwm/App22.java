package com.zwm;

import com.zwm.execption.GoodsNotEnoughException;
import com.zwm.execption.GoodsNullPointerException;
import com.zwm.pojo.Goods;
import com.zwm.service.GoodsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App22 {
    public static void main(String[] args) throws GoodsNotEnoughException, GoodsNullPointerException {
        String springConfig = "applicationContext20.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        GoodsService goodsService = (GoodsService) applicationContext.getBean("goodsService");
        List<Goods> goodsList = goodsService.selectAllGoods();
        for (Goods goods : goodsList) System.out.println(goods.toString());
        goodsService.buy(1001, 0);
    }
}
