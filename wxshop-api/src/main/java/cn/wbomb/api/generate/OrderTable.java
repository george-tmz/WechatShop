package cn.wbomb.api.generate;

import java.io.Serializable;
import java.util.Date;

public class OrderTable implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.user_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.total_price
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private Long totalPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.address
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.express_company
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private String expressCompany;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.express_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private String expressId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.status
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.created_at
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private Date createdAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.updated_at
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private Date updatedAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_table.shop_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private Long shopId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table order_table
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.id
     *
     * @return the value of order_table.id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.id
     *
     * @param id the value for order_table.id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.user_id
     *
     * @return the value of order_table.user_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.user_id
     *
     * @param userId the value for order_table.user_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.total_price
     *
     * @return the value of order_table.total_price
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public Long getTotalPrice() {
        return totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.total_price
     *
     * @param totalPrice the value for order_table.total_price
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.address
     *
     * @return the value of order_table.address
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.address
     *
     * @param address the value for order_table.address
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.express_company
     *
     * @return the value of order_table.express_company
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public String getExpressCompany() {
        return expressCompany;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.express_company
     *
     * @param expressCompany the value for order_table.express_company
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.express_id
     *
     * @return the value of order_table.express_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public String getExpressId() {
        return expressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.express_id
     *
     * @param expressId the value for order_table.express_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setExpressId(String expressId) {
        this.expressId = expressId == null ? null : expressId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.status
     *
     * @return the value of order_table.status
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.status
     *
     * @param status the value for order_table.status
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.created_at
     *
     * @return the value of order_table.created_at
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.created_at
     *
     * @param createdAt the value for order_table.created_at
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.updated_at
     *
     * @return the value of order_table.updated_at
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.updated_at
     *
     * @param updatedAt the value for order_table.updated_at
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_table.shop_id
     *
     * @return the value of order_table.shop_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_table.shop_id
     *
     * @param shopId the value for order_table.shop_id
     *
     * @mbg.generated Sun Jul 16 00:40:21 CST 2023
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}