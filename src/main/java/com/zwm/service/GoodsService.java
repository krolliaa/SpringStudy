package com.zwm.service;

import com.zwm.execption.GoodsNotEnoughException;
import com.zwm.execption.GoodsNullPointerException;
import com.zwm.pojo.Goods;

import java.util.List;

public interface GoodsService {
    public abstract List<Goods> selectAllGoods();

    public abstract Goods selectGoodsById(int id);

    public abstract Goods buy(int id, int amount) throws GoodsNullPointerException, GoodsNotEnoughException;

    public abstract int updateGoodsPriceById(int id);
}
