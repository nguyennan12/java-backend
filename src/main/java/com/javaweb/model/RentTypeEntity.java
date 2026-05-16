package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name ="rent_types")
public class RentTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="type")
	private String type;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="building_rent_types",
			   joinColumns = @JoinColumn(name="rent_type_id", nullable = false),
			   inverseJoinColumns = @JoinColumn(name = "building_id", nullable = false))
	private List<BuildingEntity> buildings = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BuildingEntity> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<BuildingEntity> buildings) {
		this.buildings = buildings;
	}
	
	
}
