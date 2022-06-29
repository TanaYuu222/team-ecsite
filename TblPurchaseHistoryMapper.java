package jp.co.internous.brown.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.brown.model.domain.dto.PurchaseHistoryDto;

@Mapper
public interface TblPurchaseHistoryMapper {
	
	//ユーザーIDに基づいて購入履歴を取得するためのメソッド
	//SQL文はXMLファイルに記述
	List<PurchaseHistoryDto> findHistory(@Param("userId") int userId);
	
	//購入履歴をDBから論理削除するためのメソッド
	@Update("UPDATE tbl_purchase_history SET status = 0, updated_at = now() WHERE user_id = #{userId}")
	int update(int userId);
	
	//購入履歴をDBに登録するためのメソッド
	//SQL文はXMLファイルに記述
	int insert(@Param("destinationId") int destinatoinId, @Param("userId") int userId);

}