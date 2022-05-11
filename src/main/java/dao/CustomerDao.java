package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import vo.Customer;




public class CustomerDao {
	
	public int deleteCustomer(Customer Customer) {
		int row = -1; // ��� ������ ���� ����,���� �߻��� -1�� ���
		//DB �ڿ� �غ�
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		//���� �ۼ�
		//select customer_id
		String selectCustomerNoSql = "SELECT customer_id CustomerNo FROM Customer WHERE Customer_id=?";
		
		//Customer ���̺� ������ ����
		String deleteCustomerSql = "DELETE FROM Customer WHERE Customer_id=? AND Customer_pw=PASSWORD(?)";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/orangepoolvilla","root","java1234");
			conn.setAutoCommit(false); // �ڵ� Ŀ�� ����
			//0. select customer_id
			stmt = conn.prepareStatement(selectCustomerNoSql);
			stmt.setString(1, Customer.getCustomerId());
			rs = stmt.executeQuery();
			List<Integer> list = new ArrayList<>(); //CustomerId ���� ����Ʈ 
			while(rs.next()) {//CustomerId�� ���� ��� CustomerId ���� ����
				list.add(rs.getInt("CustomerId"));
				
			}
			//stmt ���� �� ���� ���� ��û

			//3. Customer ���̺� ������ ����
			stmt = conn.prepareStatement(deleteCustomerSql);
			stmt.setString(1, Customer.getCustomerId());
			stmt.setString(2, Customer.getCustomerPw());
			row = stmt.executeUpdate();
			if (row == 1) { // ���� ������ commit
				conn.commit();
			} else { // �����̿��� ����� rollback
				conn.rollback();
			}
		} catch (Exception e) {
			try {
				conn.rollback();
				
			} catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				//DB�ڿ� �ݳ�
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	//ȸ������
		public Customer selectCustomerOne(String CustomerId) {
			Customer m = new Customer();
			//DB �ڿ� �غ�
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			//���� �ۼ�
			String sql ="SELECT Customer_id CustomerId"
					+ "					,name"
					+ "					,gender"
					+ "					,birthDate"
					+ "					,create_date createDate "
					+ "		 FROM Customer "
					+ "		WHERE Customer_id=? ";
			//DB�� �� ��û
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/orangepoolvilla","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, CustomerId);
				rs =stmt.executeQuery();
				if(rs.next()) {
					m.setCustomerId(rs.getString("CustomerID"));
					m.setName(rs.getString("name"));
					m.setGender(rs.getString("gender"));
					m.setBirthDate(rs.getString("birthDate"));
					m.setCreateDate(rs.getString("createDate"));
				}
						
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try {
							//DB�ڿ� �ݳ�
							rs.close();
							stmt.close();
							conn.close();
						}catch(SQLException e) {
							e.printStackTrace();
						}
					}		
			return m;
		}
	//Customer ���̺� �Է�
		public int insertCustomer(Customer customer) {
			int row = -1; // ��� ������ ���� ����,���� �߻��� -1�� ���
			String CustomerId =null; //�α��� ���н� null�� ����
			//DB �ڿ� �غ�
			Connection conn = null;
			PreparedStatement stmt = null;
			//���� �ۼ�
			String sql ="INSERT INTO Customer (Customer_id "
					+ "									,Customer_pw "
					+ " 								,name "
					+ "									, gender "
					+ "									,age "
					+ "									,create_date) "
					+ "			VALUES (?,PASSWORD(?),?,?,?,NOW()) ";
			//DB�� �� ��û
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/orangepoolvilla","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, customer.getCustomerId());
				stmt.setString(2, customer.getCustomerPw());
				stmt.setString(3, customer.getName());
				stmt.setString(4, customer.getGender());
				stmt.setString(5, customer.getBirthDate());
				row =stmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					//DB�ڿ� �ݳ�
					stmt.close();
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return row;
		}
		
		//Customer ���̺� ����
		public int updateCustomerByIdPw(Customer customer,String newCustomerPw) {
			int row = -1; // ��� ������ ���� ����,���� �߻��� -1�� ���
			String CustomerId =null; //�α��� ���н� null�� ����
			if(newCustomerPw.equals("")) {//���ο� ��й�ȣ ������ "" �̶�� �����й�ȣ�� ä��
				newCustomerPw=customer.getCustomerPw();
			}
			//DB �ڿ� �غ�
			Connection conn = null;
			PreparedStatement stmt = null;
			//���� �ۼ�
			String sql ="UPDATE Customer SET name = ? "
					+ "									, gender=? "
					+ "									,age=? "
					+ "									,Customer_pw = PASSWORD(?) "
					+ "			WHERE Customer_id = ? "
					+ "			AND Customer_pw =PASSWORD(?)";
			//DB�� �� ��û
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/orangepoolvilla","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, customer.getName());
				stmt.setString(2, customer.getGender());
				stmt.setString(3, customer.getBirthDate());
				stmt.setString(4, newCustomerPw);
				stmt.setString(5, customer.getCustomerId());
				stmt.setString(6, customer.getCustomerPw());
				row =stmt.executeUpdate();
						
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					//DB�ڿ� �ݳ�
					stmt.close();
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return row;
		}
}