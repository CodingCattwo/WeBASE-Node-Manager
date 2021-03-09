package com.webank.webase.node.mgr.external.mapper;

import com.webank.webase.node.mgr.external.entity.TbExternalAccount;
import com.webank.webase.node.mgr.user.entity.UserParam;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface TbExternalAccountMapper {

    @SelectProvider(type = TbExternalAccountSqlProvider.class, method = "getList")
    List<TbExternalAccount> listExtAccount(UserParam param);

    @SelectProvider(type = TbExternalAccountSqlProvider.class, method = "count")
    int countExtAccount(UserParam param);

    @Select({ "select count(1)", "from tb_external_account", "where group_id = #{groupId} and address = #{address}" })
    int countOfExtAccount(@Param("groupId") Integer groupId, @Param("address") String address);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_external_account
     *
     * @mbg.generated
     */
    @Delete({ "delete from tb_external_account", "where id = #{id,jdbcType=INTEGER}" })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_external_account
     *
     * @mbg.generated
     */
    @InsertProvider(type = TbExternalAccountSqlProvider.class, method = "insertSelective")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertSelective(TbExternalAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_external_account
     *
     * @mbg.generated
     */
    @Select({ "select", "id, group_id, public_key, address, sign_user_id, has_pk, user_name, user_status, ", "create_time, modify_time, description, app_id", "from tb_external_account", "where id = #{id,jdbcType=INTEGER}" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true), @Result(column = "group_id", property = "groupId", jdbcType = JdbcType.INTEGER), @Result(column = "public_key", property = "publicKey", jdbcType = JdbcType.VARCHAR), @Result(column = "address", property = "address", jdbcType = JdbcType.VARCHAR), @Result(column = "sign_user_id", property = "signUserId", jdbcType = JdbcType.VARCHAR), @Result(column = "has_pk", property = "hasPk", jdbcType = JdbcType.INTEGER), @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR), @Result(column = "user_status", property = "userStatus", jdbcType = JdbcType.INTEGER), @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP), @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP), @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR), @Result(column = "app_id", property = "appId", jdbcType = JdbcType.VARCHAR) })
    TbExternalAccount selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_external_account
     *
     * @mbg.generated
     */
    @UpdateProvider(type = TbExternalAccountSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TbExternalAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_external_account
     *
     * @mbg.generated
     */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert({ "<script>", "insert into tb_external_account (group_id, ", "public_key, address, ", "sign_user_id, has_pk, ", "user_name, user_status, ", "create_time, modify_time, ", "description, app_id)", "values<foreach collection=\"list\" item=\"detail\" index=\"index\" separator=\",\">(#{detail.groupId,jdbcType=INTEGER}, ", "#{detail.publicKey,jdbcType=VARCHAR}, #{detail.address,jdbcType=VARCHAR}, ", "#{detail.signUserId,jdbcType=VARCHAR}, #{detail.hasPk,jdbcType=INTEGER}, ", "#{detail.userName,jdbcType=VARCHAR}, #{detail.userStatus,jdbcType=INTEGER}, ", "#{detail.createTime,jdbcType=TIMESTAMP}, #{detail.modifyTime,jdbcType=TIMESTAMP}, ", "#{detail.description,jdbcType=VARCHAR}, #{detail.appId,jdbcType=VARCHAR})</foreach></script>" })
    int batchInsert(java.util.List<TbExternalAccount> list);
}
