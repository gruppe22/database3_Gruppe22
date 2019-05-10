import dal.dao.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class main {
    public static void main(String[] args) {
        Connector sysConnector = new Connector();
        try(Connection connection = sysConnector.static_createConnection()) {
            // ROLES GET
            PreparedStatement statement = connection.prepareStatement("Select * From `Roles`");
            ResultSet res = statement.executeQuery();
            while(res.next()){

                System.out.println(
                        "insert into `Roles` (`roleId`, `roleName`)" +
                        "values(" + res.getInt("roleId") +
                                ",\"" + res.getString("roleName") +
                                "\" );");
            }

            System.out.println("\n");

            // USERS GET
            statement = connection.prepareStatement("Select * From `Users`");
            res = statement.executeQuery();
            while(res.next()){

                System.out.println(
                        "insert into `Users` (`userId`, `userName`, `ini`, `expired`)" +
                        "values( "+ res.getInt("userId") +
                                ", \""+res.getNString("userName")+
                                "\", \""+res.getNString("ini")+
                                "\","+res.getBoolean("expired")+");"
                );
            }

            // RECIPE GET
            statement = connection.prepareStatement("Select * From `Recipe`");
            res = statement.executeQuery();
            while(res.next()){

                System.out.println("insert into `Recipe` (`recipeId`, `endDate`, `name`, `authorId`, `description`, `quantity`)" +
                        "           values("+res.getInt("recipeId") +
                        ","+ res.getDate("endDate")+
                        ",\""+ res.getString("name") +
                        "\","+ res.getInt("authorId")+
                        ",\""+ res.getString("description")+
                        "\","+ res.getInt("quantity") + "); ");
            }

            // PRODUCTION GET
            statement = connection.prepareStatement("Select * From `Production`");
            res = statement.executeQuery();
            while(res.next()) {

                System.out.println(
                        "insert into `Production`(`productionId`, `quantity`, `status`, `recipeId`, `recipeEndDate`, `produktionsLeaderID`)" +
                                "       values(" + res.getInt("productionId") +
                                "," + res.getInt("quantity") +
                                ", \" " + res.getString("status") +
                                " \" ," + res.getInt("recipeId") +
                                "," + res.getDate("recipeEndDate") +
                                "," + res.getInt("produktionsLeaderID") +
                                ");");
            }

            // RELATION USER ROLES
            statement = connection.prepareStatement("Select * From `relUserRoles`");
            res = statement.executeQuery();
            while(res.next()){

                System.out.println(
                        "insert into `relUserRoles` (`userId`, `rolesId`)values(" +
                                res.getInt("userId")+
                                ","+res.getInt("rolesId") +
                                ");");
            }

            // RELATION PRODUCTIONS MATERIALS
            statement = connection.prepareStatement("Select * From `relProdMat`");
            res = statement.executeQuery();
            while(res.next()){

                System.out.println(
                        "insert into `relProdMat` (`produktionId`, `batchId`)" +
                                "values("+res.getInt("produktionId") +
                                ","+ res.getInt("batchId") +");");
            }

            // RELATION INGREDIENT RECIPE
            statement = connection.prepareStatement("Select * From `relIngredientRecipe`");
            res = statement.executeQuery();
            while(res.next()){

                System.out.println(
                        "insert into `relIngredientRecipe` (`ingredientId`, `recipeId`, `endDate`)values" +
                                "("+res.getInt("ingredientId")+
                                ","+res.getInt("recipeId") +
                                ","+res.getDate("endDate") + ");");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
