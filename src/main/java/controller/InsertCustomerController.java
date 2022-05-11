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

@WebServlet("/InsertCustomerController")
public class InsertCustomerController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�α��� �Ǿ� �ִ� ����� CashBookByMonthController�� �����̷�Ʈ
		HttpSession session = request.getSession();
		if(session.getAttribute("sessionCustomerId") != null) {
			response.sendRedirect(request.getContextPath()+"/CashBookByMonthController");
			return;
		}
		//insertCustomerForm.jsp ȣ��
		request.getRequestDispatcher("/WEB-INF/view/insertCustomerForm.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���ڵ�
		request.setCharacterEncoding("UTF-8");
		//�α��� �Ǿ� �ִ� ����� CashBookByMonthController�� �����̷�Ʈ
		HttpSession session = request.getSession();
		if(session.getAttribute("sessionCustomerId") != null) {
			response.sendRedirect(request.getContextPath()+"/CashBookByMonthController");
			return;
		}
	    //�� üũ
	    if(request.getParameter("name")==null||request.getParameter("age")==null||request.getParameter("CustomerId")==null||request.getParameter("gender")==null) {
	    	System.out.println("null insertCustomercontroller.dopost");
	    	response.sendRedirect(request.getContextPath()+"/UpdateCustomerController?msg=null");//��û���� null������ UpdateCustomerController�� ��������
	    	return;
	    }
	    //��û�� ó��
	    String CustomerPw =null; //��й�ȣ�� �� ���� �ʱ�ȭ
	    if(request.getParameter("CustomerPw1")!=null&&request.getParameter("CustomerPw2")!=null&&!request.getParameter("CustomerPw1").equals("")&&request.getParameter("CustomerPw1").equals(request.getParameter("CustomerPw2"))) {
	    // null, ��ĭ�� �ƴ� ��й�ȣ�� ���� ��ġ�Ѵٸ� CustomerPw�� ����
	     CustomerPw = request.getParameter("CustomerPw1");
	    }else if(request.getParameter("CustomerPw1")!=null&&request.getParameter("CustomerPw2")!=null&&!request.getParameter("CustomerPw1").equals("")&&!request.getParameter("CustomerPw1").equals(request.getParameter("CustomerPw2"))){
	    	// null, ��ĭ�� �ƴ����� ��й�ȣ�� ���� ��ġ���� �ʴ´ٸ� msg�� �Բ� ��������
	    	response.sendRedirect(request.getContextPath()+"/InsertCustomerController?msg=notMatch");
	    	return;
	    }
	    Customer customer = new Customer();
	    customer.setCustomerId(request.getParameter("CustomerId"));
	    customer.setName(request.getParameter("name"));
	    customer.setBirthDate(request.getParameter("age"));
	    customer.setGender(request.getParameter("gender"));
	    customer.setCustomerPw(CustomerPw);
	    //�����
	    System.out.println(customer.toString()+"<-insertCustomerController.dopost");
	    //Dao�� insert ��û
	    CustomerDao CustomerDao = new CustomerDao();
	    int row = CustomerDao.insertCustomer(customer);
	    if (row==1) { //������ Logincontroller���� ��������
	    	System.out.println("���Լ��� InsertCustomerController.dopist");
	    	response.sendRedirect(request.getContextPath()+"/LoginController");
	    	return;
	    }else if(row==0) {// row==0�̸� ������� ���� �����Ƿ� (row �⺻�� -1), ���Խ���
	    	System.out.println("���Խ��� insertCustomerController.dopist");
	    	response.sendRedirect(request.getContextPath()+"/InsertCustomerController?msg=fail");
	    	
	    }else if (row==-1) {//row�� -1�̸� sql�� �۵� ����
	    	System.out.println("���� �߻� insertCustomerController.dopist");
	    	response.sendRedirect(request.getContextPath()+"/InsertCustomerController?msg=exception");
	    }
	    
	    
	    
	
	}
}