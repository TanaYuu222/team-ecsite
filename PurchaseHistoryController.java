package jp.co.internous.brown.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.brown.model.domain.dto.PurchaseHistoryDto;
import jp.co.internous.brown.model.mapper.TblPurchaseHistoryMapper;
import jp.co.internous.brown.model.session.LoginSession;

@Controller
@RequestMapping("/brown/history")
public class PurchaseHistoryController {
	
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private TblPurchaseHistoryMapper tblPurchaseHistoryMapper;

	//ユーザーIDに紐づく購入履歴情報をデータベースから取得し、フロントへ送る
	@RequestMapping("/")
	public String index(Model m) {
		
		int userId = loginSession.getId();
		
		List<PurchaseHistoryDto> history = tblPurchaseHistoryMapper.findHistory(userId);
		m.addAttribute("history", history);
		
		m.addAttribute("loginSession", loginSession);
		
		return "purchase_history";
	}
	
	//削除ボタンが押下されたとき、購入履歴をDBから論理削除(tbl_purchase_history.statusを0に更新)
	@RequestMapping("/delete")
	@ResponseBody
	public boolean remover() {
		
		int userId = loginSession.getId();
		
		int result = tblPurchaseHistoryMapper.update(userId);
		return result > 0;
		
	}
}
