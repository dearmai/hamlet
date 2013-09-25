package kr.or.sencha.hamlet.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/***
 * 
 * @author 곽옥석
 * 
 *
 */
public class Dummy implements Serializable {
	private List<java.util.HashMap> entitys;
	private String totalCount;
	private boolean success = true;
	private String errTitle;
	private String errMsg;
	private String message;
	public Dummy() {
		// TODO Auto-generated constructor stub
	}
	public void setEntitys(List<java.util.HashMap> t){
		this.entitys = t;
	}
	public String getTotalCount(){
		return this.totalCount;
	}
	public List getEntitys(){
		return this.entitys;
	}
	public void setTotalCount(String t){
		this.totalCount = t;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrTitle() {
		return errTitle;
	}
	public void setErrTitle(String errTitle) {
		this.errTitle = errTitle;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
