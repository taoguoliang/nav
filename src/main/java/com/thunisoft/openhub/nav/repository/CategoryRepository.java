package com.thunisoft.openhub.nav.repository;

import com.blinkfox.fenix.jpa.QueryFenix;
import com.thunisoft.openhub.nav.repository.pojo.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 网址链接的分类的持久层仓库接口.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    /**
     * 查询出最大的顺序号.
     *
     * @return 顺序
     */
    @Query("select max(c.order) from Category as c")
    Integer findMaxOrder();

    /**
     * 分页查询所有分类信息.
     *
     * @param name 分类名称
     * @param pageable 分页信息
     * @return 分类的分页查询结果
     */
    @QueryFenix
    Page<Category> findByPaging(String name, Pageable pageable);

    /**
     * 根据分类的所属页面查询所有分类信息序.
     *
     * @param page 分类所属页面
     * @return 分类的分页查询结果
     */
    @Query("select c from Category as c where c.page = :page")
    List<Category> findByPage(@Param("page") String page);

    /**
     * 查询出所有的一级分类集合信息.
     *
     * @return 一级分类集合
     */
    @Query("select c from Category as c where c.parentId is null order by c.order")
    List<Category> findFirstLevelCategories();

}
