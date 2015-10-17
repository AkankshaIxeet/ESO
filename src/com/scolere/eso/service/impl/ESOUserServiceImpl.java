package com.scolere.eso.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adventnet.zoho.client.report.ParseException;
import com.adventnet.zoho.client.report.ReportClient;
import com.adventnet.zoho.client.report.ServerException;
import com.scolere.eso.application.web.form.EsoUserForm;
import com.scolere.eso.domain.vo.ESOUserVO;
import com.scolere.eso.domain.to.InterestTO;
import com.scolere.eso.domain.vo.InterestVO;
import com.scolere.eso.persistance.dao.iface.ESOUserDao;
import com.scolere.eso.persistance.dao.impl.ESOUserDaoImpl;
import com.scolere.eso.persistance.factory.Config;
import com.scolere.eso.persistance.factory.ESODaoAbstract;
import com.scolere.eso.persistance.factory.EsoDaoFactory;
import com.scolere.eso.service.iface.ESOUserServiceIface;

public class ESOUserServiceImpl extends ESODaoAbstract implements ESOUserServiceIface{
	
	
    private ReportClient rc = Config.getReportClient();
    ESOUserDao eSOUserDaoImpl = new ESOUserDaoImpl();
    
	
	@Override
	public ESOUserVO getUser(EsoUserForm form) {
		ESOUserVO eSOUserVO = new ESOUserVO() ;
		 try {
			 
			 ESOUserDao dao = (ESOUserDao) EsoDaoFactory.getDAO(ESOUserDao.class);
			 eSOUserVO = dao.getUser(form);

	        } catch (Exception ex) {
	            System.out.println("ESOServiceException #  = "+ex);
	        }
		return eSOUserVO;
	}
	
	@Override
	public ESOUserVO getCRMUser(EsoUserForm form) {
		ESOUserVO eSOUserVO = new ESOUserVO() ;
		 try {
			 
			 ESOUserDao dao = (ESOUserDao) EsoDaoFactory.getDAO(ESOUserDao.class);
			 eSOUserVO = dao.getCRMUser(form);

	        } catch (Exception ex) {
	            System.out.println("ESOServiceException #  = "+ex);
	        }
		return eSOUserVO;
	}
	
	@Override
	public List<InterestVO> getUserInterest() {
		
		List<InterestVO> inList = null;
		try{
			ESOUserDao dao = (ESOUserDao) EsoDaoFactory.getDAO(ESOUserDao.class);
			List<InterestTO> interestFromDb = dao.getUserInterest();
			inList = new ArrayList<InterestVO>(interestFromDb.size());
			InterestVO interestVO2 = null;
			for(InterestTO to:interestFromDb)
			{
				interestVO2 = new InterestVO(to.getInterestId(),to.getInterestName());
				inList.add(interestVO2);
				
			}
			
		}catch (Exception e){
			System.out.println("getUserInterest = "+e);
		}
		return inList;

	}
	
	@Override
	public boolean updateESOUserProfile(EsoUserForm form) throws IOException, ServerException, ParseException{
		
		String uri = rc.getURI(Config.LOGINEMAILID,Config.DATABASENAME,"ESO_USERS");
        rc.updateData(uri,eSOUserDaoImpl.profileUpdate(form),getCriteria(form),null);
        
        System.out.println("Successfully added the row to " + uri);
        //System.out.println(" The response is " + result);	
		return true;
	}

	@Override
	public boolean updateESOUserInterest(int userId,int interestId) throws IOException,ServerException, ParseException {
		
		String uri = rc.getURI(Config.LOGINEMAILID,Config.DATABASENAME,"USER_INTERESTS_TXN");
        Map result = rc.addRow(uri,eSOUserDaoImpl.saveUserInterest(userId, interestId),null);
        
       // System.out.println("Data matching the criteria " + getCriteria(EsoUserForm form) + " successfully updated.");
        System.out.println("Successfully added the row to " + uri);
        //System.out.println(" The response is " + result);	
		return true;
	}

	@Override
	public boolean addESOUserOtherInterest(EsoUserForm form) throws IOException, ServerException, ParseException {
		
		String uri = rc.getURI(Config.LOGINEMAILID,Config.DATABASENAME,"USER_INTERESTS");
        Map result = rc.addRow(uri,eSOUserDaoImpl.saveUserOtherInterest(form),null);
        
       // System.out.println("Data matching the criteria " + getCriteria(EsoUserForm form) + " successfully updated.");
        System.out.println("Successfully added the row to " + uri);
        //System.out.println(" The response is " + result);	
		return true;
	}
	
	@Override
	public boolean updateUserLoginFlag(ESOUserVO vo) throws IOException,
			ServerException, ParseException {
		
		String uri = rc.getURI(Config.LOGINEMAILID,Config.DATABASENAME,"ESO_USERS");
        rc.updateData(uri,eSOUserDaoImpl.loginFlagUpdate(vo),getCriteria(vo),null);
        
        System.out.println("Successfully added the row to " + uri);
        
        return true;
	}
	
	@Override
	public boolean updateESOUserProfilePic(EsoUserForm form)
			throws IOException, ServerException, ParseException {
		
		String uri = rc.getURI(Config.LOGINEMAILID,Config.DATABASENAME,"ESO_USERS");
        rc.updateData(uri,eSOUserDaoImpl.profilePicUpdate(form),getCriteria(form),null);
        
        System.out.println("Successfully added the row to " + uri);
        //System.out.println(" The response is " + result);	
		return true;
	}
	
    public String getCriteria(ESOUserVO vo)
    {
        return "(\"Email\" = '"+vo.getEmailId()+"')";
    }
    
    public String getCriteria(EsoUserForm form)
    {
        return "(\"Email\" = '"+form.getEmailId()+"')";
    }

	@Override
	public boolean restPassword(EsoUserForm form) throws IOException,
			ServerException, ParseException {
		
		String uri = rc.getURI(Config.LOGINEMAILID,Config.DATABASENAME,"ESO_USERS");
        rc.updateData(uri,eSOUserDaoImpl.resetPassword(form),getCriteria(form),null);
        
        System.out.println("Successfully added the row to " + uri);
        //System.out.println(" The response is " + result);	
		return true;
	}

	@Override
	public boolean addAlternativeUser(EsoUserForm form) throws IOException,
			ServerException, ParseException {
		String uri = rc.getURI(Config.LOGINEMAILID,Config.DATABASENAME,"ESO_USERS");
        Map result = rc.addRow(uri,eSOUserDaoImpl.saveESOUservO(form),null);
        
       // System.out.println("Data matching the criteria " + getCriteria(EsoUserForm form) + " successfully updated.");
        System.out.println("Successfully added the row to " + uri);
        //System.out.println(" The response is " + result);	
		return true;
	}

}
