package com.bca.bankunit.controller;


import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bca.bankunit.model.Block;
import com.bca.bankunit.service.BlockService;
import com.bca.bankunit.service.ConnectDB;

@Component
@RestController
public class MainController {	
	
	ResultSet rs;
	ConnectDB db = new ConnectDB();
	RestTemplate rt = new RestTemplate();
	
	//public String base64pubkey = "MFUwEwYHKoZIzj0CAQYIKoZIzj0DAQQDPgAEdRjqcQG0/6qisxiTnXW8XhZZwp3SsGXV1WXXEfxqAWAwBLgjOHX7/Sw0+5kKNACoZ0cwDVOf3NeJTkbW";
	public String base64pubkey1 = "MFUwEwYHKoZIzj0CAQYIKoZIzj0DAQQDPgAEdRjqcQG0/6qisxiTnXW8XhZZwp3SsGXV1WXXEfxqAWAwBLgjOHX7/Sw0+5kKNACoZ0cwDVOf3NeJTkbW";
	public PublicKey publickey1;
	
	public String base64pubkey2 = "MFUwEwYHKoZIzj0CAQYIKoZIzj0DAQQDPgAEG0VDhgZP9w7oarYdXcb0ViGEHultxRbqopU6UB7ZMyVMXciT31a8sjCX0gxWybPDDCTdLqBvfBnSORLn";
	public PublicKey publickey2;
	
	public String base64pubkey3 = "MFUwEwYHKoZIzj0CAQYIKoZIzj0DAQQDPgAELONymd22w1O1L/enAq2qoAVSlSYkMbeOHsuOHVfeF6r37noiu0cJwrOeJMqsidL32xPgbMcqPISZelLS";
	public PublicKey publickey3;
	
	public String base64pubkey4 = "MFUwEwYHKoZIzj0CAQYIKoZIzj0DAQQDPgAEY+/I1Sm9pAmmvV8i2PrraBAMqCJlBdKkzI+sKZtxb+1spsCYB8x3vLL/15NmiU39B0ipge7EuVUlQ5u4";
	public PublicKey publickey4;
	
	public String base64pubkey5 = "MFUwEwYHKoZIzj0CAQYIKoZIzj0DAQQDPgAEV2wErQmFG1Wf+LtSlyvjBFXsqQ7hnkOMvoyMIYPQTAKanF6Ze/iSw/igOjQL/A2qAaceAi8gqQjor3qS";
	public PublicKey publickey5;
	
	
	public String base64privateKey1 = "MIGNAgEAMBMGByqGSM49AgEGCCqGSM49AwEEBHMwcQIBAQQeD5os95M4dUv/w/X3iNs1Q+MQnRn6ARetYPbHsdOWoAoGCCqGSM49AwEEoUADPgAEdRjqcQG0/6qisxiTnXW8XhZZwp3SsGXV1WXXEfxqAWAwBLgjOHX7/Sw0+5kKNACoZ0cwDVOf3NeJTkbW";
	public PrivateKey privatekey1;

	
	ArrayList<Block> alBlock = new ArrayList<Block>();
	public static int difficulty = 2;
	
	@Autowired
	private BlockService mBlockService;

	
	public MainController(BlockService mBlockService) {
		this.mBlockService = mBlockService;
	}
	
	
	
	//master to unitdb
	@PostMapping("/bankBlock")
	public Block bankBlock(@RequestBody Block mBlock) {
		System.out.println(mBlock.getFirstname());
		alBlock = new ArrayList<Block>();
		
		try {
			db.openDB();
			db.executeUpdate("INSERT INTO msdata (ktp, firstname, lastname, email, dob, address, nationality, accountnum, photo, verified) VALUES ('"+mBlock.getKtp()+"','"+mBlock.getFirstname()+"','"+mBlock.getLastname()+"','"+mBlock.getEmail()+"','"+mBlock.getDob()+"','"+mBlock.getAddress()+"','"+mBlock.getNationality()+"','"+mBlock.getAccountnum()+"','"+mBlock.getPhoto()+"','0')");
			db.closeDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mBlock;
	}
	
	@PostMapping("/getTest")
	public String returnBlock(@RequestBody Block mBlock) {
		//if(mBlock.getFirstname().equals("William"))
		System.out.println("First name: " + mBlock);
		if(("William").equals(mBlock.getFirstname()))
		{
		return "True";
		}
		else 
		{
		return "False";
		}
	}
	
	@PostMapping("/returnResponse")
	public String returnResponse(@RequestBody Block mBlock) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		try {
			//Key 1
			byte[] aPublic1 = Base64.getDecoder().decode(base64pubkey1.getBytes("UTF-8"));
      		X509EncodedKeySpec keySpec1 = new X509EncodedKeySpec(aPublic1);
      		KeyFactory keyFactory1 = KeyFactory.getInstance("ECDSA" , "BC");
      		publickey1 = keyFactory1.generatePublic(keySpec1);
      		

			//Key 1
			byte[] aPublic2 = Base64.getDecoder().decode(base64pubkey2.getBytes("UTF-8"));
      		X509EncodedKeySpec keySpec2 = new X509EncodedKeySpec(aPublic2);
      		KeyFactory keyFactory2 = KeyFactory.getInstance("ECDSA" , "BC");
      		publickey2 = keyFactory2.generatePublic(keySpec2);
      		

			//Key 1
			byte[] aPublic3 = Base64.getDecoder().decode(base64pubkey3.getBytes("UTF-8"));
      		X509EncodedKeySpec keySpec3 = new X509EncodedKeySpec(aPublic3);
      		KeyFactory keyFactory3 = KeyFactory.getInstance("ECDSA" , "BC");
      		publickey3 = keyFactory3.generatePublic(keySpec3);
      		

			//Key 1
			byte[] aPublic4 = Base64.getDecoder().decode(base64pubkey4.getBytes("UTF-8"));
      		X509EncodedKeySpec keySpec4 = new X509EncodedKeySpec(aPublic4);
      		KeyFactory keyFactory4 = KeyFactory.getInstance("ECDSA" , "BC");
      		publickey4 = keyFactory4.generatePublic(keySpec4);
      		

			//Key 1
			byte[] aPublic5 = Base64.getDecoder().decode(base64pubkey5.getBytes("UTF-8"));
      		X509EncodedKeySpec keySpec5 = new X509EncodedKeySpec(aPublic5);
      		KeyFactory keyFactory5 = KeyFactory.getInstance("ECDSA" , "BC");
      		publickey5 = keyFactory5.generatePublic(keySpec5);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
			
			int CounterTrue = 0;
			byte[] getSig = Base64.getDecoder().decode(mBlock.getSignature());
			if(BlockService.verifyECDSASig(publickey1, mBlock.getData(), getSig))
			{CounterTrue++;}
			if(BlockService.verifyECDSASig(publickey2, mBlock.getData(), getSig))
			{CounterTrue++;}
			if(BlockService.verifyECDSASig(publickey3, mBlock.getData(), getSig))
			{CounterTrue++;}
			if(BlockService.verifyECDSASig(publickey4, mBlock.getData(), getSig))
			{CounterTrue++;}
			if(BlockService.verifyECDSASig(publickey5, mBlock.getData(), getSig))
			{CounterTrue++;}
			
			if(CounterTrue>=1) {
				return "True";
			}
			else {
				return "False";
			}
	}
	
	//php to unitservice to master
	@PostMapping("/acceptBlock")
	public Block phpBlock(@RequestBody Block bBlock) {
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		int counterTrue = 0;
		
		String needVerify = bBlock.getFirstname()+bBlock.getLastname()+bBlock.getKtp()+bBlock.getEmail()+bBlock.getDob()+bBlock.getAddress()+bBlock.getNationality()+bBlock.getAccountnum()+bBlock.getPhoto()+bBlock.getVerified()+bBlock.getBcabank()+bBlock.getBcainsurance()+bBlock.getBcafinancial()+bBlock.getBcasyariah()+bBlock.getBcasekuritas();
		RestTemplate restTemplate = new RestTemplate();
		//From Here
        //Set as Unit 2 Key IP
		//Copy Paste for number of unit
		String url = "http://192.168.43.171:8090/returnResponse";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject postdata = new JSONObject();
        //Converting String to Private Key
        
        try {
	        byte[] aPrivate = Base64.getDecoder().decode(base64privateKey1.getBytes("UTF-8"));			
			PKCS8EncodedKeySpec keySpecx = new PKCS8EncodedKeySpec(aPrivate);
			KeyFactory keyFactory = KeyFactory.getInstance("ECDSA" , "BC");
			privatekey1 = keyFactory.generatePrivate(keySpecx);
        }
        catch(Exception e) {
        	throw new RuntimeException(e);
        }
		//Converting signature Byte to String
		byte[] byteSig = BlockService.applyECDSASig(privatekey1, needVerify);
		String encoded = Base64.getEncoder().encodeToString(byteSig);
		
		try {
            postdata.put("signature",encoded);
            postdata.put("data",needVerify);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        String requestJson = postdata.toString();
        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String answer = restTemplate.postForObject(url, entity, String.class);
        
        if(answer.equals("True")){
        	counterTrue++;
        }
        //To Here
		
        //set >2 from 4
        if(counterTrue>=1) {
        	System.out.println("Berhasil");
        	RestTemplate restTemplatex = new RestTemplate();
       	 	String urlx = "http://localhost:8095/newBlock";
       	 	HttpHeaders headersx = new HttpHeaders();
       	 	headersx.setContentType(MediaType.APPLICATION_JSON);
            JSONObject postdatax = new JSONObject();
            try {
                postdatax.put("firstname",bBlock.getFirstname());
                postdatax.put("lastname",bBlock.getLastname());
                postdatax.put("ktp",bBlock.getKtp());
                postdatax.put("email",bBlock.getEmail());
                postdatax.put("dob",bBlock.getDob());
                postdatax.put("address",bBlock.getAddress());
                postdatax.put("nationality",bBlock.getNationality());
                postdatax.put("accountnum",bBlock.getAccountnum());
                postdatax.put("photo",bBlock.getPhoto());
                postdatax.put("verified", bBlock.getVerified());
                postdatax.put("bcabank", bBlock.getBcabank());
                postdatax.put("bcainsurance", bBlock.getBcainsurance());
                postdatax.put("bcafinancial", bBlock.getBcafinancial());
                postdatax.put("bcasyariah", bBlock.getBcasyariah());
                postdatax.put("bcasekuritas", bBlock.getBcasekuritas());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            String requestJsonx = postdatax.toString();
	        HttpEntity<String> entityx = new HttpEntity<String>(requestJsonx,headersx);
	        String answerx = restTemplatex.postForObject(urlx, entityx, String.class);
	        System.out.println(answerx);
        }
		
   	 	if(answer.equals("False"))
   	 	{
   	 		try {
   	 			db.openDB();
				db.executeUpdate("UPDATE msdata SET verified = '0' WHERE ktp = '"+bBlock.getKtp()+"'");
				db.closeDB();
   	 		} catch (Exception e) {
   	 			// TODO Auto-generated catch block
   	 			e.printStackTrace();
   	 	}
   	 }
   	 
		return bBlock;
	}
	
	//Haha
	public java.util.List<Block> getBlock() {
//		try {
//			db.openDB();
//			rs= db.executeQuery("select firstname from msdata");
//			alBlock = new ArrayList<Block>();
//			while(rs.next()) {
//				if(alBlock.isEmpty()) {
//					alBlock.add(new Block(rs.getString(1),"0"));
//					alBlock.get(alBlock.size()-1).mineBlock(difficulty);
//					Thread.sleep(100);	
//				}
//				else {
//					alBlock.add(new Block(rs.getString(1),alBlock.get(alBlock.size()-1).getHash()));
//					alBlock.get(alBlock.size()-1).mineBlock(difficulty);
//					Thread.sleep(100);
//				}
//			}
//			db.closeDB();
//		} catch (Exception	 e) {
//			e.printStackTrace();
//		}
//		System.out.println(alBlock.get(0));
		
		alBlock = new ArrayList<Block>();
		try {
			db.openDB();
			rs=db.executeQuery("select * from msdata join mshash on msdata.id = mshash.id");
			while(rs.next()) {
				alBlock.add(new Block(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(21),rs.getString(20), Integer.parseInt(rs.getString(13)),rs.getString(14),rs.getString(15),rs.getString(16), rs.getString(17),rs.getString(18)));
			}
			db.closeDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return alBlock;
	}
}
