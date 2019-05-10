package dal.dto;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class ProductionDTO {

    private int productionId;
    private int quantity;
    private String status;
    private int recipeId;
    private Timestamp recipeEndDate;
    private int produktionsLeaderID;

    public List<MaterialDTO> getMateials() {
        return mateials;
    }
    public void setMateials(List<MaterialDTO> mateials) {
        this.mateials = mateials;
    }

    private List<MaterialDTO> mateials = new LinkedList<>();

    public int getProductionId() {
        return productionId;
    }
    public void setProductionId(int productionId) {
        this.productionId = productionId;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Timestamp getRecipeEndDate() {
        return recipeEndDate;
    }
    public void setRecipeEndDate(Timestamp recipeEndDate) {
        this.recipeEndDate = recipeEndDate;
    }

    public int getProduktionsLeaderID() {
        return produktionsLeaderID;
    }
    public void setProduktionsLeaderID(int produktionsLeaderID) {
        this.produktionsLeaderID = produktionsLeaderID;
    }

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }
}
