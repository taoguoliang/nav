package com.thunisoft.openhub.nav.repository;

import com.blinkfox.fenix.jpa.QueryFenix;
import com.thunisoft.openhub.nav.bean.LinkQueryParam;
import com.thunisoft.openhub.nav.repository.pojo.Link;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 网址链接的持久层仓库接口.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Repository
public interface LinkRepository extends JpaRepository<Link, String> {

    /**
     * 查询出最大的顺序号.
     *
     * @return 顺序
     */
    @Query("select max(a.order) from Link as a")
    Integer findMaxOrder();

    /**
     * 分页查询所有网址信息.
     *
     * @param linkParam 网址的查询参数实体
     * @param pageable 分页信息
     * @return 网址的分页查询结果
     */
    @QueryFenix
    Page<Link> findByPaging(@Param("linkParam") LinkQueryParam linkParam, Pageable pageable);

    /**
     * 根据分类信息的 ID 集合查询这些分类下的所有网址信息集合.
     *
     * @param categoryIds 分类信息的 ID 集合
     * @return 某些分类下的所有网址信息
     */
    @Query("select a from Link as a where a.categoryId in :categoryIds")
    List<Link> findByCategoryIds(@Param("categoryIds") List<String> categoryIds);

}
