package pt.ipg.rateit;

public class Filmes {

    private int id;
    private String nome;
    private int nota;
    private int idCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void getNome(String title) {
        this.nome = title;
    }

    public int getNota() {
        return nota;
    }

    public void getNota(int nota) {
        this.nota = nota;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
