package com.tourInteraction.controller;

import com.tourInteraction.config.GlobalConstantKey;
import com.tourInteraction.entity.Role;
import com.tourInteraction.entity.User;
import com.tourInteraction.service.IUserManageService;
import com.tourInteraction.utils.JSONUtil;
import com.tourInteraction.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("userManage")
public class UserManageController {
	
	@Resource(name="userManageServiceImpl")
	private IUserManageService userManageService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("getUser.do")
	public @ResponseBody String getUser( @RequestParam("limit") int limit,@RequestParam("offset") int offset){
		
		logger.info("userManage/getUser.do被调用");
		List<User> list = new ArrayList<User>();
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		mapParam.put("limit", limit);
		mapParam.put("offset", offset);
		int count = userManageService.getUserCount(mapParam);
		list = userManageService.getUser(mapParam);
		Map<String,Object> map = new HashMap<String , Object>();
		map.put("list", list);
		map.put("count", count);
		String result = JSONUtil.map2json(map);
		return result;
	}

	@RequestMapping("getUserRand.do")
	public @ResponseBody String getUserRand( @RequestParam("limit") int limit,@RequestParam("offset") int offset){

		logger.info("userManage/getUserRand.do被调用");
		List<User> list = new ArrayList<User>();
		Map<String, Object> mapParam = new HashMap<String, Object>();

		mapParam.put("limit", limit);
		mapParam.put("offset", offset);
		list = userManageService.getUserRand(mapParam);
		String result = JSONUtil.list2json(list);
		return result;
	}
	
	@RequestMapping("getUserByInteraction.do")
	public @ResponseBody String getUserByInteraction( @RequestParam("limit") int limit,@RequestParam("offset") int offset){
		
		logger.info("userManage/getUser.do被调用");
		List<User> list = new ArrayList<User>();
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		mapParam.put("limit", limit);
		mapParam.put("offset", offset);
		int count = userManageService.getUserCount(mapParam);
		list = userManageService.getUserByInteraction(mapParam);
		Map<String,Object> map = new HashMap<String , Object>();
		map.put("list", list);
		map.put("count", count);
		String result = JSONUtil.map2json(map);
		return result;
	}
	
	@RequestMapping("getUserById.do")
	public @ResponseBody String getUserById( @RequestParam("userId") int userId){
		User user = new User();
		logger.info("userManage/getUser.do被调用");
		user = userManageService.getUserById(userId);
	
		String result = JSONUtil.object2json(user);
		return result;
	}
	
	@RequestMapping("delUserById.do")
	public @ResponseBody String delUserById( @RequestParam("userId") int userId){
		logger.info("userManage/delUserById.do被调用");
		
		int num = userManageService.delUserById(userId);
		String result = "删除失败!";
		if(num > 0){
			result = "删除成功!";
		}
		return result;
	}
	
	@RequestMapping("lockUserById.do")
	public @ResponseBody String lockUserById( @RequestParam("userId") int userId,@RequestParam("status") String status){
		logger.info("userManage/lockUserById.do被调用");
		
		int num = userManageService.lockUserById(userId,status);
		String result = "操作失败!";
		if(num > 0){
			result = "操作成功!";
		}
		return result;
	}
	
	@RequestMapping("getUserRole.do")
	public @ResponseBody String getUserRole(){
		List<Role> list = new ArrayList<Role>();
		logger.info("userManage/getUserRole.do被调用");
		list = userManageService.getUserRole();
		String result = JSONUtil.list2json(list);
		return result;
	}
	
	@RequestMapping("/updateUser.do")
	public @ResponseBody String updateUser(HttpServletRequest req, 
		int id,String userName, String passWord,String email,
		 String phoneNumber,String integration,String roleId){
		logger.info("userManage/updateUser.do被调用");
		User user = SignInAndUpController.getSignInUser(req);
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("id", id);
		mapParam.put("userName", userName);
		mapParam.put("email", email);
		mapParam.put("phoneNumber", phoneNumber);
		if(passWord !=null && passWord !=""){
			mapParam.put("passWord", MD5Util.md5(passWord));
		}
		mapParam.put("integration", integration);
		mapParam.put("roleId", roleId);
		mapParam.put("updateTime", new Date());
		mapParam.put("updateUser", user.getId());
		mapParam.put("status", GlobalConstantKey.STATUS_OPEN);

		int num = userManageService.updateUser(mapParam);
		String result = "修改用户失败";
		if(num>0){
			result = "修改用户成功";
			return result;
		}
		return result;
	}
	
	@RequestMapping("/updateUserDescription.do")
	public @ResponseBody String updateUserDescription(HttpServletRequest req,String userDescription){
		logger.info("userManage/updateUserDescription.do被调用");
		User user = SignInAndUpController.getSignInUser(req);
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("id", user.getId());
		
		mapParam.put("userDescription", userDescription);
		mapParam.put("updateTime", new Date());
		mapParam.put("updateUser", user.getId());
		mapParam.put("status", GlobalConstantKey.STATUS_OPEN);

		int num = userManageService.updateUser(mapParam);
		
		HttpSession session = req.getSession();
		user.setUserDescription(userDescription);
		session.removeAttribute("user");
		session.setAttribute("user", user);
		
		String result = "修改个人介绍失败";
		if(num>0){
			result = "修改个人介绍成功";
			return result;
		}
		return result;
	}
	
	@RequestMapping("/updateUserIcon.do")
	public @ResponseBody String updateUserIcon(HttpServletRequest req, 
		int userIconId){
		logger.info("userManage/updateUserIcon.do被调用");
		User user = SignInAndUpController.getSignInUser(req);
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("userIconId", userIconId);
		mapParam.put("updateTime", new Date());
		mapParam.put("updateUser", user.getId());
		mapParam.put("id", user.getId());
		int num = userManageService.updateUserIcon(mapParam);
		String result = "修改用户头像失败";
		if(num>0){
			result = "修改用户头像成功";
			return result;
		}
		return result;
	}
	
	@RequestMapping("/addUser.do")
	public @ResponseBody String addUser(HttpServletRequest req, 
			@RequestParam("userName") String userName, 
			@RequestParam("email") String email,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("integration") String integration,
			@RequestParam("roleId") String roleId){
		logger.info("userManage/addUser.do被调用");
		User user = SignInAndUpController.getSignInUser(req);
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("passWord", MD5Util.md5("000000"));
		mapParam.put("userName", userName);
		mapParam.put("email", email);
		mapParam.put("phoneNumber", phoneNumber);
		mapParam.put("integration", integration);
		mapParam.put("roleId", roleId);
		mapParam.put("createTime", new Date());
		mapParam.put("createUser", user.getId());
		mapParam.put("status", GlobalConstantKey.STATUS_OPEN);
		
		int num = userManageService.addUser(mapParam);
		String result = "增加用户失败";
		if(num>0){
			result = "增加用户成功";
			return result;
		}
		return result;
	}
	

}
