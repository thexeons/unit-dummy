package com.Profile.controller;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.bouncycastle.crypto.RuntimeCryptoException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Profile.model.Block;
import com.Profile.model.Person;
import com.Profile.service.BlockService;
import com.Profile.service.ConnectDB;
import com.Profile.service.PersonService;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class MainController {	
	
	ResultSet rs;
	ConnectDB db = new ConnectDB();
	RestTemplate rt = new RestTemplate();
	
	//public String base64pubkey = "MFUwEwYHKoZIzj0CAQYIKoZIzj0DAQQDPgAEdRjqcQG0/6qisxiTnXW8XhZZwp3SsGXV1WXXEfxqAWAwBLgjOHX7/Sw0+5kKNACoZ0cwDVOf3NeJTkbW";
	public String base64pubkey = "MFUwEwYHKoZIzj0CAQYIKoZIzj0DAQQDPgAEG0VDhgZP9w7oarYdXcb0ViGEHultxRbqopU6UB7ZMyVMXciT31a8sjCX0gxWybPDDCTdLqBvfBnSORLn";
	public PublicKey publickey;
	
	ArrayList<Block> alBlock = new ArrayList<Block>();
	public static int difficulty = 2;
	
	@Autowired
	private PersonService mPersonService;
	private BlockService mBlockService;
	
	public MainController(PersonService mPersonService) {
		this.mPersonService = mPersonService;
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
		if(mBlock.getFirstname().equals("William"))
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
      byte[] aPublic = Base64.getDecoder().decode(base64pubkey.getBytes("UTF-8"));
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(aPublic);
      KeyFactory keyFactory = KeyFactory.getInstance("ECDSA" , "BC");
      publickey = keyFactory.generatePublic(keySpec);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		byte[] getSig = Base64.getDecoder().decode(mBlock.getSignature());
		if(BlockService.verifyECDSASig(publickey, mBlock.getData(), getSig))
		{
			return "True";
		}
		else
		{
			return "False";
		}
	}
	
	//php to unitservice to master
	@PostMapping("/acceptBlock")
	public Block phpBlock(@RequestBody Block bBlock) {
		RestTemplate restTemplate = new RestTemplate();
   	 String url = "http://192.168.43.219:8090/verifyBlock";
   	 HttpHeaders headers = new HttpHeaders();
   	 headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("firstname",bBlock.getFirstname());
            postdata.put("lastname",bBlock.getLastname());
            postdata.put("ktp",bBlock.getKtp());
            postdata.put("email",bBlock.getEmail());
            postdata.put("dob",bBlock.getDob());
            postdata.put("address",bBlock.getAddress());
            postdata.put("nationality",bBlock.getNationality());
            postdata.put("accountnum",bBlock.getAccountnum());
            postdata.put("photo",bBlock.getPhoto());
            postdata.put("verified", bBlock.getVerified());
            postdata.put("bcabank", bBlock.getBcabank());
            postdata.put("bcainsurance", bBlock.getBcainsurance());
            postdata.put("bcafinancial", bBlock.getBcafinancial());
            postdata.put("bcasyariah", bBlock.getBcasyariah());
            postdata.put("bcasekuritas", bBlock.getBcasekuritas());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        String requestJson = postdata.toString();
   	 HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
   	 String answer = restTemplate.postForObject(url, entity, String.class);
   	 System.out.println(answer);
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
