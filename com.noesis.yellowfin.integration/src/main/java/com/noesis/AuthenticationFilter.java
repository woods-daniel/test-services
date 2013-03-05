package com.noesis;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.hof.mi.web.service.AdministrationClientOrg;
import com.hof.mi.web.service.AdministrationPerson;
import com.hof.mi.web.service.AdministrationServiceRequest;
import com.hof.mi.web.service.AdministrationServiceResponse;
import com.hof.mi.web.service.AdministrationServiceService;
import com.hof.mi.web.service.AdministrationServiceServiceLocator;
import com.hof.util.Const;
import com.hof.data.SessionBean;

public class AuthenticationFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	private static String SchedulerEndPoint = "";
	private static String WebServiceUserName = "";
	private static String WebServicePassword = "";
	private static String WebServer;
	private static int WebPort;
	private static String endpoint = "/services/AdministrationService";
	   
	@Override
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
    	String companyLogoUrl = "/default/images/dummy-co.png";
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        HttpSession session = request.getSession();
        SessionBean tn_sb = null;
        
        AuthenticationFilter.setYellowfinServerInfo(request);

        if (null != session) {
        	tn_sb = (SessionBean) session.getAttribute(Const.SESSION_BEAN);
        }

        if((null == tn_sb || !tn_sb.isLoggedOn()) && request.getRequestURI().contains("logon"))
        {
        	String userName = request.getParameter("email");
            String password = request.getParameter("password");
            
            if (isValid(getEndPoint(SchedulerEndPoint), userName, password)) {
            	Logger.getRootLogger().info("Authenticated. Looking up Yellowfin user.");
            	
            	String clientOrg = AuthenticationFilter.getUserClientOrg(userName);         	
    			String token = getYellowfinToken(userName, clientOrg);
    			String lowerClient = clientOrg.toLowerCase();
    			companyLogoUrl = lowerClient.equals("") 
    								? "/default/images/dummy-co.png" 
    								: "/" + lowerClient + "/images/dummy-co.png";
    			
    			Logger.getRootLogger().info("Company Url: " + companyLogoUrl);
    			
    			session.setAttribute("companyLogoUrl", companyLogoUrl);
    			    			
    			if (token == null) {
    				Logger.getRootLogger().error("Token was null ... could not find user");
    			}
    			
    			response.sendRedirect("/logon.i4?LoginWebserviceId=" + token);
    			return;
    		}
        }
                
        session.setAttribute("companyLogoUrl", companyLogoUrl);
        chain.doFilter(req, res);
    }

    public void init(FilterConfig arg0) throws ServletException {
    	AuthenticationFilter.SchedulerEndPoint = arg0.getInitParameter("scheduler");
    	AuthenticationFilter.WebServiceUserName = "giovanni@noesis.com";
    	AuthenticationFilter.WebServicePassword = "Brazos78759!";
    }
    
    private static void setYellowfinServerInfo(HttpServletRequest request) {
    	String url =  request.getRequestURL().toString();
        url = url.substring(0, AuthenticationFilter.nthIndexOf(url, "/", 3));
        
        if (AuthenticationFilter.nthIndexOf(url, ":", 2) != -1) {
        	String port = url.substring(AuthenticationFilter.nthIndexOf(url, ":", 2) + 1, url.length());
        	AuthenticationFilter.WebPort = Integer.parseInt(port);
        } else {
        	AuthenticationFilter.WebPort = 80;
        }
        
        String urlOnly = url.substring(0, AuthenticationFilter.nthIndexOf(url, ":", 2));
        AuthenticationFilter.WebServer = urlOnly.substring("http://".length(), urlOnly.length());
    }
    
    private static String getYellowfinToken(String userName, String clientOrg)
    {
    	String token = null;

    	try {
    		
    		AdministrationServiceRequest rsr = new AdministrationServiceRequest();
    		AdministrationServiceResponse rs = null;
    		AdministrationPerson person = new AdministrationPerson();
    		person.setUserId(userName);
    		
    		rsr.setLoginId(AuthenticationFilter.WebServiceUserName);
    		rsr.setPassword(AuthenticationFilter.WebServicePassword);
    		rsr.setOrgId(1);
    		rsr.setOrgRef(clientOrg);
    		rsr.setFunction("LOGINUSERNOPASSWORD");
    		rsr.setPerson(person);

    		AdministrationServiceService ts = 
    				new AdministrationServiceServiceLocator(
    						AuthenticationFilter.WebServer, 
    						AuthenticationFilter.WebPort, 
    						AuthenticationFilter.endpoint, 
    						false);
    		try {
    			rs = ts.getAdministrationService().remoteAdministrationCall(rsr);
    		} catch (Exception e) {
    			Logger.getRootLogger().error(e.getMessage());
    		}
    		
    		token = rs.getLoginSessionId();
    	} catch (Exception e) {
    		Logger.getRootLogger().error(e.getMessage());
    	}
    	return token;
    }
    
    private static String getEndPoint(String serviceName)
	{
		String serviceUri = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(serviceName);
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
				// do something useful
					StringWriter writer = new StringWriter();
					String encoding = null;
					IOUtils.copy(instream, writer, encoding);
					String temp = writer.toString();
					com.google.gson.Gson gson = new com.google.gson.Gson();
					com.noesis.Scheduler scheduler = gson.fromJson(temp, com.noesis.Scheduler.class);
					if (scheduler != null && scheduler.Endpoints.length > 0) {
						Integer keyMasterLength = "KeyMaster.svc".length();
						serviceUri = scheduler.Endpoints[0].substring(0, scheduler.Endpoints[0].length() - keyMasterLength);
					}
				} finally {
					instream.close();
				}
				return serviceUri;
			}
		} catch (ClientProtocolException e) {
			Logger.getRootLogger().error(e.getMessage());
		} catch (IOException e) {
			Logger.getRootLogger().error(e.getMessage());
		}
		
		return serviceUri;
	}
    
    
    private static int nthIndexOf(String source, String sought, int n) {
        int index = source.indexOf(sought);
        if (index == -1) return -1;

        for (int i = 1; i < n; i++) {
            index = source.indexOf(sought, index + 1);
            if (index == -1) return -1;
        }
        return index;
    }
    
    private static Boolean isValid(String endPoint, String userName, String password)
	{
		Boolean isValid = false;
		HttpClient httpclient = new DefaultHttpClient();
		String url = endPoint + "RestKeyMaster.svc/json/LoginUsingBrazosIdentity?email=" + userName + "&password=" + password + "&sessionDurationSecs=10";
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
				// do something useful
					StringWriter writer = new StringWriter();
					String encoding = null;
					IOUtils.copy(instream, writer, encoding);
					String result = writer.toString();
					isValid = result.contains("\"ErrorCode\":0");
				} finally {
					instream.close();
				}
			}
		} catch (ClientProtocolException e) {
			Logger.getRootLogger().error(e.getMessage());
		} catch (IOException e) {
			Logger.getRootLogger().error(e.getMessage());
		}

		return isValid;
	}
        
    private static String getUserClientOrg(String userName) {
    	
    	String clientOrg = "";
    	
    	try {
    		AdministrationServiceRequest rsr = new AdministrationServiceRequest();
			AdministrationServiceResponse rs = null;
			AdministrationPerson person = new AdministrationPerson();
			person.setUserId(userName);
			
			rsr.setLoginId(AuthenticationFilter.WebServiceUserName);
			rsr.setPassword(AuthenticationFilter.WebServicePassword);
			rsr.setOrgId(1);
			rsr.setFunction("GETUSERACCESS");
			rsr.setPerson(person);
			
			AdministrationServiceService ts = new AdministrationServiceServiceLocator(AuthenticationFilter.WebServer, AuthenticationFilter.WebPort, AuthenticationFilter.endpoint, false);
			try {
				rs = ts.getAdministrationService().remoteAdministrationCall(rsr);
				AdministrationClientOrg[] orgs = rs.getClients();
				if (orgs != null && orgs.length > 0) {
					AdministrationClientOrg org = orgs[0];
					clientOrg = org.getClientReferenceId();
				}
			} catch (ServiceException e) {
				Logger.getRootLogger().error(e.getMessage());
			}
    	} catch (Exception ex) {
    		Logger.getRootLogger().error(ex.getMessage());
    	}

    	return clientOrg;
    }
}
