package de.gravitex.banking.client.dto;

import java.math.BigDecimal;

public class BudgetPlanningItemDto {

	private String purposeCategory;
	
	private BigDecimal quantity;

	public String getPurposeCategory() {
		return purposeCategory;
	}

	public void setPurposeCategory(String purposeCategory) {
		this.purposeCategory = purposeCategory;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
}