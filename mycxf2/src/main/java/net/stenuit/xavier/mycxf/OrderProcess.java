package net.stenuit.xavier.mycxf;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface="MyEndpointInterface")
public interface OrderProcess {
	@WebMethod
	String processOrder(Order order);
	
}
