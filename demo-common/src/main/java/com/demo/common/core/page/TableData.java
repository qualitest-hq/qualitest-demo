package com.demo.common.core.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页数据（用于 R 响应体中的 data 字段）
 *
 * @author qualitest
 */
@Getter
@Setter
@NoArgsConstructor
public class TableData<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 列表数据
     */
    private List<T> rows;

    /**
     * 总记录数
     */
    private long total;

    public TableData(List<T> rows, long total) {
        this.rows = rows;
        this.total = total;
    }
}
