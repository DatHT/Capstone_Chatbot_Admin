/**
 *
 */
package com.psib.dto;

import com.psib.model.ProductAddress;

import java.util.List;

/**
 * @author DatHT
 *         Jun 5, 2016
 * @Email: datht0601@gmail.com
 */
public class ProductDto {

    private int current;

    private int rowCount;

    private List<ProductAddressDto> rows;

    private long total;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<ProductAddressDto> getRows() {
        return rows;
    }

    public void setRows(List<ProductAddressDto> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
