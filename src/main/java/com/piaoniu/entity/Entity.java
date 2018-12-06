package com.piaoniu.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = -1L;

    public static final int STATUS_VALID = 1;

    public static final int STATUS_INVALID = 0;

    public static final int STATUS_TO_AUDIT = 1;

    public static final int STATUS_AUDIT_PASS = 2;

    public static final int STATUS_AUDIT_REJECT = 3;

    public static final int STATUS_WATING_PUBLISH = 4;

    public int  id;
    Date addTime;
    Date updateTime;

    /**
     * 有副作用 注意
     * @return
     */
    protected Entity emptyTime() {
        this.setAddTime(null);
        this.setUpdateTime(null);
        return this;
    }

    public static <T extends Entity> void emptyTime(List<T> list) {
        list.forEach(entity -> entity.emptyTime());
    }
}
