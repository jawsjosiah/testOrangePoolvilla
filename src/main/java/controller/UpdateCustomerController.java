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
@WebServlet("/UpdateCustomerController")
public class UpdateCustomerController extends HttpServlet {
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
	    //Dao ȣ��
	    CustomerDao customerDao = new CustomerDao();
	    //id������ DB�� �󼼺��� �� ȣ��
	    Customer Customer = new Customer();
	    Customer  = customerDao.selectCustomerOne(sessionCustomerId);
	    request.setAttribute("Customer", Customer);
	    request.getRequestDispatcher("/WEB-INF/view/updateCustomerForm.jsp").forward(request, response);
	}	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���ڵ�
		request.setCharacterEncoding("UTF-8");
		//session �� ��û
		HttpSession session = request.getSession();
	    String sessionCustomerId = (String)session.getAttribute("sessionCustomerId");
	    //�α����� �ȵǾ����� ��� LoginController�� ����
	    if(sessionCustomerId == null) {
	        response.sendRedirect(request.getContextPath()+"/LoginController");
	        System.out.println("noLogin");//�����
	        return;
	      }
	    //�� üũ
	    if(request.getParameter("name")==null||request.getParameter("age")==null||request.getParameter("CustomerPw")==null||request.getParameter("CustomerId")==null||request.getParameter("gender")==null) {
	    	System.out.println("null UpdateCustomercontroller.dopost");
	    	response.sendRedirect(request.getContextPath()+"/UpdateCustomerController");//��û���� null������ UpdateCustomerController�� ��������
	    	return;
	    }
	    //form ��û �� ó��
	    Customer customer = new Customer();
	    customer.setCustomerId(request.getParameter("CustomerId"));
	    customer.setName(request.getParameter("name"));
	    customer.setBirthDate(request.getParameter("BirthDate"));
	    customer.setGender(request.getParameter("gender"));
	    customer.setCustomerPw(request.getParameter("CustomerPw"));
	    System.out.println(customer.toString()+"<-UPdateCustomerController.dopost");//�����
	    
	    String newCustomerPw="";
	    if(request.getParameter("newPw")==null) {
	    	System.out.println("1245");
	    }
	    if(request.getParameter("newPw").equals("open")){//��й�ȣ ���� ��û ���� Ȯ��
	    	if(request.getParameter("newCustomerPw1")!=null&&!request.getParameter("newCustomerPw1").equals("")&&request.getParameter("newCustomerPw1").equals(request.getParameter("newCustomerPw2"))) {
	    		//���ο� ��й�ȣ�� null,"" �� �ƴϰ� pw1,pw2�� ��ġ�ϸ� newCustomerPw�� ����
	    		newCustomerPw=request.getParameter("newCustomerPw1");
	    		System.out.println(newCustomerPw+"<- newCustomerPw UpdateCustomerController.dopost");//�����
	    	}else if(request.getParameter("newCustomerPw1")!=null&&!request.getParameter("newCustomerPw1").equals("")&&!request.getParameter("newCustomerPw1").equals(request.getParameter("newCustomerPw2"))){
	    		//null, ""�� �ƴ�����, pw1,pw2�� ��ġ���� ���� ��� msg�� �Բ� ��������
	    		response.sendRedirect(request.getContextPath()+"/UpdateCustomerController?msg=notMatch");
	    		return;
	    	}
	    }
	    //Dao�� update ��û
	    CustomerDao CustomerDao = new CustomerDao();
	    int row = CustomerDao.updateCustomerByIdPw(customer, newCustomerPw);
	    System.out.println(row+"<-row UpdateCustomerController.dopist");
	    if (row==1) { //������ SelectCustomerOnecontroller���� ��������
	    	System.out.println("�������� UpdateCustomerController.dopist");
	    	response.sendRedirect(request.getContextPath()+"/SelectCustomerOneController");
	    	return;
	    }else if(row==0) {// row==0�̸� ������� ���� �����Ƿ� (row �⺻�� -1), ��й�ȣ ����
	    	System.out.println("�������к�й�ȣ���� UpdateCustomerController.dopist");
	    	response.sendRedirect(request.getContextPath()+"/UpdateCustomerController?msg=wrongPw");
	    	
	    }else if (row==-1) {//row�� -1�̸� sql�� �۵� ����
	    	System.out.println("���� �߻� UpdateCustomerController.dopist");
	    	response.sendRedirect(request.getContextPath()+"/UpdateCustomerController?msg=exception");
	    }
	    
	    

	    
	}
}