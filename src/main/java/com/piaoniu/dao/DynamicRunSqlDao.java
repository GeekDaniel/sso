package com.piaoniu.dao;

import com.piaoniu.annotations.DaoGen;
import org.apache.ibatis.annotations.Param;

/**
 * @author code4crafter@gmail.com
 *         Date: 17/2/6
 *         Time: 下午6:10
 */
@DaoGen
public interface DynamicRunSqlDao {

	int runDynamic(@Param("sql") String sql);
}
