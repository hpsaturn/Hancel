package org.hansel.myAlert;

import java.io.Serializable;
import java.util.List;

import org.linphone.Contact;

public class Ring implements Serializable{
	
	private static final long serialVersionUID = -2188411347965052790L;
	private String id;
	private String name;
<<<<<<< HEAD
	private long always;
=======
	private long notify;
>>>>>>> second_stage
	private List<Contact> contacs;
	
	
	public Ring(){
		id = null;
		name = null;
	}
<<<<<<< HEAD
	public Ring(String id, String name, long always){
		this.id = id;
		this.name = name;
		this.always = always;
=======
	public Ring(String id, String name, long notify){
		this.id = id;
		this.name = name;
		this.notify = notify;
>>>>>>> second_stage
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
<<<<<<< HEAD
	public long getAlways() {
		return always;
	}
	public void setAlways(long always) {
		this.always = always;
=======
	public long getNotify() {
		return notify;
	}
	public void setNotify(long notify) {
		this.notify = notify;
>>>>>>> second_stage
	}
	public List<Contact> getContacs() {
		return contacs;
	}
	public void setContacs(List<Contact> contacs) {
		this.contacs = contacs;
	}
}