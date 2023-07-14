package cn.wbomb.api.generate;

import cn.wbomb.api.generate.OrderTable;
import cn.wbomb.api.generate.OrderTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderTableMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    long countByExample(OrderTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    int deleteByExample(OrderTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    int insert(OrderTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    int insertSelective(OrderTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    List<OrderTable> selectByExample(OrderTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    OrderTable selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    int updateByExampleSelective(@Param("record") OrderTable record, @Param("example") OrderTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    int updateByExample(@Param("record") OrderTable record, @Param("example") OrderTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    int updateByPrimaryKeySelective(OrderTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_table
     *
     * @mbg.generated Sat Jul 15 00:47:38 CST 2023
     */
    int updateByPrimaryKey(OrderTable record);
}