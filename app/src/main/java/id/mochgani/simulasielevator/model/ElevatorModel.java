package id.mochgani.simulasielevator.model;

/**
 * Created by mochgani on 16/12/18.
 */

public class ElevatorModel {
    public int floor;
    public int actualFloor;
    public boolean kondisiElevator;
    public String kondisiPosisi;
    public boolean kondisiPintu;

    public ElevatorModel() {
        this.floor = 1;
        this.actualFloor = 1;
        this.kondisiElevator = false;
        this.kondisiPintu = false;
        this.kondisiPosisi = "-";
    }

    public ElevatorModel(int floor, int actualFloor, boolean kondisiElevator, boolean kondisiPintu, String kondisiPosisi) {
        this.floor = floor;
        this.actualFloor = actualFloor;
        this.kondisiElevator = kondisiElevator;
        this.kondisiPintu = kondisiPintu;
        this.kondisiPosisi = kondisiPosisi;
    }
}
