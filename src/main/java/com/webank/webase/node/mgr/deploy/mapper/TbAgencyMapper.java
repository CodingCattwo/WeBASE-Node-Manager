package com.webank.webase.node.mgr.deploy.mapper;

import com.webank.webase.node.mgr.deploy.entity.TbAgency;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface TbAgencyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_agency
     *
     * @mbg.generated
     */
    @Delete({
        "delete from tb_agency",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_agency
     *
     * @mbg.generated
     */
    @InsertProvider(type=TbAgencySqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(TbAgency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_agency
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, agency_name, desc, chain_id, chain_name, create_time, modify_time",
        "from tb_agency",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="agency_name", property="agencyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="chain_id", property="chainId", jdbcType=JdbcType.INTEGER),
        @Result(column="chain_name", property="chainName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TbAgency selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_agency
     *
     * @mbg.generated
     */
    @UpdateProvider(type=TbAgencySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TbAgency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_agency
     *
     * @mbg.generated
     */
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn = "id")
    @Insert({
    "<script>",
        "insert into tb_agency (agency_name, ",
        "desc, chain_id, ",
        "chain_name, create_time, ",
        "modify_time)",
        "values<foreach collection=\"list\" item=\"detail\" index=\"index\" separator=\",\">(#{detail.agencyName,jdbcType=VARCHAR}, ",
        "#{detail.desc,jdbcType=VARCHAR}, #{detail.chainId,jdbcType=INTEGER}, ",
        "#{detail.chainName,jdbcType=VARCHAR}, #{detail.createTime,jdbcType=TIMESTAMP}, ",
        "#{detail.modifyTime,jdbcType=TIMESTAMP})</foreach></script>",
    })
    int batchInsert(java.util.List<TbAgency> list);
}