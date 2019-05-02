package dal.dto;

public class MaterialDTO {

    private int materialBatchId;
    private String name;
    private int ingredientId;
    private String quantity;
    private String supplier;
    private boolean expired;

    public int getMaterialBatchId() {
        return materialBatchId;
    }
    public void setMaterialBatchId(int materialBatchId) {
        this.materialBatchId = materialBatchId;
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

    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
        return "MaterialDTO [materialId= " + materialBatchId + ", materialName= " + name + ", supplierName= " + supplier + "]";
    }
}
