package cloud7c.speedrunrecorder.model;

public class Record {
    private String worldPath;
    private Long start;
    private Long enterNether;
    private Long enterBastion;
    private Long enterFortress;
    private Long firstPortal;
    private Long enterStronghold;
    private Long enterEnd;
    private Long finish;
    
    public Record(){
        this.worldPath = null;
        this.start = null;
        this.enterNether = null;
        this.enterBastion = null;
        this.enterFortress = null;
        this.firstPortal = null;
        this.enterStronghold = null;
        this.enterEnd = null;
        this.finish = null;
    }

    public Record(String worldPath, Long start, Long enterNether, Long enterBastion, Long enterFortress, Long firstPortal, Long enterStronghold, Long enterEnd, Long finish){
        this.worldPath = worldPath;
        this.start = start;
        this.enterNether = enterNether;
        this.enterBastion = enterBastion;
        this.enterFortress = enterFortress;
        this.firstPortal = firstPortal;
        this.enterStronghold = enterStronghold;
        this.enterEnd = enterEnd;
        this.finish = finish;
    }

    public Record(Record recordToCopy){
        this.worldPath = recordToCopy.getWorldPath();
        this.start = recordToCopy.getStart();
        this.enterNether = recordToCopy.getEnterNether();
        this.enterBastion = recordToCopy.getEnterBastion();
        this.enterFortress = recordToCopy.getEnterFortress();
        this.firstPortal = recordToCopy.getFirstPortal();
        this.enterStronghold = recordToCopy.getEnterStronghold();
        this.enterEnd = recordToCopy.getEnterEnd();
        this.finish = recordToCopy.getFinish();
    }

    public String getWorldPath(){
        return worldPath;
    }

    public Long getStart(){
        return start;
    }

    public Long getEnterNether(){
        return enterNether;
    }

    public Long getEnterBastion(){
        return enterBastion;
    }

    public Long getEnterFortress(){
        return enterFortress;
    }

    public Long getFirstPortal(){
        return firstPortal;
    }

    public Long getEnterStronghold(){
        return enterStronghold;
    }

    public Long getEnterEnd(){
        return enterEnd;
    }

    public Long getFinish(){
        return finish;
    }

    public void setWorldPath(String worldPath){
        this.worldPath = worldPath;
    }
    
    public void setStart(Long start){
        this.start = start;
    }

    public void setEnterNether(Long enterNether){
        this.enterNether = enterNether;
    }

    public void setEnterBastion(Long enterBastion){
        this.enterBastion = enterBastion;
    }

    public void setEnterFortress(Long enterFortress){
        this.enterFortress = enterFortress;
    }

    public void setFirstPortal(Long firstPortal){
        this.firstPortal = firstPortal;
    }

    public void setEnterStronghold(Long enterStronghold){
        this.enterStronghold = enterStronghold;
    }

    public void setEnterEnd(Long enterEnd){
        this.enterEnd = enterEnd;
    }

    public void setFinish(Long finish){
        this.finish = finish;
    }

    public void copyRecord(Record recordToCopy){
        this.worldPath = recordToCopy.getWorldPath();
        this.start = recordToCopy.getStart();
        this.enterNether = recordToCopy.getEnterNether();
        this.enterBastion = recordToCopy.getEnterBastion();
        this.enterFortress = recordToCopy.getEnterFortress();
        this.firstPortal = recordToCopy.getFirstPortal();
        this.enterStronghold = recordToCopy.getEnterStronghold();
        this.enterEnd = recordToCopy.getEnterEnd();
        this.finish = recordToCopy.getFinish();
    }
}
