package net.stenuit.xavier.mycxf;

import java.util.Random;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;

@WebService
@Features(features = "org.apache.cxf.feature.LoggingFeature") // sx : added logging
public class OrderProcessImpl implements OrderProcess {
	static Random r=new Random();
	
	public String processOrder(Order order) {
		String orderID=validate(order);
		
		return orderID;
	}

	private String validate(Order order) {
		String custID = order.getCustomerID();
		String itemID = order.getItemID();
		int qty = order.getQty();
		double price = order.getPrice();
		if (custID != null && itemID != null && !custID.equals("")
		&& !itemID.equals("") && qty > 0
		&& price > 0.0) {
			
			return "ORD"+r.nextInt(10000);
		}
		return null;
	}

}
