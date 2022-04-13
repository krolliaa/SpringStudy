package com.zwm.service.impl;

import com.zwm.exception.GoodsNotEnoughException;
import com.zwm.exception.GoodsNullPointerException;
import com.zwm.mapper.GoodsMapper;
import com.zwm.pojo.Goods;
import com.zwm.service.GoodsService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {

    private GoodsMapper goodsMapper;

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public List<Goods> selectAllGoods() {
        return goodsMapper.selectAllGoods();
    }

    @Override
    public Goods selectGoodsById(int id) {
        return goodsMapper.selectGoodsById(id);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, timeout = -1, rollbackFor = {GoodsNotEnoughException.class, GoodsNullPointerException.class})
    public Goods buy(int id, int amount) throws GoodsNullPointerException, GoodsNotEnoughException {
        Goods goods = this.selectGoodsById(id);
        if (goods == null) {
            throw new GoodsNullPointerException("无此商品");
        }
        //更改价格
        this.updateGoodsPriceById(id);
        if (goods.getAmount() < amount) {
            throw new GoodsNotEnoughException("商品数量不足，无法更改价格");
        }
        return goods;
    }

    @Override
    public int updateGoodsPriceById(int id) {
        int price = goodsMapper.selectGoodsById(id).getPrice() + 100;
        return goodsMapper.updateGoodsPriceById(id, price);
    }
}
