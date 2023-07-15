package cn.wbomb.api.generate;

import cn.wbomb.api.generate.OrderGoods;
import cn.wbomb.api.generate.OrderGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    long countByExample(OrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    int deleteByExample(OrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    int insert(OrderGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    int insertSelective(OrderGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    List<OrderGoods> selectByExample(OrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    OrderGoods selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    int updateByExampleSelective(@Param("record") OrderGoods record, @Param("example") OrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    int updateByExample(@Param("record") OrderGoods record, @Param("example") OrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    int updateByPrimaryKeySelective(OrderGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_goods
     *
     * @mbg.generated Sat Jul 15 16:44:38 CST 2023
     */
    int updateByPrimaryKey(OrderGoods record);
}