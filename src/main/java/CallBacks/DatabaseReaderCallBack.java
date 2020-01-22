package CallBacks;

import android.hardware.SensorEvent;
import java.util.List;
import Model.Cell;

public interface DatabaseReaderCallBack {
    void OnCallBack(List<Cell> databaseCellList);

}
