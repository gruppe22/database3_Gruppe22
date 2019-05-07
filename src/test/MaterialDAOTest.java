package test;

import dal.dao.IMaterialDAO;
import dal.dao.MaterialDAO;
import dal.dto.MaterialDTO;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class MaterialDAOTest {
    MaterialDAO MDAO = new MaterialDAO();
    MaterialDTO myM_n1;
    MaterialDTO myM_n2;

    @Test
    void createMaterial()throws IMaterialDAO.DALException{

        int batchId = 25;
        String name = "Bobs Materiale Grej";
        int ingredientId = 255;
        int quantity = 2500025;
        String supplier = "min Supplier";
        boolean expired = false;

        MaterialDTO myM = SetupMaterial(batchId, name, ingredientId, quantity, supplier, expired);

            MDAO.createMaterial(myM);
            myM_n1 = MDAO.getMaterial(ingredientId);

            assertEquals(myM.getBatchId(), myM_n1.getBatchId());
            assertEquals(myM.getIngredientId(), myM_n1.getIngredientId());
            assertEquals(myM.getName(), myM_n1.getName());
            assertEquals(myM.getQuantity(), myM_n1.getQuantity());
            assertEquals(myM.getSupplier(), myM_n1.getSupplier());
            assertEquals(myM.isExpired(), myM_n1.isExpired());

        MDAO.SUPERdeleteMaterial(batchId);
    };

    @Test
    void getMaterial()throws IMaterialDAO.DALException {

        int batchId = 24;
        String name = "Bobsys Materiale Grej";
        int ingredientId = 252;
        int quantity = 2500025;
        String supplier = "min SupplierY";
        boolean expired = false;


        MaterialDTO myM = SetupMaterial(batchId, name, ingredientId, quantity, supplier, expired);
            MDAO.createMaterial(myM);
            myM_n2 = MDAO.getMaterial(ingredientId);

            assertEquals(myM.getBatchId(), myM_n2.getBatchId());
            assertEquals(myM.getIngredientId(), myM_n2.getIngredientId());
            assertEquals(myM.getName(), myM_n2.getName());
            assertEquals(myM.getQuantity(), myM_n2.getQuantity());
            assertEquals(myM.getSupplier(), myM_n2.getSupplier());
            assertEquals(myM.isExpired(), myM_n2.isExpired());

        MDAO.SUPERdeleteMaterial(batchId);
    }

    @Test
    void getMaterialList()throws IMaterialDAO.DALException {
        Queue<MaterialDTO> myDtos = (Queue<MaterialDTO>) MDAO.getMaterialList();

    }

    @Test
    void updateMaterial() {
    }

    @Test
    void deleteMaterial() {
    }


    public MaterialDTO SetupMaterial(int batchId, String name, int ingredientId, int quantity, String supplier, boolean expired){
        MaterialDTO myM = new MaterialDTO();
        myM.setName(name);
        myM.setBatchId(batchId);
        myM.setIngredientId(ingredientId);
        myM.setQuantity(quantity);
        myM.setSupplier(supplier);
        myM.setExpired(expired);

        return myM;
    }

}