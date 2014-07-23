package net.stenuit.xavier.mycxf;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;

public class SimpleClient implements CallbackHandler {
	public static void main(String[] args)
	{
		ClientProxyFactoryBean factory=new ClientProxyFactoryBean();
		factory.setServiceClass(OrderProcess.class);
		factory.setAddress("http://localhost:8080/mycxf2/OrderProcess");
		

		OrderProcess client=(OrderProcess)factory.create();
		
		// xs - added security
		Endpoint cxfEndpoint=ClientProxy.getClient(client).getEndpoint();
		/*
		Map<String,Object> inProps=new HashMap<String, Object>();
		// TODO : configure inProps
		WSS4JInInterceptor wssIn=new WSS4JInInterceptor(inProps);
		cxfEndpoint.getInInterceptors().add(wssIn);
		*/
		Map<String,Object> outProps=new HashMap<String,Object>();
		
		/*// password-based security 
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, "xavier");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		
		// for hashed password use:
		//properties.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,SimpleClient.class.getName());
		
		WSS4JOutInterceptor wssOut=new WSS4JOutInterceptor(outProps);
		cxfEndpoint.getOutInterceptors().add(wssOut);
		*/
		
		
		// Signature-based security
		
		// test : client_sign.properties accessible ?
		//URL test=SimpleClient.class.getClassLoader().getResource("client_sign.properties");
		//System.out.println(test);
		
		outProps.put(WSHandlerConstants.ACTION, "Signature");
		outProps.put(WSHandlerConstants.USER, "signature"); // "signature" is the alias name of the key in the keystore
		outProps.put(WSHandlerConstants.SIG_KEY_ID, "DirectReference"); // copy the whole certificate in the soap header
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, SimpleClient.class.getName());
		outProps.put(WSHandlerConstants.SIG_PROP_FILE, "client_sign.properties");
		WSS4JOutInterceptor wssOut=new WSS4JOutInterceptor(outProps);
		cxfEndpoint.getOutInterceptors().add(wssOut);
		
		// xs - end
		
		Order order=new Order();
		order.setCustomerID("mlkj");
		order.setItemID("2212");
		order.setPrice(12.34d);
		order.setQty(1);
		String result=client.processOrder(order);
		
		System.out.println("result="+result);
	}
	
    public void handle(Callback[] callbacks) throws IOException,UnsupportedCallbackException {

	    WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
	
	    // set the password for user (in case of user/password authentication)
	    // set the password for the key in the keystore (in case of signature)
	    pc.setPassword("");
    }
}
