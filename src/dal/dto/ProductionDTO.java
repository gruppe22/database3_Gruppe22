package dal.dto;

import java.sql.Date;

public class ProductionDTO {

    private int productionId;
    private int quantity;
    private String status;
    private int recipeId;
    private Date recipeEndDate;
    private int produktionsLeaderID;

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

    public Date getRecipeEndDate() {
        return recipeEndDate;
    }
    public void setRecipeEndDate(Date recipeEndDate) {
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
