package com.bw.movie.seat.bean;
/**
 *
 * @描述 订单的bean
 *
 * @创建日期 2019/2/14 14:52
 *
 */
public class IndentPayBean {

    /**
     * orderId : 20180807084055347
     * message : 下单成功
     * status : 0000
     */

    private String orderId;
    private String message;
    private String status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
