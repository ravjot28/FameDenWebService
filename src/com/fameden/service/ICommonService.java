package com.fameden.service;

import com.fameden.bean.FamedenRequestDetail;

public interface ICommonService {
	
	public boolean validate(Object obj) throws Exception;
	
	public Object processRequest(Object obj);
	
	public Object populateResponse(Object obj);
	
	public int insertRequest(FamedenRequestDetail famedenRequestDetail) throws Exception;
	
	public Object populateRequest(Object obj);

}
