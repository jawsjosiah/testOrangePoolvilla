package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HostDao;

@WebServlet("/freezingCustomerController")
public class FreezingCustomerController extends HttpServlet {
	HostDao hostDao = new HostDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// customerDetail.jsp 페이지에서 customerId 받아야 됨 
		String customerId = request.getParameter("customer.customerId");
		System.out.println("[FreezingCustomerController.doGet()] customerId : " + customerId);
		
		// 고객 등급을 -1로 변경하는 메서드 
		hostDao.freezeCustomer(customerId);
		
		// 변경이 완료된다면 
		request.getRequestDispatcher("/WEB-INF/view/customerList.jsp").forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
