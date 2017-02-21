package model;


import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.DuplicateKeyException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.UserBean;


public class UserDAO extends GenericDAO<UserBean> {

	public UserDAO(ConnectionPool connectionPool, String tableName) throws DAOException {
		super(UserBean.class, tableName, connectionPool);
	}
	
	/*public UserBean read(String userName) throws RollbackException {
		//try	{
			//Transaction.begin();
			
			//Transaction.commit();
			return res[0];
		//} finally {
			//if (Transaction.isActive()) Transaction.rollback();
		//}
	}*/
	
/*	public void create(UserBean user) {		
		try {
			//Transaction.begin();
			
			super.create(user);
			//Transaction.commit();
		} catch (DuplicateKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}

	}*/
//    public UserBean setPassword(String Email, ChangePwdForm form) 
//    			throws RollbackException {
//        try {
//        	
//        	UserBean dbUser = read(Email);
//        	
//        	String d1 = computeDigest(dbUser);
//        	
//            if (dbUser == null) {
//                throw new RollbackException("User " + Email + " does not exists!");
//            }
//            System.out.println("2");
//            
//            
//            String d2 = computeDigest(dbUser);
//            if(!d1.equals(d2)) {
//            	return null;
//            }
//            if (form.getOldPassword()!= dbUser.getPassword() ) {
//           	 throw new RollbackException("The old password is not correct!");
//           }
//            Transaction.begin();
//            
//            dbUser.setPassword(form.getConfirmPassword());
//             
//            super.update(dbUser);
//          
//            Transaction.commit();
//        
//            return dbUser;
//        } finally {
//            if (Transaction.isActive()) Transaction.rollback();
//        }
//    }
//    public UserBean setPasswordByEmployee(String UserName, ResetPwdForm form) 
//			throws RollbackException {
//    try {
//    	UserBean dbUser = read(UserName);
//        Transaction.begin();
//        if (dbUser == null) {
//            throw new RollbackException("User " + UserName + " no longer exists!");
//        }
//        
//        dbUser.setPassword(form.getConfirmPassword());
//         
//        super.update(dbUser);
//      
//        Transaction.commit();
//    
//        return dbUser;
//    } finally {
//        if (Transaction.isActive()) Transaction.rollback();
//    }
//}
    public UserBean[] getUsers() throws RollbackException {
    	Transaction.begin();
        UserBean[] users = match();
        Transaction.commit();
        return users;
    }

//    public String computeDigest(UserBean dbUser) {
//    	String ans;
//    	String s1 = String.valueOf(dbUser.getUserId());
//    	String s2 = dbUser.getAddr1()!=null?dbUser.getAddr1():""; 
//    	String s3 = dbUser.getAddr2()!=null?dbUser.getAddr1():"";
//    	String s4 = String.valueOf(dbUser.getCash());
//    	String s5 = dbUser.getCity()!=null?dbUser.getCity():"";
//    	String s6 = dbUser.getFirstName()!=null?dbUser.getFirstName():"";
//    	String s7 = dbUser.getLastName()!=null?dbUser.getLastName():"";
//    	String s8 = String.valueOf(dbUser.getHashedPassword()); 
//    	String s9 = dbUser.getState()!=null?dbUser.getState():"";
//    	String s10 = dbUser.getUserName();
//    	String s11 = dbUser.getZip()!=null?dbUser.getZip():"";
//		
//    	ans = s1 + s2 + s3 + s4+s5+s6+s7+s8+s9+s10+s11;
//    	return ans;
//		
//    	
//    }

}
