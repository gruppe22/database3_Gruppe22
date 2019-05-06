package dal.dto;

public class IngredientDTO {
      int ingredientId;
      String name;
      boolean active;
      boolean reOrder;
      boolean expired;

    public int getIngredientId() {
        return ingredientId;
    }
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isReOrder() {
        return reOrder;
    }
    public void setReOrder(boolean reOrder) {
        this.reOrder = reOrder;
    }
    public void setExpired(boolean expired){
        this.expired=expired;


    }
}
