package com.chitfund.menu.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chitfund.menu.model.MenuInfo;


public interface MenuRepository extends JpaRepository<MenuInfo, Long> {

	@Query(value = "CALL getMenusByUserId(:userid)", nativeQuery = true)
	List<Map<String, Object>> getMenusByUserId(@Param("userid") Long userid);

}
