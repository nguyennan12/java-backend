package com.javaweb.builder;

import java.util.ArrayList;
import java.util.List;

public class BuildingSearchBuilder {
	private String name;
	private Long floorArea;
	private Integer numberOfBasement;
	private String ward;
	private String street;
	private Long districtId;
	private List<String> typeCode = new ArrayList<>();
	private Long rentPriceTo;
	private Long rentPriceFrom;
	private Long rentAreaTo;
	private Long rentAreaFrom;
	private String serviceFee;
	private String brokerageFee;
	private String managerName;
	private String managerPhoneNumber;
	private Long staffId;
	
	
	private BuildingSearchBuilder(Builder builder) {
		this.name = builder.name;
		this.floorArea = builder.floorArea;
		this.numberOfBasement = builder.numberOfBasement;
		this.ward = builder.ward;
		this.street = builder.street;
		this.districtId = builder.districtId;
		this.typeCode = builder.typeCode;
		this.rentPriceTo = builder.rentPriceTo;
		this.rentPriceFrom = builder.rentPriceFrom;
		this.rentAreaTo = builder.rentAreaTo;
		this.rentAreaFrom = builder.rentAreaFrom;
		this.serviceFee = builder.serviceFee;
		this.brokerageFee = builder.brokerageFee;
		this.managerName = builder.managerName;
		this.managerPhoneNumber = builder.managerPhoneNumber;
		this.staffId = builder.staffId;
	}
	
	public String getName() {
		return name;
	}

	public Long getFloorArea() {
		return floorArea;
	}

	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}

	public String getWard() {
		return ward;
	}

	public String getStreet() {
		return street;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public List<String> getTypeCode() {
		return typeCode;
	}

	public Long getRentPriceTo() {
		return rentPriceTo;
	}

	public Long getRentPriceFrom() {
		return rentPriceFrom;
	}

	public Long getRentAreaTo() {
		return rentAreaTo;
	}

	public Long getRentAreaFrom() {
		return rentAreaFrom;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public String getBrokerageFee() {
		return brokerageFee;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}

	public Long getStaffId() {
		return staffId;
	}

	public static class Builder {
		private String name;
		private Long floorArea;
		private Integer numberOfBasement;
		private String ward;
		private String street;
		private Long districtId;
		private List<String> typeCode = new ArrayList<>();
		private Long rentPriceTo;
		private Long rentPriceFrom;
		private Long rentAreaTo;
		private Long rentAreaFrom;
		private String serviceFee;
		private String brokerageFee;
		private String managerName;
		private String managerPhoneNumber;
		private Long staffId;	
		
		public Builder setName(String name) {
	        this.name = name;
	        return this;
	    }

	    public Builder setFloorArea(Long floorArea) {
	        this.floorArea = floorArea;
	        return this;
	    }

	    public Builder setNumberOfBasement(Integer numberOfBasement) {
	        this.numberOfBasement = numberOfBasement;
	        return this;
	    }

	    public Builder setWard(String ward) {
	        this.ward = ward;
	        return this;
	    }

	    public Builder setStreet(String street) {
	        this.street = street;
	        return this;
	    }

	    public Builder setDistrictId(Long districtId) {
	        this.districtId = districtId;
	        return this;
	    }

	    public Builder setTypeCode(List<String> typeCode) {
	        this.typeCode = typeCode;
	        return this;
	    }

	    public Builder setRentPriceTo(Long rentPriceTo) {
	        this.rentPriceTo = rentPriceTo;
	        return this;
	    }

	    public Builder setRentPriceFrom(Long rentPriceFrom) {
	        this.rentPriceFrom = rentPriceFrom;
	        return this;
	    }

	    public Builder setRentAreaTo(Long rentAreaTo) {
	        this.rentAreaTo = rentAreaTo;
	        return this;
	    }

	    public Builder setRentAreaFrom(Long rentAreaFrom) {
	        this.rentAreaFrom = rentAreaFrom;
	        return this;
	    }

	    public Builder setServiceFee(String serviceFee) {
	        this.serviceFee = serviceFee;
	        return this;
	    }

	    public Builder setBrokerageFee(String brokerageFee) {
	        this.brokerageFee = brokerageFee;
	        return this;
	    }

	    public Builder setManagerName(String managerName) {
	        this.managerName = managerName;
	        return this;
	    }

	    public Builder setManagerPhoneNumber(String managerPhoneNumber) {
	        this.managerPhoneNumber = managerPhoneNumber;
	        return this;
	    }

	    public Builder setStaffId(Long staffId) {
	        this.staffId = staffId;
	        return this;
	    }
		public BuildingSearchBuilder build() {
			return new BuildingSearchBuilder(this);
		}
	}
}
