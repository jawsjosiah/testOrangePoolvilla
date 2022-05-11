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

@WebServlet("/DeleteCustomerController")
public class DeleteCustomerController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session �� ��û
		HttpSession session = request.getSession();
	    String sessionCustomerId = (String)session.getAttribute("sessionCustomerId");
	    //�α����� �ȵǾ����� ��� LoginController�� ����
	    if(sessionCustomerId == null) {
	        response.sendRedirect(request.getContextPath()+"/LoginController");
	        System.out.println("noLogin");//�����
	        return;
	      }
	    //id������ ȸ��Ż�� �� ȣ��
	    request.setAttribute("CustomerId", sessionCustomerId);
	    request.getRequestDispatcher("/WEB-INF/view/deleteCustomerForm.jsp").forward(request, response);
			}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session �� ��û
		HttpSession session = request.getSession();
	    String sessionCustomerId = (String)session.getAttribute("sessionCustomerId");
	    //�α����� �ȵǾ����� ��� LoginController�� ����
	    if(sessionCustomerId == null) {
	        response.sendRedirect(request.getContextPath()+"/LoginController");
	        System.out.println("noLogin");//�����
	        return;
	      }
		//��üũ
	    if(request.getParameter("CustomerPw")==null||request.getParameter("CustomerId")==null) {
	    	//�޼����� �Բ� DeleteCustomerController �ٽ� ��û
	    	response.sendRedirect(request.getContextPath()+"/DeleteCustomerController?msg=null");
	    	return;
	    }
	    //��û�� ó��
	    Customer customer = new Customer();
	    customer.setCustomerId(request.getParameter("CustomerId"));
	    customer.setCustomerPw(request.getParameter("CustomerPw"));
	    //�����
	    System.out.println(customer.toString()+"DeleteCustomerController.dopost");
	    //Dao�� delete ��û
	    CustomerDao CustomerDao = new CustomerDao();
	    int row = CustomerDao.deleteCustomer(customer);
	    if (row==1) { //������ �α� �ƿ� �Ŀ� Logincontroller���� ��������
	    	System.out.println("�������� DeleteCustomerController.dopost");
	    	request.getSession().invalidate();//session ���� �޼���-�α׾ƿ�
	    	response.sendRedirect(request.getContextPath()+"/LoginController");
	    	return;
	    }else if(row==0) {// row==0�̸� ������� ���� �����Ƿ� (row �⺻�� -1), ��������,��й�ȣ����
	    	System.out.println("�������� deleteCustomerController.dopost");
	    	response.sendRedirect(request.getContextPath()+"/DeleteCustomerController?msg=wrongPw");
	    	return;
	    }else if (row==-1) {//row�� -1�̸� sql�� �۵� ����
	    	System.out.println("���� �߻� DeleteCustomerController.dopost");
	    	response.sendRedirect(request.getContextPath()+"/DeleteCustomerController?msg=exception");
	    	return;
	    }
	}

}