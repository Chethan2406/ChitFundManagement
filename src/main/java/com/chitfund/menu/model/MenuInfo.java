package com.chitfund.menu.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menuInfo")
public class MenuInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
	private long MenuId;
	@Column(name = "menu_name", unique = true, nullable = false)
	private String menuName;
	@Column(name = "menu_type")
	private String menuType;
	@Column(name = "menu_order")
	private String menuOrder;
	@Column(name = "menu_url")
	private String menuUrl;
	@Column(name = "menu_icon")
	private String menuIcon;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "menu_submenu")
	private List<MenuInfo> subMenu;
	@Column(name = "checked")
	private boolean checked;
	@Column(name = "indeterminate")
	private boolean indeterminate;
	@Column(name = "un_selectable")
	private boolean unselectable;
	@Column(name = "is_delete_flag")
	private int isDeleteFlag = 0;

}
