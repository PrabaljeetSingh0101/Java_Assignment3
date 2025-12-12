package AbstractVehicle;

public abstract class AbstractVehicle {
    private int id;
    private int disTvl;
    private int gasLvl;
    private String status;
    
    // ---- Setters ----
    public void setID(int id_in) {
        id = id_in;
    }
    public void setDisTvl(int disTvl_in) {
        disTvl = disTvl_in;
    }
    public void setGasLvl(int gas_in) {
        gasLvl = gas_in;
    } 
    public void setStatus(String status_in) {
        status = status_in;
    }

    // ---- Getters ----
    public int getID() {
        return id;
    }
    public int getDisTvl() {
        return disTvl;
    }
    public int getGasLvl() {
        return gasLvl;
    } 
    public String getStatus() {
        return status;
    }
    abstract public String getType();
}
