package test;

import dal.dao.IMaterialDAO;
import dal.dao.MaterialDAO;
import dal.dto.MaterialDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class MaterialDAOTest {

    IMaterialDAO MDAO = new MaterialDAO();
    MaterialDTO databaseTestMaterialOne;
    MaterialDTO databaseTestMaterialTwo;






    @Test
    void createMaterial()throws IMaterialDAO.DALException{


            MaterialDTO objectTestMaterial = new MaterialDTO(255, "Bobs Materiale Grej", 1, 2500025, "Testsupplier A/S", false);
            MDAO.createMaterial(objectTestMaterial);
            databaseTestMaterialOne = MDAO.getMaterial(255);

            assertEquals(objectTestMaterial.getBatchId(), databaseTestMaterialOne.getBatchId());
            assertEquals(objectTestMaterial.getIngredientId(), databaseTestMaterialOne.getIngredientId());
            assertEquals(objectTestMaterial.getName(), databaseTestMaterialOne.getName());
            assertEquals(objectTestMaterial.getQuantity(), databaseTestMaterialOne.getQuantity());
            assertEquals(objectTestMaterial.getSupplier(), databaseTestMaterialOne.getSupplier());
            assertEquals(objectTestMaterial.isExpired(), databaseTestMaterialOne.isExpired());
            MDAO.SUPERdeleteMaterial(255);
    }


    @Test
    void getMaterial()throws IMaterialDAO.DALException {




        MaterialDTO myM = new MaterialDTO(255, "Bobs Materiale Grej", 1, 2500025, "Testsupplier A/S", false);


            MDAO.createMaterial(myM);
            databaseTestMaterialTwo = MDAO.getMaterial(255);

            assertEquals(myM.getBatchId(), databaseTestMaterialTwo.getBatchId());
            assertEquals(myM.getIngredientId(), databaseTestMaterialTwo.getIngredientId());
            assertEquals(myM.getName(), databaseTestMaterialTwo.getName());
            assertEquals(myM.getQuantity(), databaseTestMaterialTwo.getQuantity());
            assertEquals(myM.getSupplier(), databaseTestMaterialTwo.getSupplier());
            assertEquals(myM.isExpired(), databaseTestMaterialTwo.isExpired());

        MDAO.SUPERdeleteMaterial(255);
    }

    @Test
    void getMaterialList()throws IMaterialDAO.DALException {

        // Create object myMaterial with random values and insert into database.
        MaterialDTO myMaterial = new MaterialDTO(255, "Bobs Materiale Grej", 1, 2500025, "Testsupplier A/S", false);
        MDAO.createMaterial(myMaterial);
        // Pull list from database and check if myMaterial is in it.
        ArrayList<MaterialDTO> myDtos = (ArrayList<MaterialDTO>) MDAO.getMaterialList();

        boolean result = false;
                for(MaterialDTO m : myDtos)
                    if(myMaterial.getBatchId()==m.getBatchId())
                        result = true;

        assertTrue(result);

        MDAO.SUPERdeleteMaterial(myMaterial.getBatchId());



    }

    @Test
    void updateMaterial() throws IMaterialDAO.DALException {


        // Create object myMaterial with random values.
        MaterialDTO myMaterial = new MaterialDTO(255, "Bobs Materiale Grej", 1, 2500025, "Testsupplier A/S", false);
        // Create the material in the database
        MDAO.createMaterial(myMaterial);
        // change values in object
        myMaterial.setExpired(true);
        myMaterial.setName("Hopsa");
        myMaterial.setQuantity(750);
        myMaterial.setSupplier("Brian");
        // change values in database
        MDAO.updateMaterial(myMaterial);

        // Test, if both object and database materials have the same newly updated values
        assertEquals(myMaterial.getBatchId(),MDAO.getMaterial(255).getBatchId());
        assertEquals(myMaterial.getName(),MDAO.getMaterial(255).getName());
        assertEquals(myMaterial.getQuantity(),MDAO.getMaterial(255).getQuantity());
        assertEquals(myMaterial.getSupplier(),MDAO.getMaterial(255).getSupplier());
        assertEquals(myMaterial.isExpired(),MDAO.getMaterial(255).isExpired());
        // Rinse the database from the test-generated filt
        MDAO.SUPERdeleteMaterial(255);
    }


    @Test
    void deleteMaterial() throws IMaterialDAO.DALException {
        // Create object myMaterial with expired set to false.
        MaterialDTO myMaterial = new MaterialDTO(255, "Bobs Materiale Grej", 1, 2500025, "Testsupplier A/S", false);
        // Create the material in the database
        MDAO.createMaterial(myMaterial);
        // Set expired = True in object
        myMaterial.setExpired(true);
        // Set expired = True in database
        MDAO.deleteMaterial(myMaterial);

        // Test, if both object and database materials are both true in expired.
        assertEquals(myMaterial.isExpired(),MDAO.getMaterial(255).isExpired());
        // Rinse the database from the test-generated filt
        MDAO.SUPERdeleteMaterial(255);
    }





}