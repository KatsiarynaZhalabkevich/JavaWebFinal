package by.epam.web.unit6.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserTarif implements Serializable {
    /**
     * Класс dto для обработки сложных запросов
     */
    private int userId;
    private int tarifId;
    private String name;
    private String description;
    private double price;
    private int speed;
    private double discount = 0;
    private int noteId;
    private Date date;

    public UserTarif() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getTarifId() {
        return tarifId;
    }

    public void setTarifId(int tarifId) {
        this.tarifId = tarifId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTarif tarif = (UserTarif) o;
        return userId == tarif.userId &&
                tarifId == tarif.tarifId &&
                Double.compare(tarif.price, price) == 0 &&
                speed == tarif.speed &&
                Double.compare(tarif.discount, discount) == 0 &&
                noteId == tarif.noteId &&
                Objects.equals(name, tarif.name) &&
                Objects.equals(description, tarif.description) &&
                Objects.equals(date, tarif.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tarifId, name, description, price, speed, discount, noteId, date);
    }

    @Override
    public String toString() {
        return "UserTarif{" +
                "userId=" + userId +
                ", tarifId=" + tarifId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", speed=" + speed +
                ", discount=" + discount +
                ", noteId=" + noteId +
                ", date=" + date +
                '}';
    }
}
