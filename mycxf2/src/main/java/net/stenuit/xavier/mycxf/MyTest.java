package net.stenuit.xavier.mycxf;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class MyTest {
	public static void main(String[] args) throws Exception
	{
		FileInputStream fis=new FileInputStream("/home/xs/documents_xs/openssl/client.keystore");
		java.security.KeyStore ks=KeyStore.getInstance("JKS");
		
		ks.load(fis,"password".toCharArray());
		
		Certificate cert=ks.getCertificate("signature");
		
		Key k=ks.getKey("signature", "".toCharArray());
		
		if(k==null)
			System.out.println("error : not found");
		else
			System.out.println("found : "+cert);
		
	}
}
