package dal.dto;

public class MaterialDTO {
    private int materialId;
    private String materialName;
    private String supplierName;

    public int getMaterialId() {
        return materialId;
    }
    public String getMaterialName() {return this.materialName; }
    public String getSupplierName(){return supplierName;}

    public void setMaterialId(int materialId) {this.materialId = materialId;}
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    public void setSupplierName(String supplierName){this.supplierName = supplierName;}

    @Override
    public String toString() {
        return "MaterialDTO [materialId= " + materialId + ", materialName= " + materialName + ", supplierName= " + supplierName + "]";
    }
}
