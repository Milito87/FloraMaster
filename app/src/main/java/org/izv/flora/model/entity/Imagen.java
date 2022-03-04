package org.izv.flora.model.entity;

public class Imagen {

    public long id, idflora;
    public String name, descr;

    public Imagen(long id, long idflora, String name, String descr) {
        this.id = id;
        this.idflora = idflora;
        this.name = name;
        this.descr = descr;
    }

    public Imagen() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdflora() {
        return idflora;
    }

    public void setIdflora(long idflora) {
        this.idflora = idflora;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + id +
                ", idFlora=" + idflora +
                ", name='" + name + '\'' +
                ", descr='" + descr + '\'' +
                '}';
    }
}
