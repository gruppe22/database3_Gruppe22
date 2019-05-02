package dal.dto;

public class RecipeDTO {

    private int recipeId;
    private String recipeName;
    private int authorId;
    private int quantity;

    public RecipeDTO (int recipeId, String recipeName, int authorId, int quantity) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.authorId = authorId;
        this.quantity = quantity;
    }

    public RecipeDTO(){}

    public int getRecipeId(){return recipeId;}
    public String getRecipeName(){return recipeName;}
    public int getAuthorId() {return authorId;}
    public int getQuantity(){return quantity;}

    public void setRecipeId(int recipeId) {this.recipeId = recipeId;}
    public void setRecipeName(String recipeName) {this.recipeName = recipeName;}
    public void setAuthorId(int authorId) {this.authorId = authorId;}
    public void setQuantity(int quantity){this.quantity = quantity;}

    @Override
    public String toString() {
        return "RecipeDTO [recipeId= " + recipeId + ", recipeName= " + recipeName + ", authorID= " + authorId + ", quantity= " + quantity + "]";
    }
}
