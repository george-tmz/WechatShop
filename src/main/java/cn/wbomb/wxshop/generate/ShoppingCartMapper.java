package cn.wbomb.wxshop.generate;

import cn.wbomb.wxshop.generate.ShoppingCart;
import cn.wbomb.wxshop.generate.ShoppingCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShoppingCartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    long countByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    int deleteByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    int insert(ShoppingCart row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    int insertSelective(ShoppingCart row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    List<ShoppingCart> selectByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    ShoppingCart selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    int updateByExampleSelective(@Param("row") ShoppingCart row, @Param("example") ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    int updateByExample(@Param("row") ShoppingCart row, @Param("example") ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    int updateByPrimaryKeySelective(ShoppingCart row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated Sat Jul 08 23:39:58 CST 2023
     */
    int updateByPrimaryKey(ShoppingCart row);
}