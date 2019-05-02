package dal.dto;

import java.time.LocalTime;

public class RecipeDTO {

    private int recipeId;
    private LocalTime endDate;
    private String name;
    private int authorId;
    private String description;
    private int quantity;

    public RecipeDTO (int recipeId, String name, int authorId, int quantity) {
        this.recipeId = recipeId;
        this.name = name;
        this.authorId = authorId;
        this.quantity = quantity;
    }

    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public LocalTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalTime endDate) {
        this.endDate = endDate;
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
