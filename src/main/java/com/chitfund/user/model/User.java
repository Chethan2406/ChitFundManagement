package com.chitfund.user.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.chitfund.menu.model.MenuInfo;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class User implements Serializable {

	private static final long serialVersionUID = -8754742146921136107L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;

	@Column(name = "name")
	private String name;

	@Column(name = "phone_no", unique = true)
	private String phoneNo;

	@Column(name = "password")
	private String password;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "address")
	private String address;

	@Column(name = "status")
	private int status;

	@OneToOne
	@JoinColumn(name = "role_id")
	private Role roleId;

	@Column(name = "delete_flag")
	private Boolean deleteFlag;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_menu", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private List<MenuInfo> menuInfo;

}
