package com.javaweb.repository.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "rent_areas")
public class RentAreaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "value")
	private Long value;
	
	@ManyToOne
	@JoinColumn(name = "building_id")
	private BuildingEntity building;
	
	
	public BuildingEntity getBuilding() {
		return building;
	}
	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	
}
