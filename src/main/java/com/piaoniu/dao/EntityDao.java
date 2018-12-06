package com.piaoniu.dao;

import com.piaoniu.entity.Entity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: daniel
 * Date: 2018-11-30
 * Time: 上午1:30
 */
public interface EntityDao<T extends Entity> {
    int insert(T var1);

    T findById(int var1);

    int update(T var1);

    List<T> queryInIds(@Param("ids") Collection<Integer> var1);

    List<T> queryAll(RowBounds var1);

    int batchInsert(List<T> var1);
}
