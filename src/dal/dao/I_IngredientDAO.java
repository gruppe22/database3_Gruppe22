package dal.dao;

import dal.dto.IngredientDTO;

import java.util.List;

public interface I_IngredientDAO {

    int getSumOfIngredient(); // ental
    List<IngredientDTO> getReorders();
    List<IngredientDTO> getIngredientsAll();
    List<IngredientDTO> getIngredientsAktive();
    List<IngredientDTO> getIngredientsInAktive();
    void CreateIngredient(IngredientDTO ingredient);
    void UpdateIngredient(IngredientDTO ingredient);
    void DeleteIngredient(IngredientDTO ingredient);
    IngredientDTO getIngredient(IngredientDTO ingredient);
    IngredientDTO getIngredient(String SearchParameter);
}
