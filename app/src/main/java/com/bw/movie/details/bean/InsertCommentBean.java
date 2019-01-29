package com.bw.movie.details.bean;

/**
 *
 * @描述 添加评论的bean
 *
 * @创建日期 2019/1/29 8:44
 *
 */
public class InsertCommentBean {

    /**
     * message : 评论成功
     * status : 0000
     */

    private String message;
    private String status;

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
