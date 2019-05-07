package dal.dto;

public class MaterialDTO {

    private int batchId;
    private String name;
    private int ingredientId;
    private int quantity;
    private String supplier;
    private boolean expired;


    public MaterialDTO (int batchId, String name, int ingredientId, int quantity, String supplier, boolean expired){
        this.batchId = batchId;
        this.name = name;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.supplier = supplier;
        this.expired = expired;
    }

    public MaterialDTO() {

    }

    public int getBatchId() {
        return batchId;
    }
    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getIngredientId() {
        return ingredientId;
    }
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public boolean isExpired() {
        return expired;
    }
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "MaterialDTO [materialId= " + batchId + ", materialName= " + name + ", supplierName= " + supplier + "]";
    }
}
