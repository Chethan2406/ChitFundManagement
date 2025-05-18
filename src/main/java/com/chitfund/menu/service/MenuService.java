package com.chitfund.menu.service;

import java.util.List;

import com.chitfund.menu.dto.MenuRequest;
import com.chitfund.menu.model.MenuInfo;



public interface MenuService {

    MenuInfo saveMenu(MenuRequest request);
    MenuInfo getMenuById(Long id);
    List<MenuInfo> getAllMenus();
    MenuInfo updateMenu(Long id, MenuRequest request);
    void deleteMenu(Long id);



}
