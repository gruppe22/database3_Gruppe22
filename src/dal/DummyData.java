package dal;

import dal.dto.BatchDTO;
import dal.dto.MaterialDTO;
import dal.dto.UserDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DummyData {
    private List<User> users = new ArrayList<>();
    private List<MaterialDTO> materials = new ArrayList<>();
    private List<BatchDTO> batches = new ArrayList<>();


    public DummyData() throws IOException, ClassNotFoundException {
        if (!(new File("Data.txt").exists())) {
            for (int i = 0; i < 12; i++) {
                List<String> tomListe = new ArrayList<String>();
                tomListe.add("Pharmacist");
                users.add(new User(i, "StandardUserName_" + i, "TEMP", tomListe));
            }
            List<String> adminListe = new ArrayList<String>();
            adminListe.add("Admin");
            users.add(new User(12, "Anders And", "ADM", adminListe));

            // Materials
            MaterialDTO material = new MaterialDTO();
            material.setMaterialId(1234);
            material.setMaterialName("Salt");
            materials.add(material);
        }
        else {
            readFromDisk("Data.txt");
        }
    }
    public User getUser(int id){
        return users.get(id);
    }

    public void createBatch(BatchDTO batch) {
        batches.add(batch);
    }
    public void createUser(UserDTO user){
        while(users.size() > user.getUserId()){
            user.setUserId(user.getUserId() + 1);
        }
        users.add(new User(user.getUserId(), user.getUserName(), user.getIni(),
                user.getRoles()));
    }
    public void updateUser(UserDTO updatedUser){
        int id = updatedUser.getUserId();
        users.get(id).setIni(updatedUser.getIni());
        users.get(id).setUserName(updatedUser.getUserName());
        users.get(id).setRole(updatedUser.getRoles());
    }
    public void deleteUser(UserDTO user){
        int id = user.getUserId();
        users.remove(id);
    }

    public int getUserListSize() {
        return users.size();
    }

    public MaterialDTO getMaterial(int id) {
        for (MaterialDTO m : materials) {
            if (m.getMaterialId() == id)
                return m;
        }
        return null;
    }

    public void saveToDisk(String fileName) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(this.users);
        out.writeObject(this.materials);
        out.writeObject(this.batches);
        out.flush();
        out.close();
    }

    public void readFromDisk(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        this.users = (List<User>)in.readObject();
        this.batches = (List<BatchDTO>)in.readObject();
        this.materials = (List<MaterialDTO>)in.readObject();
    }
}
