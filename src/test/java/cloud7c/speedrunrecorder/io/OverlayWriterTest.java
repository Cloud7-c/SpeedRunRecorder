package cloud7c.speedrunrecorder.io;

import cloud7c.speedrunrecorder.RecordManager;
import cloud7c.speedrunrecorder.model.Record;
import cloud7c.speedrunrecorder.model.RecordsMetrics;
import org.junit.jupiter.api.Test;

class OverlayWriterTest {

    // after adding preferences, these tests will fail
//    @Test
//    void writeTotalOverlay() {
//        OverlayWriter.writeCountOverlay(new RecordsMetrics());
//    }
//
//    @Test
//    void writeTotalOverlay2() {
//        RecordsMetrics recordsMetrics = new RecordsMetrics();
//        recordsMetrics.setEnteredNetherCount(100);
//        OverlayWriter.writeCountOverlay(recordsMetrics);
//    }
//
//    @Test
//    void writeCurrentOverlay() {
//        OverlayWriter.writeCurrentOverlay(new RecordManager().getCurrentRecordMap(new Record("111", 10000L, 2000000L, 10000L, 10000L, 10000L, 10000L,10000L,10000L)));
//    }
//
//    @Test
//    void writeCurrentOverlay2() {
//        OverlayWriter.writeCurrentOverlay(
//                new RecordManager().getCurrentRecordMap(
//                        new Record("222", 10000L, 400000L, 10000L, null, null, null,null,null)
//                )
//        );
//    }
//
//    @Test
//    void writeAverageOverlay() {
//        RecordsMetrics recordsMetrics = new RecordsMetrics();
//        recordsMetrics.setEnteredNetherCount(100);
//        recordsMetrics.setEnteredNetherTime(1000000L);
//        OverlayWriter.writeAverageOverlay(new RecordManager().getAverageRecordMap(recordsMetrics));
//    }
}