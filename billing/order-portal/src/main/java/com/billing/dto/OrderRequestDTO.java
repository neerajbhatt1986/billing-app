package com.billing.dto;

import com.sun.istack.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRequestDTO implements Serializable {

    private Integer id;
    @NotNull
    private Integer userId;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;



    List<OrderLineRequestDTO> orderLines = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<OrderLineRequestDTO> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineRequestDTO> orderLines) {
        this.orderLines = orderLines;
    }
}
