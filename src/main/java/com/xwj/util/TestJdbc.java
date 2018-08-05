package com.xwj.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

import com.xwj.bean.User;

public class TestJdbc {

	private ApplicationContext ctx = null;
	private JdbcTemplate jdbcTemplate = null;

	{
		ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
	}

	/**
	 * ִ�� INSERT
	 */
	@Test
	public void testInsert() {
		String sql = "INSERT INTO xwj_user(id, last_name, age) VALUES(?, ?, ?)";
		jdbcTemplate.update(sql, "1", "a-xwj", 0);
	}

	/**
	 * ִ��UPDATE
	 */
	@Test
	public void testUpdate() {
		String sql = "UPDATE xwj_user SET last_name = ? WHERE id = ?";
		jdbcTemplate.update(sql, "b-xwj", 1);
	}

	/**
	 * ִ�� DELETE
	 */
	@Test
	public void testDelete() {
		String sql = "DELETE from xwj_user WHERE id = ?";
		jdbcTemplate.update(sql, 1);
	}

	/**
	 * �����������²��� ���һ�������� Object[] �� List ���ͣ���Ϊ�޸�һ����¼��Ҫһ�� Object ���飬�޸Ķ�����¼����Ҫһ��
	 * List ����Ŷ�����顣
	 */
	@Test
	public void testBatchUpdate() {
		String sql = "INSERT INTO xwj_user(id, last_name, email) VALUES(?, ?, ?)";

		List<Object[]> batchArgs = new ArrayList<>();
		batchArgs.add(new Object[] { "2", "AA", "aa@atguigu.com" });
		batchArgs.add(new Object[] { "3", "BB", "bb@atguigu.com" });
		batchArgs.add(new Object[] { "4", "CC", "cc@atguigu.com" });
		batchArgs.add(new Object[] { "5", "DD", "dd@atguigu.com" });

		jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	/**
	 * �����ݿ��л�ȡһ����¼��ʵ�ʵõ���Ӧ��һ������ ע�⣺���ǵ��� queryForObject(String sql,Class<Employee> requiredType, Object... args) ����! 
	 * ����Ҫ����queryForObject(String sql, RowMapper<Employee> rowMapper, Object... args)
	 * 1�����е� RowMapper ָ�����ȥӳ���������У����õ�ʵ����Ϊ BeanPropertyRowMapper 
	 * 2��ʹ��SQL�е��еı�����������������������ӳ�䣬���� last_name lastName 
	 * 3����֧�ּ������ԡ� JdbcTemplateֻ����Ϊһ�� JDBC ��С����, ������ ORM ���
	 */
	@Test
	public void testQueryForObject() {
		String sql = "SELECT id, last_name lastName, email FROM xwj_user WHERE ID = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		// �ڽ�����װ�����ʱ��Ҫ����set������
		User user = jdbcTemplate.queryForObject(sql, rowMapper, 2);
		System.out.println(user);
	}
	
	/** 
     * һ�β�ѯ������� 
     * ע�⣺���õĲ��� queryForList ���� 
     */  
    @Test  
    public void testQueryForList() {  
        String sql = "SELECT id, name, email FROM xwj_user WHERE id > ?";  
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);  
        List<User> userList = jdbcTemplate.query(sql, rowMapper, 1);  
        if (!CollectionUtils.isEmpty(userList)) {
        	userList.forEach(user -> {
        		System.out.println(user);
        	});
        }
    }  
    
    /** 
     * ��ȡ�����е�ֵ����ͳ�Ʋ�ѯ 
     * ʹ�� queryForObject(String sql, Class<Long> requiredType)  
     */  
    @Test  
    public void testQueryForCount() {  
        String sql = "SELECT count(id) FROM xwj_user";  
        long count = jdbcTemplate.queryForObject(sql, Long.class);  
          
        System.out.println(count);  
    }     

}
