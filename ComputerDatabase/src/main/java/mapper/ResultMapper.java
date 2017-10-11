package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultMapper<T> {
    /**
     * @param rs ComputerPreview Set loaded from DB*@return the model list*@throws SQLException an unexpected error.
     * @return the model list generated
     * @throws SQLException occur while accessing datas
     */
    T process(ResultSet rs) throws SQLException;
}
