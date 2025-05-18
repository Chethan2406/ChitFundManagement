package com.chitfund.menu.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.chitfund.menu.model.MenuInfo;
import com.chitfund.menu.model.MenuRequest;
import com.chitfund.menu.repository.MenuRepository;
import com.chitfund.util.MenuException;
import com.chitfund.util.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final ObjectMapper objectMapper;

	public MenuServiceImpl(MenuRepository menuRepository, ObjectMapper objectMapper) {
		this.menuRepository = menuRepository;
		this.objectMapper = objectMapper;
	}

	public MenuInfo saveMenu(MenuRequest request) {
		try {
			MenuInfo menu = objectMapper.convertValue(request, MenuInfo.class);
			menu = menuRepository.save(menu);
			return menu;
		} catch (DataIntegrityViolationException e) {
			log.error("CFM_MSI_001 - Data integrity violation while saving menu", e);

			String rootCause = Optional.ofNullable(e.getRootCause())
					.map(Throwable::getMessage)
					.orElse("");

			if (rootCause.contains("uk_") || rootCause.contains("Duplicate")) {
				throw new UserException("A menu with the same menuNmae already exists.");
			}

			throw new UserException("Unable to save menu due to data integrity violation.");
		} catch (Exception e) {
			log.error("CFM_MSI_001 - Error saving menu", e);
			throw new MenuException("Unable to save menu", e);
		}
	}

	public MenuInfo updateMenu(MenuInfo menu) {
		try {
			return menuRepository.save(menu);
		} catch (Exception e) {
			log.error("CFM_MSI_002 - Error updating menu", e);
			throw new MenuException("Unable to update menu", e);
		}
	}

	public MenuInfo getMenuById(Long menuInfoId) {
		try {
			return menuRepository.findById(menuInfoId)
					.orElseThrow(() -> new MenuException("Menu not found with id: " + menuInfoId, null));
		} catch (Exception e) {
			log.error("CFM_MSI_003 - Error fetching menu by ID", e);
			throw new MenuException("Unable to fetch menu with id: " + menuInfoId, e);
		}
	}

	public void deleteMenu(Long menuInfoId) {
		try {
			MenuInfo menu = getMenuById(menuInfoId);
			menu.setIsDeleteFlag(1);
			menuRepository.save(menu);
		} catch (Exception e) {
			log.error("CFM_MSI_004 - Error deleting menu", e);
			throw new MenuException("Unable to delete menu with id: " + menuInfoId, e);
		}
	}

	public List<MenuInfo> getMenuInfoList() {
		try {
			List<MenuInfo> res = menuRepository.findAll();
			if (res.isEmpty()) {
				throw new MenuException("No menus found", null);
			}
			return res;
		} catch (Exception e) {
			log.error("CFM_MSI_005 - Error fetching menu list", e);
			throw new MenuException("Unable to fetch menu list", e);
		}
	}

	public List<Map<String, Object>> getMenuInfoByUserId(Long userId) {
		try {
			return menuRepository.getMenusByUserId(userId);
		} catch (Exception e) {
			log.error("CFM_MSI_006 - Error fetching menus by user ID", e);
			throw new MenuException("Unable to fetch menus for user ID: " + userId, e);
		}
	}

	@Override
	public List<MenuInfo> getAllMenus() {
		try {
			return menuRepository.findAll();
		} catch (Exception e) {
			log.error("CFM_MSI_007 - Error fetching all menus", e);
			throw new MenuException("Unable to fetch all menus", e);
		}
	}

	@Override
	public MenuInfo updateMenu(Long id, MenuRequest request) {
		try {
			MenuInfo existingMenu = getMenuById(id);
			objectMapper.updateValue(existingMenu, request);
			return menuRepository.save(existingMenu);
		} catch (Exception e) {
			log.error("CFM_MSI_008 - Error updating menu with request", e);
			throw new MenuException("Unable to update menu with id: " + id, e);
		}
	}
}
