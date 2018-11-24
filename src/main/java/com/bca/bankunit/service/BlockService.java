package com.bca.bankunit.service;


import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import com.bca.bankunit.model.Block;

@Service
public class BlockService {
	ResultSet rs;
	ConnectDB db = new ConnectDB();

	ArrayList<Block> alBlock = new ArrayList<Block>();
	
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public static byte[] applyECDSASig(PrivateKey privateKey, String input){
		Signature dsa;
		byte[] output = new byte[0];
	
		try {
			dsa = Signature.getInstance("ECDSA","BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}
	
}
