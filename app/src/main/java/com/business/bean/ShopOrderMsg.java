package com.business.bean;

import java.io.Serializable;

public class ShopOrderMsg implements Serializable{
	private String order;
	private String goods;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}
}


