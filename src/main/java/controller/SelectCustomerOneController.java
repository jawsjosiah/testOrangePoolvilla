package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDao;
import vo.Customer;
@WebServlet("/SelectCustomerOneController")
public class SelectCustomerOneController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session �� ��û
		HttpSession session = request.getSession();
	    String sessionCustomerId = (String)session.getAttribute("sessionCustomerId");
	    //�α����� �ȵǾ����� ��� LoginController�� ����
	    if(sessionCustomerId == null) {
	        response.sendRedirect(request.getContextPath()+"/LoginController");
	        System.out.println("notLogin");
	        return;
	    }
	    //�� ȣ��
	    CustomerDao CustomerDao = new CustomerDao();
	    Customer customer = CustomerDao.selectCustomerOne(sessionCustomerId);
	    request.setAttribute("Customer", customer);
	    request.getRequestDispatcher("/WEB-INF/view/CustomerOne.jsp").forward(request, response);
	}

}