package com.newstar.dao;

import com.newstar.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 案例：根据客户名称查询客户
     * jpql:from Customer where custName = ?
     */
    @Query(value = "from Customer where custName = ?")
    public Customer findJpql(String custName);

    /**
     * 案例：根据客户名称和Id查询客户
     * jpql: from Customer where custName = ? and custId = ?
     * 默认情况下占位符的位置需要与方法参数中的位置保持一致
     * 也可以在占位符？后指定参数位置
     */
    @Query(value = "from Customer where custName = ?2 and custId = ?1")
    public Customer getCustomerByNameAndId(Long id, String name);

    /**
     * 案例：更新根据id更新客户名称
     * sql:update cst_customer set cust_name = ? where cust_id = ?
     * jpql: update Customer set custName = ? where custId = ?
     * @Query：代表进行查询
     * @Modifying：代表更新操作
     */
    @Query(value = "update Customer set custName = ?2 where custId = ?1")
    @Modifying
    public void updateCustomer(Long id, String name);

    /**
     * 案例：查询所有客户（sql）
     * sql:select * from cst_customer
     * nativeQuery:是否使用本地查询
     *      false（默认）：使用jpql
     *      true：使用sql
     */
    @Query(value = "select * from cst_customer", nativeQuery = true)
    public List<Object[]> findSql();

    /**
     * 案例：模糊查询（sql）
     */
    @Query(value = "select * from cst_customer where cust_name like ?1", nativeQuery = true)
    public List<Object[]> findLikeSql(String name);

    /**
     * 方法命名的约定查询
     * 精准查询
     * findBy+属性名称（首字母大写）
     */
    public Customer findByCustName(String name);

    /**
     * 方法名命名的约定查询
     * 模糊查询
     * findBy+属性名称（首字母大写）+查询方式（like）
     */
    public List<Customer> findByCustNameLike(String name);

    /**
     * 方法名命名的约定查询
     * 多条件查询
     * findBy+属性名称（首字母大写）+查询方式（like，如果是精准查询不需要写）+连接符（and|or）+属性名称（首字母大写）+查询方式（like，如果是精准查询不需要写）
     * 注意：参数的顺序不能调换
     */
    public Customer findByCustNameLikeAndCustIndustry(String name, String industry);
}
