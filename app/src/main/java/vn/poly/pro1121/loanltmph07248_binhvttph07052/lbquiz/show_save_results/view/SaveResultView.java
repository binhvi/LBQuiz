package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.view;

import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity.SaveResult;

public interface SaveResultView {
    /**
     * Lớp view định nghĩa các hàm hiển thị dữ liệu
     * @param resultList
     */
    void displaySaveResult(List<SaveResult> resultList);

    /**
     * Chuyền chuỗi để tìm kiếm kết quả quiz theo tên
     * @param text
     */
    void giveFilterText(String text);
}
