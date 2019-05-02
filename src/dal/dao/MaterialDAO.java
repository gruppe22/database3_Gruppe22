package dal.dao;

import dal.DummyData;
import dal.dto.MaterialDTO;

import java.io.IOException;

public class MaterialDAO implements IMaterialDAO {

    public MaterialDAO() throws IOException, ClassNotFoundException {
    }

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }

    private DummyData data = new DummyData();

    @Override
    public MaterialDTO getMaterial(int materialId) throws MaterialDTO.DALException {
        MaterialDTO material = data.getMaterial(materialId);
        return material;
    }
}
