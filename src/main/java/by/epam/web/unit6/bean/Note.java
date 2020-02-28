package by.epam.web.unit6.bean;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private int id;
    private int tarifId;
    private int userId;
    private Date createNote;

    public Note(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTarifId() {
        return tarifId;
    }

    public void setTarifId(int tarifId) {
        this.tarifId = tarifId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateNote() {
        return createNote;
    }

    public void setCreateNote(Date createNote) {
        this.createNote = createNote;
    }
    @Override
    public String toString() {
        return "TarifNote [id=" + id + ", tarifId=" + tarifId + ", userId=" + userId + ", createNote=" + createNote
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createNote == null) ? 0 : createNote.hashCode());
        result = prime * result + id;
        result = prime * result + tarifId;
        result = prime * result + userId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Note other = (Note) obj;
        if (createNote == null) {
            if (other.createNote != null)
                return false;
        } else if (!createNote.equals(other.createNote))
            return false;
        if (id != other.id)
            return false;
        if (tarifId != other.tarifId)
            return false;
        if (userId != other.userId)
            return false;
        return true;
    }

}
