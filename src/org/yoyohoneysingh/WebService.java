package org.yoyohoneysingh;

@javax.jws.WebService
public class WebService {
	
	public Response test(Request e){
		Response r = new Response();
		r.setResponse(e.getResponse());
		return r;
	}

}
