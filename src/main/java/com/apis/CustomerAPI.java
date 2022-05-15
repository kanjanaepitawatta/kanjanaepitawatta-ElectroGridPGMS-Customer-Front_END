package com.apis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.services.CustomerService;

/**
 * Servlet implementation class CustomerAPI
 */
@WebServlet("/CustomerAPI")
public class CustomerAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    CustomerService customerService = new CustomerService();
    
  //convert request parameters to a map
  	private static Map getParasMap(HttpServletRequest request) {
  		Map<String, String> map = new HashMap<String, String>();

  		try {
  			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
  			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
  			scanner.close();

  			String[] params = queryString.split("&");

  			for (String param : params) {
  				String[] p = param.split("=");
  				map.put(p[0], p[1]);
  			}
  		} catch (Exception e) {

  		}

  		return map;
  	}
    public CustomerAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//sending values to insert function
		String output = customerService.insertCustomer(	request.getParameter("firstName"),
											request.getParameter("lastName"),
											request.getParameter("email"),
											request.getParameter("mobile"),
											request.getParameter("address"),
											request.getParameter("postalCode"));
											
		//sending the output to client
		response.getWriter().write(output);
	}
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//parameter map
		Map paras = getParasMap(request);

		//getting values from the map and sending to update function
		String output = customerService.updateCustomer(	paras.get("hidCustomerIDSave").toString(),
													paras.get("firstName").toString(),
													paras.get("lastName").toString(),
													paras.get("email").toString(),
													paras.get("mobile").toString(),
													paras.get("address").toString(),													
													paras.get("postalCode").toString());
													
		//sending the output to client
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//parameter map
		Map paras = getParasMap(request);

		//getting values from the map and sending to delete function
		String output = customerService.deleteCustomer(	paras.get("userID").toString());
				
		//sending the output to client
		response.getWriter().write(output);
	}

}
