package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz;

public class FunctionLibrary {
    /**
     * Đếm số từ trong một chuỗi (tương đối)
     * Đếm số dấu cách " " sau đó + 1, ra số từ
     *
     * @param text Chuỗi cần đếm từ
     * @return Số từ của chuỗi đó
     */
    public int countWords(String text) {
        int wordCount = 0;
        text = text.trim();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                wordCount++;
            }
        }
        return wordCount + 1;
    }

    /**
     * Lấy vị trí của dấu cách thứ x trong chuỗi.
     * @param text
     * @param x    Dấu cách thứ mấy (muốn lấy vị trí). (ví dụ muốn lấy vị trí
     *             của dấu cách số 15 thì x là 15)
     * @return
     */
    public int getIndexOfTheXthSpace(String text, int x) {
        int spaceCount = 0;
        int indexOfXthSpace = 0;
        text = text.trim();

        while (spaceCount < x) {
            if (text.charAt(indexOfXthSpace) == ' ') {
                spaceCount++;
            }
            indexOfXthSpace++;
        }

        return indexOfXthSpace;
    }

    /**
     * Lấy x từ đầu tiên của chuỗi.
     * Lấy chuỗi con của chuỗi nhập vào từ vị trí 0 đến vị trí của dấu cách thứ x.
     * @param text Chuỗi truyền vào
     * @param numberOfWordsWantRetrieve Số lượng từ muốn lấy từ chuỗi truyền vào
     * @return Chuỗi chứa x từ đầu tiên của chuỗi truyền vào
     */
    public String getFirstXWordsOfString(String text, int numberOfWordsWantRetrieve) {
        int indexOfXthSpace = getIndexOfTheXthSpace(text, numberOfWordsWantRetrieve);
        return text.substring(0, indexOfXthSpace-1);
    }
}
