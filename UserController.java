package jp.co.internous.brown.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.brown.model.domain.MstUser;
import jp.co.internous.brown.model.form.UserForm;
import jp.co.internous.brown.model.mapper.MstUserMapper;
import jp.co.internous.brown.model.session.LoginSession;

@Controller
@RequestMapping("/brown/user")
public class UserController {
	
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private MstUserMapper mstUserMapper;
	
	@RequestMapping("/")
	public String index(Model m) {
		
		m.addAttribute("loginSession", loginSession);
		
		return "register_user";
	}
	
	//DBの会員情報マスタテーブルに、入力されたユーザー名と一致するユーザーが存在するか確認する
	@RequestMapping("/duplicatedUserName")
	@ResponseBody
	public boolean duplicationUserName(@RequestBody UserForm f) {
		
		String userName = f.getUserName();
		List<MstUser> users = mstUserMapper.findByUserName(userName);
		
		return users != null && users.size() > 0;
		
	}
	
	//入力されたユーザーの情報を、データベースに登録する
	@RequestMapping("/register")
	@ResponseBody
	public boolean addUser(@RequestBody UserForm userForm) {
		
		MstUser mstUser = new MstUser();
		mstUser.setUserName(userForm.getUserName());
		mstUser.setPassword(userForm.getPassword());
		mstUser.setFamilyName(userForm.getFamilyName());
		mstUser.setFirstName(userForm.getFirstName());
		mstUser.setFamilyNameKana(userForm.getFamilyNameKana());
		mstUser.setFirstNameKana(userForm.getFirstNameKana());
		mstUser.setGender(userForm.getGender());
		
		int count = mstUserMapper.insert(mstUser);
		
		return count > 0;
		
	}
}
