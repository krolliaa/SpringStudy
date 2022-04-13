package com.zwm.mapper;

import com.zwm.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    public abstract List<Goods> selectAllGoods();

    public abstract Goods selectGoodsById(int id);

    public abstract int updateGoodsPriceById(@Param("id") int id, @Param("price") int price);
}
