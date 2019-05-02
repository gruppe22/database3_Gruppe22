package dal.dto;

import java.time.LocalTime;

public class BatchDTO {
    private int batchNumber;
    private RecipeDTO recipeId;
    private MaterialDTO materialId;
    private UserDTO operator;
    private LocalTime dateTime;
    private boolean discarded;

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public MaterialDTO getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MaterialDTO materialId) {
        this.materialId = materialId;
    }

    public RecipeDTO getRecipeId(){return recipeId;}

    public void setRecipeId(){this.recipeId = recipeId;}

    public UserDTO getOperator() {
        return operator;
    }

    public void setOperator(UserDTO operator) {
        this.operator = operator;
    }

    public LocalTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
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
