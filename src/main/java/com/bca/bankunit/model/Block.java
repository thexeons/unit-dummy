package com.bca.bankunit.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;

@Entity
public class Block {
	public String hash;
	public String previousHash;
	@Id
	private String id;
	private String firstname;
	private String lastname;
	private String ktp;
	private String email;
	private String dob;
	private String address;
	private String nationality;
	private String accountnum;
	private String photo;
	private String verified;
	private long timeStamp;
	private int nonce;
	
	private String bcabank;
	private String bcainsurance;
	private String bcasyariah;
	private String bcafinancial;
	private String bcasekuritas;
	
	private String signature;
	private String data;
	//Constructor.
	
	public static String master1 ="0";
	public static String master2 ="0";
	public static String master3 ="0";
	public static String master4 ="0";
	public static String master5 ="0";
	
	public static String instance1 = "0";
	public static String instance2 = "0";
	public static String instance3 = "0";
	public static String instance4 = "0";

	public Block() {
		// TODO Auto-generated constructor stub
	}

	@JsonCreator
	public Block(String id,String firstname, String lastname, String ktp, String email, String dob, String address, String nationality, String accountnum,String photo,String verified, String previousHash,String bcabank,String bcainsurance,String bcasyariah,String bcafinancial,String bcasekuritas){
		
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.ktp = ktp;
		this.email = email;
		this.dob = dob;
		this.address = address;
		this.nationality = nationality;
		this.accountnum = accountnum;
		this.photo = photo;
		this.verified = verified;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
		this.bcabank = bcabank;
		this.bcainsurance = bcainsurance;
		this.bcasyariah = bcasyariah;
		this.bcafinancial = bcafinancial;
		this.bcasekuritas = bcasekuritas;
	}
	
	public Block(String id,String firstname, String lastname, String ktp, String email, String dob, String address, String nationality, String accountnum,String photo,String verified, String previousHash,String hash,int nonce,String bcabank,String bcainsurance,String bcasyariah,String bcafinancial,String bcasekuritas){
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.ktp = ktp;
		this.email = email;
		this.dob = dob;
		this.address = address;
		this.nationality = nationality;
		this.accountnum = accountnum;
		this.photo = photo;
		this.verified = verified;
		this.previousHash = previousHash;
		this.timeStamp= new Date().getTime();
		this.hash = hash;
		this.nonce=nonce;
		this.bcabank = bcabank;
		this.bcainsurance = bcainsurance;
		this.bcasyariah = bcasyariah;
		this.bcafinancial = bcafinancial;
		this.bcasekuritas = bcasekuritas;
	}
	
	public Block(String firstname, String lastname, String dob, String address, String email, String ktp,String nationality, String photo, String accountnum) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.address = address;
		this.email= email;
		this.accountnum = accountnum;
		this.ktp = ktp;
		this.photo = photo;
		this.nationality = nationality;
	}
	
	
	public Block(String id) {
		this.id = id;
	}
	
	public Block(String signature, String data) {
		this.signature = signature;
		this.data = data;
	}
	
	public String calculateHash(){
		//String calculatedhash = StringUtil.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + firstname+lastname+ktp+email+dob+address+nationality+accountnum);
		String calculatedhash = StringUtil.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + firstname+lastname+ktp+email+dob+address+nationality+accountnum);
		
		return calculatedhash;
	}
	
	public String[] mineBlock(int difficulty){
		String target = new String(new char[difficulty]).replace('\0' , '0');
		while(!hash.substring(0 , difficulty).equals(target)){
			nonce++;
			hash = calculateHash();
		}
		String[] arrayx = {"",""};
		arrayx[0]=hash+"";
		arrayx[1]=nonce+"";
		System.out.println("Successfully mined block " + hash);
		return arrayx;
	}

	public String getBcabank() {
		return bcabank;
	}

	public void setBcabank(String bcabank) {
		this.bcabank = bcabank;
	}

	public String getBcainsurance() {
		return bcainsurance;
	}

	public void setBcainsurance(String bcainsurance) {
		this.bcainsurance = bcainsurance;
	}

	public String getBcasyariah() {
		return bcasyariah;
	}

	public void setBcasyariah(String bcasyariah) {
		this.bcasyariah = bcasyariah;
	}

	public String getBcafinancial() {
		return bcafinancial;
	}

	public void setBcafinancial(String bcafinancial) {
		this.bcafinancial = bcafinancial;
	}

	public String getBcasekuritas() {
		return bcasekuritas;
	}

	public void setBcasekuritas(String bcasekuritas) {
		this.bcasekuritas = bcasekuritas;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getKtp() {
		return ktp;
	}

	public void setKtp(String ktp) {
		this.ktp = ktp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAccountnum() {
		return accountnum;
	}

	public void setAccountnum(String accountnum) {
		this.accountnum = accountnum;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Block [hash=" + hash + ", previousHash=" + previousHash + ", id=" + id + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", ktp=" + ktp + ", email=" + email + ", dob=" + dob + ", address="
				+ address + ", nationality=" + nationality + ", accountnum=" + accountnum + ", photo=" + photo
				+ ", verified=" + verified + ", timeStamp=" + timeStamp + ", nonce=" + nonce + "]";
	}	
}
