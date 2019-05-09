package dal.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class RecipeDTO {

    private int recipeId;
    private Timestamp endDate;
    private String name;
    private int authorId;
    private String description;
    private int quantity;
    List<IngredientDTO> ingredients = new LinkedList<>();

    public RecipeDTO (int recipeId, String name, int authorId, int quantity) {
        this.recipeId = recipeId;
        this.name = name;
        this.authorId = authorId;
        this.quantity = quantity;
    }
    public RecipeDTO(){}

    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
    public void addIngredient(IngredientDTO ingredient){
        this.ingredients.add(ingredient);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "RecipeDTO [recipeId= " + recipeId + ", recipeName= " + name + ", authorID= " + authorId + ", quantity= " + quantity + "]";
    }
}
