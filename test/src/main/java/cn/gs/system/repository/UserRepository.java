package cn.gs.system.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.gs.base.IBaseRepository;
import cn.gs.system.model.User;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete
public interface UserRepository extends IBaseRepository<User> {
	
	/**
	 * 根据用户名和密码
	 * @param username
	 * @param password
	 * @return
	 */
	User getByUsernameAndPassword(String username, String password);
	
	/**
	 * 判断某个用户名是否存在
	 * @param username
	 * @return
	 */
	boolean existsByUsername(String username);
	
	/**
	 * 重置密码
	 * @param age1
	 * @param id
	 * @return
	 */
	@Modifying
    @Query("update User u set u.password = :password, u.chgPwdTime = :now where u.username = :username")
    int resetPassword(@Param("username")String username, @Param("password")String password, @Param("now")Timestamp now);
	
    /**
	 * 设置用户状态
	 * @param id
	 * @param flag
	 * @return
	 */
    @Modifying
	@Query("update User u set u.flag = :flag where u.id in :id ")
	int setFlag(@Param("flag")String flag, @Param("id")List<String> id);
	
    /**
     * 设置用户状态
     * @param id
     * @param flag
     * @return
     */
    @Modifying
    @Query("update User u set u.flag = :flag where u.id = :id ")
    int setFlag(@Param("flag")String flag, @Param("id")String id);
    
    /**
     * 根据关键字查询前10条记录
     * @param keyword
     * @return
     */
    public List<User> findTop10ByUsernameContaining(String username);
    
}