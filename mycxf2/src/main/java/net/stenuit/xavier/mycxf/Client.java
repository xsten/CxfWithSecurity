package net.stenuit.xavier.mycxf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	
	public static void main(String[] args)
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("client-beans.xml");
		
		
		OrderProcess clientPort=(OrderProcess)context.getBean("orderClient");
		Order order=new Order();
		
		order.setCustomerID("cust001");
		order.setItemID("item003");
		order.setQty(1);
		order.setPrice(45.56);
		
		String orderId=clientPort.processOrder(order);
		
		if(orderId==null)
			System.out.println("Order refused");
		else
			System.out.println("Order accepted, id="+orderId);
		
	}
}
