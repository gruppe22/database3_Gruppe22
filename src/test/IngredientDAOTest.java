package test;

import dal.dao.IngredientDAO;
import dal.dto.IngredientDTO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientDAOTest {

    @Test
    void getSumOfIngredient() {
        IngredientDTO ingredi = new IngredientDTO();
        ingredi.setName("chocolade mus");
        ingredi.setReOrder(false);
        ingredi.setActive(false);
        ingredi.setExpired(false);
        ingredi.setIngredientId(8);
        assert(true);
    }

    @Test
    void getReorders() {
        try {
            IngredientDAO dao = new IngredientDAO();
            IngredientDTO ingre = new IngredientDTO();

            ingre.setIngredientId(777);     ingre.setExpired(true);
            ingre.setActive(true);          ingre.setReOrder(true);
            ingre.setName("Allas store bøffer");

            dao.superDelete(ingre.getIngredientId());

            dao.createIngredient(ingre);
            List<IngredientDTO> reOrders = dao.getReorders();
            boolean anyTrue = false;

            for(int i = 0; i < reOrders.size() ; i++){
                if(reOrders.get(i).getIngredientId() == ingre.getIngredientId()) {
                    assert (true);
                    anyTrue = true;
                }
            }

            if(!anyTrue){
                assert(false);
            }

            dao.superDelete(ingre.getIngredientId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIngredientsAll() {
        //todo implement a test that makes sense.
        assert(false);
    }

    @Test
    void getIngredientsActive() {

        IngredientDAO dao = new IngredientDAO();
        IngredientDTO dto = new IngredientDTO();

        // -- -- -- -- -- -- -- -- -- -- -- -- --

        dto.setIngredientId(12);
        dto.setExpired(false);
        dto.setActive(true);
        dto.setReOrder(true);
        dto.setName("Ingredient");

        // -- -- -- -- -- -- -- -- -- -- -- -- --
        try {
            dao.createIngredient(dto);
            List<IngredientDTO> dtoList = dao.getIngredientsActive();
            for(int i = 0; i< dtoList.size(); i++){
                if(dtoList.get(i).getIngredientId() == dto.getIngredientId()){
                    assertEquals(dtoList.get(i).getName(), dto.getName());
                    assertEquals(dtoList.get(i).isActive(), dto.isActive());
                    assertEquals(dtoList.get(i).isReOrder(), dto.isReOrder());
                    assertEquals(dtoList.get(i).isExpired(), dto.isExpired());
                }
            }

            dao.superDelete(dto.getIngredientId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIngredientsInActive() {
        IngredientDAO dao = new IngredientDAO();
        IngredientDTO dto = new IngredientDTO();

        // -- -- -- -- -- -- -- -- -- -- -- -- --

        dto.setIngredientId(12);
        dto.setExpired(false);
        dto.setActive(false);
        dto.setReOrder(true);
        dto.setName("Ingredient");

        // -- -- -- -- -- -- -- -- -- -- -- -- --
        try {
            dao.createIngredient(dto);
            List<IngredientDTO> dtoList = dao.getIngredientsInActive();
            for(int i = 0; i< dtoList.size(); i++){
                if(dtoList.get(i).getIngredientId() == dto.getIngredientId()){
                    assertEquals(dtoList.get(i).getName(), dto.getName());
                    assertEquals(dtoList.get(i).isActive(), dto.isActive());
                    assertEquals(dtoList.get(i).isReOrder(), dto.isReOrder());
                    assertEquals(dtoList.get(i).isExpired(), dto.isExpired());
                }
            }
            dao.superDelete(dto.getIngredientId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createIngredient() {
        // Already tested in Tests above.
    }

    @Test
    void updateIngredient() {
        IngredientDAO dao = new IngredientDAO();
        IngredientDTO dto = new IngredientDTO();
        dto.setIngredientId(12);
        dto.setExpired(false);
        dto.setActive(false);
        dto.setReOrder(true);
        dto.setName("Ingredient");

        try {
            dao.createIngredient(dto);
            dto.setName("INGREDIENTNEW");
            dao.updateIngredient(dto);

            IngredientDTO dto2 = dao.getIngredient(dto.getIngredientId());
            assertEquals(dto.getName(), dto2.getName());

            dao.superDelete(dto.getIngredientId());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void deleteIngredient() {

        IngredientDAO dao = new IngredientDAO();
        IngredientDTO dto = new IngredientDTO();
        dto.setIngredientId(12);
        dto.setExpired(false);
        dto.setActive(false);
        dto.setReOrder(true);
        dto.setName("Ingredient");

        try {
            dao.createIngredient(dto);
            dto.setName("INGREDIENTNEW");
            dao.updateIngredient(dto);

            IngredientDTO dto2 = dao.getIngredient(dto.getIngredientId());
            assertEquals(dto.getName(), dto2.getName());

            dao.deleteIngredient(dto);
            dto2 = dao.getIngredient(dto);

            assertEquals(dto2.isExpired(), true);

            dao.superDelete(dto.getIngredientId());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    void getIngredient() {
        // Already tested in Tests above
    }

}