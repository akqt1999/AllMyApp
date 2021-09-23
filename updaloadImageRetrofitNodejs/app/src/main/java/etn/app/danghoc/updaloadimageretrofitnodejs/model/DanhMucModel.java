package etn.app.danghoc.updaloadimageretrofitnodejs.model;


import java.util.List;

public class DanhMucModel {
    private boolean success;
    private List<CategoryProduct> result;

    public DanhMucModel(boolean success, List<CategoryProduct> result) {
        this.success = success;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<CategoryProduct> getResult() {
        return result;
    }

    public void setResult(List<CategoryProduct> result) {
        this.result = result;
    }
}
