package com.chitfund.menu.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chitfund.menu.dto.MenuRequest;
import com.chitfund.menu.model.MenuInfo;
import com.chitfund.menu.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@Slf4j
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuInfoService;

    public MenuController(MenuService menuInfoService) {
        this.menuInfoService = menuInfoService;
    }

    /**
     * Saves a new menu to the system.
     * 
     * This method handles the POST request to create a new menu. It logs the
     * attempt,
     * saves the menu using the menuInfoService, and returns the saved menu
     * information.
     * If an error occurs during the process, it logs the error and returns an
     * internal server error.
     *
     * @param menu The MenuInfo object containing the details of the menu to be
     *             saved.
     * @return ResponseEntity<MenuInfo> A ResponseEntity containing the saved
     *         MenuInfo object if successful,
     *         or an error status if the operation fails.
     */
    @PostMapping
    public ResponseEntity<?> saveMenu(@Valid @RequestBody MenuRequest request, BindingResult result) {
        log.info("CFM_MNC_001 - Saving new MenuRequest: {}", request);

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            log.warn("CFM_MNC_001 - Validation errors: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }

        // Let MenuException propagate, don't catch it here
        MenuInfo savedMenu = menuInfoService.saveMenu(request);
        log.info("CFM_MNC_001 - Menu saved successfully with ID: {}", savedMenu.getMenuId());
        return ResponseEntity.ok(savedMenu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable Long id, @Valid @RequestBody MenuRequest request,
            BindingResult result) {
        log.info("CFM_MNC_002 - Updating MenuRequest: {}", request);

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            log.warn("CFM_MNC_002 - Validation errors: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }

        MenuInfo updatedMenu = menuInfoService.updateMenu(id, request);
        log.info("CFM_MNC_002 - Menu updated successfully with ID: {}", updatedMenu.getMenuId());
        return ResponseEntity.ok(updatedMenu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuInfoById(@PathVariable Long id) {
        log.info("CFM_MNC_003 - Fetching menu by ID: {}", id);

        MenuInfo menu = menuInfoService.getMenuById(id);
                if(menu!=null){
                    log.info("CFM_MNC_003 - Menu found: {}", menu);
                    return ResponseEntity.ok(menu);
                }
                else{
                    log.warn("CFM_MNC_003 - Menu ID not found: {}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu not found");
                }
    }

    @GetMapping
    public ResponseEntity<List<MenuInfo>> listMenu() {
        log.info("CFM_MNC_005 - Fetching all menus");
        try {
            List<MenuInfo> menus = menuInfoService.getAllMenus();
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            log.error("CFM_MNC_005 - Error fetching menu list: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuInfo(@PathVariable Long id) {
        log.info("CFM_MNC_004 - Deleting menu by ID: {}", id);

        menuInfoService.deleteMenu(id);
        log.info("CFM_MNC_004 - Menu deleted successfully for ID: {}", id);
        return ResponseEntity.noContent().build();
    }

}
